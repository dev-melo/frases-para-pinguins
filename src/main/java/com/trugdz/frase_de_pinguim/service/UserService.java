package com.trugdz.frase_de_pinguim.service;

import com.trugdz.frase_de_pinguim.dto.*;
import com.trugdz.frase_de_pinguim.model.User;
import com.trugdz.frase_de_pinguim.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //Config inicial
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    //Regras
    public List<UserResponseDTO> getAll(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getNickname()))
                .toList();
    }



    public UserDetailsResponseDTO getById(Long id){
        Optional<User> userDetails = userRepository.findById(id);

        if (userDetails.isEmpty()){
            throw new RuntimeException("User not found");
        }

        User u = userDetails.get();



        List<UserFraseResponseDTO> frasesDTO = u.getFrases()
                .stream()
                .map(frase -> new UserFraseResponseDTO(
                        frase.getId(),
                        frase.getDataCriacao(),
                        frase.getDeslike(),
                        frase.getFrase()
                ))
                .toList();

        return new UserDetailsResponseDTO(u.getId(), u.getNickname(), u.getAtivo(), u.getDataCriacao(), frasesDTO);

    }


    public UserResponseDTO create(CreateUserDTO request){
        User user = new User();

        user.setNickname(request.nickname());
        user.setAtivo(true);
        user.setDataCriacao(LocalDate.now());
        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser.getId(), savedUser.getNickname());
    }

    public void delete(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    public UserStatusDTO activeUser(@Valid Long id) {

        if (!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }

        User userById = userRepository.findById(id).get();
        userById.setAtivo(true);
        userRepository.save(userById);


        return new UserStatusDTO(
                userById.getId(),
                userById.getNickname(),
                userById.getAtivo()
        );
    }
    public UserStatusDTO disableUser(@Valid Long id) {

        if (!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }

        User userById = userRepository.findById(id).get();
        userById.setAtivo(false);
        userRepository.save(userById);


        return new UserStatusDTO(
                userById.getId(),
                userById.getNickname(),
                userById.getAtivo()
        );
    }
}
