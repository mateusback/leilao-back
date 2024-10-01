package com.mateus_back.leilao.service;

import com.mateus_back.leilao.model.Person;
import com.mateus_back.leilao.repository.interfaces.IPersonRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonService {

    @Autowired
    private IPersonRespository personRepository;

    public Person register(Person person) {
        return personRepository.save(person);
    }

    public Person update(Person person) {
        var savedPerson = personRepository.findById(person.getId())
                .orElseThrow(() -> new NoSuchElementException("Person not found"));
        savedPerson.setName(person.getName());
        return personRepository.save(savedPerson);
    }

    public Person delete(Long id) {
        var savedPerson = personRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Person not found"));
        personRepository.deleteById(id);
        return savedPerson;
    }
}
