package com.bibliotecalivrosemprestimos.core.UseCase;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.LivroMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.output.repository.LivroRepository;
import com.bibliotecalivrosemprestimos.adapter.output.repository.UsuarioRepository;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.exception.BusinessException;
import com.bibliotecalivrosemprestimos.adapter.input.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class LivroUseCase implements LivroInputPort {

    private final LivroOutputPort livroOutputPort;

//    private final LivroMapper livroMapper;

    public LivroUseCase(LivroOutputPort livroOutputPort) {
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

         return LivroMapper.INSTANCE.fromEntity(livro);
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
                .map(LivroMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    public LivroRequest buscarPorId(Long id) {
        Livro livro = livroOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        return LivroMapper.INSTANCE.fromEntity(livro);
    }

     public LivroRequest atualizarLivro(Long id, AtualizarLivroRequest request) {
         Livro livro = livroOutputPort.findById(id)
                 .orElseThrow(() -> new NotFoundException("Livro não encontrado"));


         if (request.titulo() != null) {
             livro.setTitulo(request.titulo());
         }

         if (request.autor() != null) {
             livro.setAutor(request.autor());
         }

         if (request.isbn() != null) {
             livro.setIsbn(request.isbn());
         }

         if (request.estoque() != null) {
             livro.setEstoque(request.estoque());
         }

         if (request.ativo() != null) {
             livro.setAtivo(request.ativo());
         }

         livroOutputPort.update(livro);

         return LivroMapper.INSTANCE.fromEntity(livro);
     }

    public void desativarLivro(Long id) {
        Livro livro = livroOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

        livro.exclusaoLogica();
        livroOutputPort.update(livro);
    }

    public List<Livro> listarTodosLivros() {
        return livroOutputPort.findAll();
    }


    public List<LivroComEmprestimoRequest> listarLivrosEmprestados() {
         return livroOutputPort.findLivrosEmprestados()
                 .stream()
                 .map(LivroMapper.INSTANCE::objectArrayToDto)
                 .collect(Collectors.toList());
    }
}