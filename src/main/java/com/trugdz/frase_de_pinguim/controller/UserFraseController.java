package com.trugdz.frase_de_pinguim.controller;

import com.trugdz.frase_de_pinguim.dto.FraseResponseDTO;
import com.trugdz.frase_de_pinguim.model.Frase;
import com.trugdz.frase_de_pinguim.service.FraseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/frases")
public class UserFraseController {

    private final FraseService fraseService;

    public UserFraseController(FraseService fraseService) {
        this.fraseService = fraseService;
    }

    @GetMapping
    public List<FraseResponseDTO> getByUserId(@PathVariable Long userId){
        return fraseService.getFrasesByUser(userId);
    }

}
