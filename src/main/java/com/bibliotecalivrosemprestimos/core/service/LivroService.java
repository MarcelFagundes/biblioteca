package com.bibliotecalivrosemprestimos.core.service;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.LivroMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.exception.BusinessException;
import com.bibliotecalivrosemprestimos.adapter.input.exception.NotFoundException;
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

         livro.setTitulo(request.titulo());
         livro.setAutor(request.autor());
         livro.setEstoque(request.estoque());
         livro.setAtivo(request.ativo());

         livro = livroOutputPort.save(livro);

         return LivroMapper.INSTANCE.fromEntity(livro);
     }

    public void desativarLivro(Long id) {
        Livro livro = livroOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

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
         LivroRequest livro = (LivroRequest) result[0];
         EmprestimoRequest emprestimo = (EmprestimoRequest) result[1];
         UsuarioRequest usuario = (UsuarioRequest) result[2];

         return new LivroComEmprestimoRequest(
             livro.id(),
             livro.titulo(),
             livro.autor(),
             usuario.nome(),
             usuario.email(),
             emprestimo.retiradoEm(),
             emprestimo.devolucaoPrevista()
         );
     }
}