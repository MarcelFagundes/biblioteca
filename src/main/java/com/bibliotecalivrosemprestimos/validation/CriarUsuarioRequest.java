package com.bibliotecalivrosemprestimos.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(
        @NotBlank @Size(max = 100)
        String nome,

        @NotBlank @Email @Size(max = 100)
        String email
) {}