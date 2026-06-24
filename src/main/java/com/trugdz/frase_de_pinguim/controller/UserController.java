package com.trugdz.frase_de_pinguim.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trugdz.frase_de_pinguim.dto.CreateUserDTO;
import com.trugdz.frase_de_pinguim.dto.UserDetailsResponseDTO;
import com.trugdz.frase_de_pinguim.dto.UserResponseDTO;
import com.trugdz.frase_de_pinguim.dto.UserStatusDTO;
import com.trugdz.frase_de_pinguim.model.Frase;
import com.trugdz.frase_de_pinguim.model.User;
import com.trugdz.frase_de_pinguim.service.UserService;

import jakarta.validation.Valid;

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

<<<<<<< HEAD
=======
    @PatchMapping("/{id}/active")
    public UserStatusDTO activeUser(@PathVariable Long id){
        return userService.activeUser(id);
    }

    @PatchMapping("/{id}/disable")
    public UserStatusDTO disableUser(@PathVariable Long id){
        return userService.disableUser(id);
    }
>>>>>>> origin/main
    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody CreateUserDTO request){ return userService.create(request); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ userService.delete(id);}


}
