package com.bibliotecalivrosemprestimos.core.service;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.EmprestimoMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmprestimoService implements EmprestimoInputPort {

    private final EmprestimoOutputPort emprestimoOutputPort;
    private final LivroOutputPort livroOutputPort;
    private final UsuarioOutputPort usuarioOutputPort;

    public EmprestimoService(EmprestimoOutputPort emprestimoOutputPort,
                            LivroOutputPort livroOutputPort,
                            UsuarioOutputPort usuarioOutputPort) {
        this.emprestimoOutputPort = emprestimoOutputPort;
        this.livroOutputPort = livroOutputPort;
        this.usuarioOutputPort = usuarioOutputPort;
    }

     public EmprestimoRequest criarEmprestimo(CriarEmprestimoRequest request) {
         // Validações
         Livro livro = livroOutputPort.findById(request.livroId())
                 .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

         if (!livro.getAtivo()) {
             throw new BusinessException("Livro inativo não pode ser emprestado");
         }

         if (livro.getEstoque() <= 0) {
             throw new BusinessException("Estoque insuficiente para empréstimo");
         }

         Usuario usuario = usuarioOutputPort.findById(request.usuarioId())
                 .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

         // Verifica se já existe empréstimo em aberto para o mesmo livro e usuário
           boolean emprestimoDuplicado = emprestimoOutputPort.existsByLivroAndUsuarioAndDevolvidoEmIsNull(
             livro, usuario);

         if (emprestimoDuplicado) {
             throw new BusinessException("Usuário já possui um empréstimo em aberto para este livro");
         }

         // Cria o empréstimo
         LocalDateTime retiradoEm = LocalDateTime.now();
         LocalDateTime devolucaoPrevista = retiradoEm.plusDays(7);

         if (request.devolucaoPrevista() != null) {
             devolucaoPrevista = request.devolucaoPrevista();
         }

         Emprestimo emprestimo = new Emprestimo(
             livro,
             usuario,
             devolucaoPrevista
         );

         // Atualiza estoque
         livro.decrementarEstoque();
         livroOutputPort.save(livro);

         emprestimo = emprestimoOutputPort.save(emprestimo);
         return EmprestimoMapper.INSTANCE.fromEntity(emprestimo);
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

    public EmprestimoRequest registrarDevolucao(Long id) {
        Emprestimo emprestimo = emprestimoOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado"));

        if (emprestimo.getDevolvidoEm() != null) {
            throw new BusinessException("Este empréstimo já foi devolvido");
        }

        // Registra devolução
        emprestimo.setDevolvidoEm(LocalDateTime.now());

        // Atualiza estoque
        Livro livro = emprestimo.getLivro();
        livro.decrementarEstoque();
        livroOutputPort.save(livro);

        emprestimo = emprestimoOutputPort.save(emprestimo);
        return EmprestimoMapper.INSTANCE.fromEntity(emprestimo);
    }

     public MultaRequest calcularMulta(Long id) {
         Emprestimo emprestimo = emprestimoOutputPort.findById(id)
                 .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado"));

         if (emprestimo.getDevolvidoEm() == null) {
             throw new BusinessException("O livro ainda não foi devolvido");
         }

         if (!emprestimo.isAtrasado()) {
             return new MultaRequest(0, 0.0);
         }

         long diasAtraso = java.time.Duration.between(
             emprestimo.getDevolucaoPrevista(),
             emprestimo.getDevolvidoEm()
         ).toDays();

         double valorMulta = diasAtraso * 2.0; // R$ 2,00 por dia

         return new MultaRequest(diasAtraso, valorMulta);
     }
}