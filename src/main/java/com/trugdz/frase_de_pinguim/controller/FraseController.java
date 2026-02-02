package com.trugdz.frase_de_pinguim.controller;

import com.trugdz.frase_de_pinguim.dto.CreateFraseDTO;
import com.trugdz.frase_de_pinguim.dto.FraseResponseDTO;
import com.trugdz.frase_de_pinguim.service.FraseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("frases")
public class FraseController {

    private final FraseService fraseService;
    public FraseController(FraseService fraseService){
        this.fraseService = fraseService;
    }

    @GetMapping
    public List<FraseResponseDTO> getAll(){return fraseService.getAll();}

    @GetMapping("{id}")
    public FraseResponseDTO getById(@PathVariable Long id){return fraseService.getById(id);}

    @PostMapping
    public FraseResponseDTO create(@RequestBody CreateFraseDTO request, @RequestParam Long userId){

        return fraseService.create(request, userId);
    }

    @PatchMapping("/{id}/deslike")
    public FraseResponseDTO deslike(@PathVariable Long id){return fraseService.deslike(id);}

    @DeleteMapping("/{fraseId}")
    public void delete(@PathVariable Long fraseId){fraseService.delete(fraseId);}


}
