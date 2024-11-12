package com.mateus_back.leilao.controller;

import com.mateus_back.leilao.config.security.JwtService;
import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.model.request.PersonAuthRequest;
import com.mateus_back.leilao.model.response.PersonAuthResponse;
import com.mateus_back.leilao.service.PersonService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final PersonService service;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, PersonService service) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.service = service;
    }

    @PostMapping("/login")
    public PersonAuthResponse authenticateUser(@RequestBody PersonAuthRequest authRequest) {
        if(service.isUserConfirmed(authRequest.getEmail())) {
           var response = new PersonAuthResponse();
            response.setErrors(List.of("Usuário não confirmado"));;
            response.setStatus(401);
            return response;
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword())
        );
        return new PersonAuthResponse(authRequest.getEmail() ,jwtService.generateToken(authentication.getName()));
    }

}