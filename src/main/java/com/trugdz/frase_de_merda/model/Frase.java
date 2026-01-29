package com.trugdz.frase_de_merda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "frases_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Frase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataCriacao;
    private Integer deslike;
    private String frase;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Long getUserId(){
        return user != null ? user.getId() : null;
    }
}
