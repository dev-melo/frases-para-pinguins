package com.trugdz.frase_de_merda.repository;

import com.trugdz.frase_de_merda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
