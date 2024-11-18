package com.mateus_back.leilao.service;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.model.builders.PersonBuilder;
import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.model.request.ChangePasswordPersonRequest;
import com.mateus_back.leilao.model.request.RecoverPasswordRequest;
import com.mateus_back.leilao.model.request.PersonRegisterRequest;
import com.mateus_back.leilao.repository.interfaces.IPersonRepository;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.NoSuchElementException;

@Service
public class PersonService implements UserDetailsService {

    private final IPersonRepository personRepository;
    private final EmailService emailService;

    public PersonService(EmailService emailService, IPersonRepository personRepository) {
        this.emailService = emailService;
        this.personRepository = personRepository;
    }

    public ResponseEntity<ActionResult> create(PersonRegisterRequest request) {
        var personEntity = toEntity(request);
        personEntity.generateValidationCode();
        Person personSaved = personRepository.save(personEntity);

        Context context = new Context();
        context.setVariable("name", personSaved.getName());
        context.setVariable("validationCode", personSaved.getValidationCode());
        try {
            emailService.sendTemplateEmail(
                    personSaved.getEmail(),
                    "Cadastro Efetuado com Sucesso", context,
                    "emailWelcome");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ActionResult.returnSuccess("Cadastro efetuado com sucesso", personSaved);
    }

    public Person update(Person person) {
        Person personSaved = personRepository.findById(person.getId())
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));

        personSaved.setName(person.getName());
        personSaved.setEmail(person.getEmail());

        return personRepository.save(personSaved);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public boolean isUserConfirmed(String email) {
        return personRepository.findByEmail(email)
                .map(Person::isConfirmado)
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));
    }

    public Person confirmRegistration(String email, int validationCode) {
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));

        person.confirmRegistration(validationCode);
        return personRepository.save(person);
    }

    public ResponseEntity<ActionResult> changePassword(ChangePasswordPersonRequest request){
        Person person = personRepository.findByEmailAndValidationCode(request.getEmail(), request.getRecoveryCode())
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));

        person.setPassword(request.getNewPassword());
        personRepository.save(person);
        return ActionResult.returnSuccess("Senha alterada com sucesso", null);
    }

    public ResponseEntity<ActionResult> sendRecoveryCode(RecoverPasswordRequest request){
        Person person = personRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));

        person.generateValidationCode();
        personRepository.save(person);
        emailService.sendSimpleEmail(person.getEmail(), "Código de validação", person.getValidationCode() + "");
        return ActionResult.returnSuccess("Código de validação enviado para o email", null);
    }


    private Person toEntity(PersonRegisterRequest request) {
        return PersonBuilder.builder()
                .withName(request.getNome())
                .withEmail(request.getEmail())
                .withCpf(request.getCpf())
                .withIdade(request.getIdade())
                .withPassword(request.getSenha())
                .build();
    }

}