package com.trugdz.frase_de_merda.service;

import com.trugdz.frase_de_merda.model.Frase;
import com.trugdz.frase_de_merda.model.User;
import com.trugdz.frase_de_merda.repository.FraseRepository;
import com.trugdz.frase_de_merda.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //Config inicial
    private final UserRepository userRepository;
    private final FraseRepository fraseRepository;
    public UserService(UserRepository userRepository, FraseRepository fraseRepository) {
        this.userRepository = userRepository;
        this.fraseRepository = fraseRepository;
    }

    //Regras
    public List<User> getAll(){return userRepository.findAll();}
    public List<Frase> getFrasesByUser(Long userId){return fraseRepository.findByUser_Id(userId);}
    public Optional<User> getById(Long id){ return userRepository.findById(id);}
    public User create(User user){
        user.setAtivo(true);
        user.setDataCriacao(LocalDate.now());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
