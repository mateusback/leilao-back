package com.mateus_back.leilao.controller;

import com.mateus_back.leilao.config.security.JwtService;
import com.mateus_back.leilao.model.request.ChangePasswordPersonRequest;
import com.mateus_back.leilao.model.request.PersonAuthRequest;
import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.service.PersonService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public PersonController(JwtService jwtService, AuthenticationManager authenticationManager, PersonService personService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody PersonAuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword()));
        return jwtService.generateToken(authentication.getName());
    }
    @PostMapping
    public Person create(@Valid @RequestBody Person person) {
        return personService.create(person);
    }

    @PostMapping("/recover-password")
    public String recoverPassword(@RequestBody PersonAuthRequest request) {
        return personService.sendRecoveryCode(request);
    }

    @PatchMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordPersonRequest request) {
        return personService.changePassword(request);
    }

    @PatchMapping("/confirm-registration")
    public Person confirmRegistration(@RequestParam String email, @RequestParam String validationCode) {
        return personService.confirmarCadastro(email, validationCode);
    }

    @PutMapping
    public Person update(@Valid @RequestBody Person person) {
        return personService.create(person);
    }
}