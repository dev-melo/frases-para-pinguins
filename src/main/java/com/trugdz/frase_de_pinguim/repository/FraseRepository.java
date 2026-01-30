package com.trugdz.frase_de_pinguim.repository;

import com.trugdz.frase_de_pinguim.model.Frase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FraseRepository extends JpaRepository<Frase, Long> {
    List<Frase> findByUser_Id(Long userId);

}
