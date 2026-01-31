package com.trugdz.frase_de_pinguim.dto;


import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(@NotBlank String nickname) {
}
