package com.trugdz.frase_de_pinguim.dto;

import java.time.LocalDate;

public record FraseResponseDTO(Long id, LocalDate dataCriacao, Integer deslike, String frase, Long userId) {
}
