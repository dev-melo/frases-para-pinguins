package com.trugdz.frase_de_merda.controller;

import com.trugdz.frase_de_merda.model.Frase;
import com.trugdz.frase_de_merda.service.FraseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("frases")
public class FraseController {

    private final FraseService fraseService;
    public FraseController(FraseService fraseService){
        this.fraseService = fraseService;
    }

    @GetMapping
    public List<Frase> getAll(){return fraseService.getAll();}

    @GetMapping("{id}")
    public Optional<Frase> getById(@PathVariable Long id){return fraseService.getById(id);}

    @PostMapping
    public Frase create(@RequestBody Frase frase,@RequestParam Long userId){return fraseService.create(frase, userId);}

    @PatchMapping("/{id}/deslike")
    public Frase deslike(@PathVariable Long id){return fraseService.deslike(id);}

    @DeleteMapping("/{fraseId}")
    public void delete(@PathVariable Long fraseId){fraseService.delete(fraseId);}


}
