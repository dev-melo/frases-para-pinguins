package com.trugdz.frase_de_pinguim.controller;

import com.trugdz.frase_de_pinguim.dto.CreateUserDTO;
import com.trugdz.frase_de_pinguim.dto.UserDetailsResponseDTO;
import com.trugdz.frase_de_pinguim.dto.UserResponseDTO;
import com.trugdz.frase_de_pinguim.model.Frase;
import com.trugdz.frase_de_pinguim.model.User;
import com.trugdz.frase_de_pinguim.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    //Config inicial
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    //Rotas
    @GetMapping
    public List<UserResponseDTO> getAll(){ return userService.getAll(); }

    @GetMapping("/{id}")
    public UserDetailsResponseDTO getById(@PathVariable Long id){ return userService.getById(id); }

    @GetMapping("/{userId}/frases")
    public List<Frase> getByUserId(@PathVariable Long userId){
        return userService.getFrasesByUser(userId);
    }

    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody CreateUserDTO request){ return userService.create(request); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ userService.delete(id);}


}
