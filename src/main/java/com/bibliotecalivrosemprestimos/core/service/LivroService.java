package com.bibliotecalivrosemprestimos.core.service;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.validation.CriarLivroRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
import com.bibliotecalivrosemprestimos.exception.BusinessException;
import com.bibliotecalivrosemprestimos.exception.NotFoundException;
import com.bibliotecalivrosemprestimos.adapter.output.repository.LivroRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

     @Transactional
     public LivroRequest criarLivro(CriarLivroRequest request) {
         if (livroRepository.existsByIsbn(request.isbn())) {
             throw new BusinessException("ISBN já cadastrado");
         }

         LivroEntity livro = new LivroEntity(
             request.isbn(),
             request.titulo(),
             request.autor(),
             request.estoque()
         );

         livro = livroRepository.save(livro);
         return LivroRequest.fromEntity(livro);
     }

    public List<LivroRequest> listarLivros(String titulo, Boolean ativo) {
        List<LivroEntity> livros;
        if (titulo != null && ativo != null) {
            livros = livroRepository.findByTituloContainingAndAtivo(titulo, ativo);
        } else if (titulo != null) {
            livros = livroRepository.findByTituloContaining(titulo);
        } else if (ativo != null) {
            livros = livroRepository.findByAtivo(ativo);
        } else {
            livros = livroRepository.findAll();
        }

        return livros.stream()
                .map(LivroRequest::fromEntity)
                .collect(Collectors.toList());
    }

    public LivroRequest buscarPorId(Long id) {
        LivroEntity livro = livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        return LivroRequest.fromEntity(livro);
    }

     @Transactional
     public LivroRequest atualizarLivro(Long id, AtualizarLivroRequest request) {
         LivroEntity livro = livroRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

         livro.setTitulo(request.titulo());
         livro.setAutor(request.autor());
         livro.setEstoque(request.estoque());
         livro.setAtivo(request.ativo());

         livro = livroRepository.save(livro);
         return LivroRequest.fromEntity(livro);
     }

    @Transactional
    public void desativarLivro(Long id) {
        LivroEntity livro = livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        livro.setAtivo(false);
        livroRepository.save(livro);
    }

    public List<LivroEntity> listarTodosLivros() {
        return livroRepository.findAll();
    }


    public List<LivroComEmprestimoRequest> listarLivrosEmprestados() {
         return livroRepository.findLivrosEmprestados()
                 .stream()
                 .map(this::toLivroComEmprestimoDTO)
                 .collect(Collectors.toList());
    }

    private LivroComEmprestimoRequest toLivroComEmprestimoDTO(Object[] result) {
         LivroEntity livro = (LivroEntity) result[0];
         EmprestimoEntity emprestimo = (EmprestimoEntity) result[1];
         UsuarioEntity usuario = (UsuarioEntity) result[2];

         return new LivroComEmprestimoRequest(
             livro.getId(),
             livro.getTitulo(),
             livro.getAutor(),
             usuario.getNome(),
             usuario.getEmail(),
             emprestimo.getRetiradoEm(),
             emprestimo.getDevolucaoPrevista()
         );
     }
}