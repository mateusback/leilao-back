package com.mateus_back.leilao.repository.interfaces;

import com.mateus_back.leilao.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRespository extends JpaRepository<Person, Long> {
}
