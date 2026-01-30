package com.trugdz.frase_de_pinguin.repository;

import com.trugdz.frase_de_pinguin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
