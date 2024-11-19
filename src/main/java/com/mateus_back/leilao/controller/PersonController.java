package com.mateus_back.leilao.controller;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.config.security.JwtService;
import com.mateus_back.leilao.model.request.*;
import com.mateus_back.leilao.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/person")
@Tag(name = "Person", description = "Controller responsável por registros de usuários")
@CrossOrigin
public class PersonController {

    private final PersonService personService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public PersonController(JwtService jwtService, AuthenticationManager authenticationManager, PersonService personService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<ActionResult> create(@Valid @RequestBody PersonRegisterRequest person) {
         return personService.create(person);
    }

    @PostMapping("/login")
    public ResponseEntity<ActionResult> authenticateUser(@RequestBody PersonAuthRequest authRequest) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(), authRequest.getPassword()));
            if(!personService.isUserConfirmed(authRequest.getEmail()))
                return ActionResult.returnUnauthorized("Usuário não confirmado");

            return ActionResult.returnSuccess("Token Gerado com sucesso!",
                    jwtService.generateToken(authentication.getName()));
        } catch (BadCredentialsException bcEx){
            return ActionResult.returnBadRequest(bcEx.getMessage());
        }
    }

    @PostMapping("/recover-password")
    public ResponseEntity<ActionResult> recoverPassword(@RequestBody RecoverPasswordRequest request) {
        return personService.sendRecoveryCode(request);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ActionResult> changePassword(@RequestBody ChangePasswordPersonRequest request) {
        return personService.changePassword(request);
    }

    @PatchMapping("/confirm-registration")
    public ResponseEntity<ActionResult> confirmRegistration(@RequestBody ConfirmRegistrationRequest request) {
        return personService.confirmRegistration(request.getEmail(), request.getValidationCode());
    }
}