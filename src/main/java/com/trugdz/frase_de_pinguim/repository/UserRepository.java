package com.trugdz.frase_de_pinguim.repository;

import com.trugdz.frase_de_pinguim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
