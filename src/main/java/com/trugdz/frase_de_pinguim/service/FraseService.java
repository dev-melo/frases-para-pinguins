package com.trugdz.frase_de_pinguim.service;

import com.trugdz.frase_de_pinguim.dto.CreateFraseDTO;
import com.trugdz.frase_de_pinguim.dto.FraseResponseDTO;
import com.trugdz.frase_de_pinguim.model.Frase;
import com.trugdz.frase_de_pinguim.model.User;
import com.trugdz.frase_de_pinguim.repository.FraseRepository;
import com.trugdz.frase_de_pinguim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FraseService {
    //Config inicial
    private static final Long USUARIO_DELETADO_ID = 12L;

    private final FraseRepository fraseRepository;
    private final UserRepository userRepository;
    public FraseService(FraseRepository fraseRepository, UserRepository userRepository) {
        this.fraseRepository = fraseRepository;
        this.userRepository = userRepository;
    }


    //Metodos
    public List<FraseResponseDTO> getAll(){ return fraseRepository.findAll()
            .stream()
            .map(frase -> new FraseResponseDTO(
                    frase.getId(),
                    frase.getDataCriacao(),
                    frase.getDeslike(),
                    frase.getFrase(),
                    frase.getUserId()
            ))
            .toList();}


    public FraseResponseDTO getById(Long id){
        Optional<Frase> frasesById = fraseRepository.findById(id);

        if (frasesById.isEmpty()){
            throw new RuntimeException("Frase not found");
        }

        Frase frase = frasesById.get();



        return new FraseResponseDTO(
                frase.getId(),
                frase.getDataCriacao(),
                frase.getDeslike(),
                frase.getFrase(),
                frase.getUserId()
        );

    }

    public List<FraseResponseDTO> getFrasesByUser(Long userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        User u = user.get();



        List<FraseResponseDTO> frasesDTO = u.getFrases()
                .stream()
                .map(frase -> new FraseResponseDTO(
                        frase.getId(),
                        frase.getDataCriacao(),
                        frase.getDeslike(),
                        frase.getFrase(),
                        frase.getUserId()
                ))
                .toList();

        return frasesDTO;
    }


    public FraseResponseDTO create(CreateFraseDTO request, Long userId){
        User user = userRepository.findById(userId).orElseThrow();

        String testaFrase = request.frase();
        Frase frase = new Frase();
        if(contemPalavraProibida(testaFrase)){


            User permaBan = userRepository.findById(13L).orElseThrow();
            frase.setUser(permaBan);
            String fraseUserOriginal = request.frase();
            frase.setFrase("Usuario Original: " + user.getNickname() + " | Frase: " + fraseUserOriginal);

        } else {
            frase.setFrase(request.frase());
            frase.setUser(user);
       }
        frase.setDeslike(0);
        frase.setDataCriacao(LocalDate.now());
        fraseRepository.save(frase);
        return new FraseResponseDTO(
                frase.getId(),
                frase.getDataCriacao(),
                frase.getDeslike(),
                frase.getFrase(),
                frase.getUserId()
        );
    }

    public FraseResponseDTO deslike(Long id){
        Frase frase = fraseRepository.findById(id).orElseThrow();
        frase.setDeslike(frase.getDeslike() + 1);
        fraseRepository.save(frase);

        return new FraseResponseDTO(
                frase.getId(),
                frase.getDataCriacao(),
                frase.getDeslike(),
                frase.getFrase(),
                frase.getUserId()
        );

    }

    public void delete(Long fraseId){
        Frase frase = fraseRepository.findById(fraseId).orElseThrow();
        User user = userRepository.findById(USUARIO_DELETADO_ID).orElseThrow();
        frase.setUser(user);
        fraseRepository.save(frase);
    }


    private Boolean contemPalavraProibida(String txt){
        return txt.toLowerCase().trim().contains("qwert123456");
    }
}
