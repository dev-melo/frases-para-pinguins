package com.trugdz.frase_de_pinguim.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFraseDTO(@NotBlank String frase, @NotNull Long userId) {
}
