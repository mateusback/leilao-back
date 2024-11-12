package com.mateus_back.leilao.service;

import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.model.request.ChangePasswordPersonRequest;
import com.mateus_back.leilao.model.request.PersonAuthRequest;
import com.mateus_back.leilao.repository.interfaces.IPersonRepository;
import jakarta.mail.MessagingException;
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

    public Person create(Person person) {
        Person personSaved = personRepository.save(person);


        Context context = new Context();
        context.setVariable("name", personSaved.getName());
        try {
            emailService.sendTemplateEmail(
                    personSaved.getEmail(),
                    "Cadastro Efetuado com Sucesso", context,
                    "emailWelcome");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return personSaved;
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

    public Person confirmarCadastro(String email, String validationCode) {
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));

        person.confirmRegistration(validationCode);
        return personRepository.save(person);
    }

    public String changePassword(ChangePasswordPersonRequest request){
        Person person = personRepository.findByEmailAndRecoveryCode(request.getEmail(), request.getRecoveryCode())
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));

        person.setPassword(request.getNewPassword());
        personRepository.save(person);
        return "Senha alterada com sucesso";
    }

    public String sendRecoveryCode(PersonAuthRequest request){
        Person person = personRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));

        person.generateValidationCode();
        personRepository.save(person);
        emailService.sendSimpleEmail(person.getEmail(), "Código de validação", person.getValidationCode());
        return "Código de validação enviado para o email";
    }

}