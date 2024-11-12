package com.mateus_back.leilao.repository.interfaces;

import com.mateus_back.leilao.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    Optional<Person> findByEmailAndRecoveryCode(String email, String recoveryCode);
}