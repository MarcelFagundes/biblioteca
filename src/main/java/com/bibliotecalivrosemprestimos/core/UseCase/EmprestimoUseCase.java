package com.bibliotecalivrosemprestimos.core.UseCase;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.EmprestimoMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.DevolverLivroRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.input.EmprestimoInputPort;
import com.bibliotecalivrosemprestimos.port.output.EmprestimoOutputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.exception.BusinessException;
import com.bibliotecalivrosemprestimos.adapter.input.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmprestimoUseCase implements EmprestimoInputPort {

    private final EmprestimoOutputPort emprestimoOutputPort;
    private final LivroOutputPort livroOutputPort;
    private final UsuarioOutputPort usuarioOutputPort;

    public EmprestimoUseCase(EmprestimoOutputPort emprestimoOutputPort,
                             LivroOutputPort livroOutputPort,
                             UsuarioOutputPort usuarioOutputPort) {
        this.emprestimoOutputPort = emprestimoOutputPort;
        this.livroOutputPort = livroOutputPort;
        this.usuarioOutputPort = usuarioOutputPort;
    }

    public EmprestimoRequest criarEmprestimo(CriarEmprestimoRequest request) {

        // Verifica disponibilidade do livro
        if (!verificarDisponibilidadeLivro(request.livroId())) {
            throw new IllegalStateException("Livro não está disponível para empréstimo");
        }

        Emprestimo emprestimo = new Emprestimo();

        if (emprestimosAtivosUsuarioLivro(request.usuarioId(), request.livroId())) {
            throw new IllegalStateException("Usuário com emprestimo para este livro");
        }

         // Cria o empréstimo
         LocalDateTime retiradoEm = LocalDateTime.now();
         LocalDateTime devolucaoPrevista = retiradoEm.plusDays(7);

        Livro livro = livroOutputPort.findById(request.livroId())
                .orElseThrow(() -> new NotFoundException("Livro com ID " + request.livroId() + " não encontrado"));

        Usuario usuario = usuarioOutputPort.findById(request.usuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + request.usuarioId() + " não encontrado"));

                 emprestimo.setLivroId(livro.getId());
                 emprestimo.getLivroTitulo();
                 emprestimo.setUsuarioId(usuario.getId());
                 emprestimo.setUsuarioNome(usuario.getNome());
                 emprestimo.setLivroTitulo(livro.getTitulo());
                 emprestimo.setRetiradoEm(retiradoEm);
                 emprestimo.setDevolucaoPrevista(devolucaoPrevista);
                 emprestimo.getDevolvidoEm();
                 livro.decrementarEstoque();

        boolean emprestimoDuplicado = emprestimoOutputPort.existsByLivroAndUsuarioAndDevolvidoEmIsNull(
                livro, usuario);

        if (emprestimoDuplicado) {
            throw new BusinessException("Usuário já possui um empréstimo em aberto para este livro");
        }

         emprestimo = emprestimoOutputPort.save(emprestimo);
         return EmprestimoMapper.INSTANCE.fromEntity(emprestimo);
     }

    private boolean verificarDisponibilidadeLivro(Long livroId) {
        return livroOutputPort.findById(livroId)
                .map(livro -> livro.getEstoque() > 0)
                .orElseThrow(() -> new NotFoundException("Livro com ID " + livroId + " não encontrado"));
    }

    private boolean emprestimosAtivosUsuarioLivro(Long usuarioId, Long livroId) {
        Optional<Emprestimo> emprestimo = emprestimoOutputPort.buscarEmprestimoAtivoPorUsuarioELivro(
                usuarioId, livroId);

        if (emprestimo.isPresent()) {
            throw new IllegalStateException("Usuário com emprestimo para este livro");
        }
        return false;
    }

    public List<EmprestimoRequest> listarEmprestimos(Long usuarioId, Boolean ativo) {

        List<Emprestimo> emprestimos;

        if (usuarioId != null && ativo != null) {
            if (ativo) {
                emprestimos = emprestimoOutputPort.findByUsuarioIdAndDevolvidoEmIsNull(usuarioId);
            } else {
                emprestimos = emprestimoOutputPort.findByUsuarioIdAndDevolvidoEmIsNotNull(usuarioId);
            }
        } else if (usuarioId != null) {
            emprestimos = emprestimoOutputPort.findByUsuarioId(usuarioId);
        } else if (ativo != null) {
            if (ativo) {
                emprestimos = emprestimoOutputPort.findByDevolvidoEmIsNull();
            } else {
                emprestimos = emprestimoOutputPort.findByDevolvidoEmIsNotNull();
            }
        } else {
            emprestimos = emprestimoOutputPort.findAll();
        }

        return emprestimos.stream()
                .map(EmprestimoMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    public EmprestimoRequest registrarDevolucao(Long id, DevolverLivroRequest request) {
        Emprestimo emprestimo = emprestimoOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado"));

        if (emprestimosAtivosUsuarioLivro(request.usuarioId(), request.livroId())) {
            throw new IllegalStateException("Usuário com emprestimo para este livro");
        }

//        if (emprestimo.getDevolvidoEm() != null && emprestimo.) {
//            throw new BusinessException("Este empréstimo já foi devolvido");
//        }

        Livro livro = livroOutputPort.findById(emprestimo.getLivroId())
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

        // Registra devolução
        emprestimo.setDevolvidoEm(LocalDateTime.now());

        // Atualiza estoque
        livro.incrementarEstoque();
        livroOutputPort.update(livro);

        emprestimo = emprestimoOutputPort.save(emprestimo);
        return EmprestimoMapper.INSTANCE.fromEntity(emprestimo);
    }

     public MultaRequest calcularMulta(Long id) {
         Emprestimo emprestimo = emprestimoOutputPort.findById(id)
                 .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado"));

         if (emprestimo.getDevolvidoEm() == null) {
             throw new BusinessException("O livro ainda não foi devolvido");
         }

         long diasAtraso = java.time.Duration.between(
                 emprestimo.getDevolucaoPrevista(),
                 emprestimo.getDevolvidoEm()
         ).toDays();

         double valorMulta = diasAtraso * 2.0;

         if (!emprestimo.isAtrasado()) {
             return new MultaRequest(diasAtraso, valorMulta);
         }
         return new MultaRequest(diasAtraso, valorMulta);
     }
}