package com.trugdz.frase_de_merda.controller;

import com.trugdz.frase_de_merda.model.Frase;
import com.trugdz.frase_de_merda.model.User;
import com.trugdz.frase_de_merda.service.UserService;
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
    public List<User> getAll(){ return userService.getAll(); }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id){ return userService.getById(id); }

    @GetMapping("/{userId}/frases")
    public List<Frase> getByUserId(@PathVariable Long userId){
        return userService.getFrasesByUser(userId);
    }

    @PostMapping
    public User create(@RequestBody User user){ return userService.create(user); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ userService.delete(id);}


}
