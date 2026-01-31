package com.trugdz.frase_de_pinguim.dto;

import java.time.LocalDate;
import java.util.List;

public record UserDetailsResponseDTO(Long id, String nickname, Boolean ativo, LocalDate dataCriacao, List<UserFraseResponseDTO> frases) {
}
