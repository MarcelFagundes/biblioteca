package com.bibliotecalivrosemprestimos.core.service;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import com.bibliotecalivrosemprestimos.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.validation.CriarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.exception.BusinessException;
import com.bibliotecalivrosemprestimos.exception.NotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LivroService implements LivroInputPort {

    private final LivroOutputPort livroOutputPort;

    public LivroService(LivroOutputPort livroOutputPort) {
        this.livroOutputPort = livroOutputPort;
    }

     public LivroRequest criarLivro(CriarLivroRequest request) {
         if (livroOutputPort.existsByIsbn(request.isbn())) {
             throw new BusinessException("ISBN já cadastrado");
         }

         Livro livro = new Livro(
             request.isbn(),
             request.titulo(),
             request.autor(),
             request.estoque()
         );

         livro.incrementarEstoque();
         livro = livroOutputPort.save(livro);
         return LivroRequest.fromEntity(livro);
     }

    public List<LivroRequest> listarLivros(String titulo, Boolean ativo) {
        List<Livro> livros;
        if (titulo != null && ativo != null) {
            livros = livroOutputPort.findByTituloContainingAndAtivo(titulo, ativo);
        } else if (titulo != null) {
            livros = livroOutputPort.findByTituloContaining(titulo);
        } else if (ativo != null) {
            livros = livroOutputPort.findByAtivo(ativo);
        } else {
            livros = livroOutputPort.findAll();
        }

        return livros.stream()
                .map(LivroRequest::fromEntity)
                .collect(Collectors.toList());
    }

    public LivroRequest buscarPorId(Long id) {
        Livro livro = livroOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        return LivroRequest.fromEntity(livro);
    }

     public LivroRequest atualizarLivro(Long id, AtualizarLivroRequest request) {
         Livro livro = livroOutputPort.findById(id)
                 .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

         livro.setTitulo(request.titulo());
         livro.setAutor(request.autor());
         livro.setEstoque(request.estoque());
         livro.setAtivo(request.ativo());

         livro = livroOutputPort.save(livro);
         return LivroRequest.fromEntity(livro);
     }

    public void desativarLivro(Long id) {
        Livro livro = livroOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
//        livro.setAtivo(false);
        livro.exclusaoLogica();
        livroOutputPort.save(livro);
    }

    public List<Livro> listarTodosLivros() {
        return livroOutputPort.findAll();
    }


    public List<LivroComEmprestimoRequest> listarLivrosEmprestados() {
         return livroOutputPort.findLivrosEmprestados()
                 .stream()
                 .map(this::toLivroComEmprestimoDTO)
                 .collect(Collectors.toList());
    }

    private LivroComEmprestimoRequest toLivroComEmprestimoDTO(Object[] result) {
         Livro livro = (Livro) result[0];
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