package com.bibliotecalivrosemprestimos.service;

import com.bibliotecalivrosemprestimos.dto.MultaDTO;
import com.bibliotecalivrosemprestimos.validation.CriarEmprestimoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotecalivrosemprestimos.dto.EmprestimoDTO;
import com.bibliotecalivrosemprestimos.entity.EmprestimoEntity;
import com.bibliotecalivrosemprestimos.entity.LivroEntity;
import com.bibliotecalivrosemprestimos.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.exception.BusinessException;
import com.bibliotecalivrosemprestimos.exception.NotFoundException;
import com.bibliotecalivrosemprestimos.repository.EmprestimoRepository;
import com.bibliotecalivrosemprestimos.repository.LivroRepository;
import com.bibliotecalivrosemprestimos.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                            LivroRepository livroRepository,
                            UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

     @Transactional
     public EmprestimoDTO criarEmprestimo(CriarEmprestimoRequest request) {
         // Validações
         LivroEntity livro = livroRepository.findById(request.livroId())
                 .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

         if (!livro.getAtivo()) {
             throw new BusinessException("Livro inativo não pode ser emprestado");
         }

         if (livro.getEstoque() <= 0) {
             throw new BusinessException("Estoque insuficiente para empréstimo");
         }

         UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                 .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

         // Verifica se já existe empréstimo em aberto para o mesmo livro e usuário
         boolean emprestimoDuplicado = emprestimoRepository.existsByLivroAndUsuarioAndDevolvidoEmIsNull(
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

         EmprestimoEntity emprestimo = new EmprestimoEntity(
             livro,
             usuario,
             devolucaoPrevista
         );

         // Atualiza estoque
         livro.decrementarEstoque();
         livroRepository.save(livro);

         emprestimo = emprestimoRepository.save(emprestimo);
         return EmprestimoDTO.fromEntity(emprestimo);
     }

    public List<EmprestimoDTO> listarEmprestimos(Long usuarioId, Boolean ativo) {
        List<EmprestimoEntity> emprestimos;

        if (usuarioId != null && ativo != null) {
            if (ativo) {
                emprestimos = emprestimoRepository.findByUsuarioIdAndDevolvidoEmIsNull(usuarioId);
            } else {
                emprestimos = emprestimoRepository.findByUsuarioIdAndDevolvidoEmIsNotNull(usuarioId);
            }
        } else if (usuarioId != null) {
            emprestimos = emprestimoRepository.findByUsuarioId(usuarioId);
        } else if (ativo != null) {
            if (ativo) {
                emprestimos = emprestimoRepository.findByDevolvidoEmIsNull();
            } else {
                emprestimos = emprestimoRepository.findByDevolvidoEmIsNotNull();
            }
        } else {
            emprestimos = emprestimoRepository.findAll();
        }

        return emprestimos.stream()
                .map(EmprestimoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmprestimoDTO registrarDevolucao(Long id) {
        EmprestimoEntity emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado"));

        if (emprestimo.getDevolvidoEm() != null) {
            throw new BusinessException("Este empréstimo já foi devolvido");
        }

        // Registra devolução
        emprestimo.setDevolvidoEm(LocalDateTime.now());

        // Atualiza estoque
        LivroEntity livro = emprestimo.getLivro();
        livro.incrementarEstoque();
        livroRepository.save(livro);

        emprestimo = emprestimoRepository.save(emprestimo);
        return EmprestimoDTO.fromEntity(emprestimo);
    }

     public MultaDTO calcularMulta(Long id) {
         EmprestimoEntity emprestimo = emprestimoRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado"));

         if (emprestimo.getDevolvidoEm() == null) {
             throw new BusinessException("O livro ainda não foi devolvido");
         }

         if (!emprestimo.isAtrasado()) {
             return new MultaDTO(0, 0.0);
         }

         long diasAtraso = java.time.Duration.between(
             emprestimo.getDevolucaoPrevista(),
             emprestimo.getDevolvidoEm()
         ).toDays();

         double valorMulta = diasAtraso * 2.0; // R$ 2,00 por dia

         return new MultaDTO(diasAtraso, valorMulta);
     }
}