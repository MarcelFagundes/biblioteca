package com.bibliotecalivrosemprestimos.adapter.input.request.validation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarLivroRequest(@NotBlank @Size(max = 20)
                                String isbn, @NotBlank @Size(max = 100)
                                String titulo, @NotBlank @Size(max = 100)
                                String autor, @Min(0)
                                Integer estoque)
{}