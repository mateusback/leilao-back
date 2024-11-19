package com.mateus_back.leilao.service;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.model.request.ChangePasswordPersonRequest;
import com.mateus_back.leilao.model.request.RecoverPasswordRequest;
import com.mateus_back.leilao.model.request.PersonRegisterRequest;
import com.mateus_back.leilao.repository.interfaces.IPersonRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class PersonService implements UserDetailsService {

    private final IPersonRepository personRepository;
    private final EmailService emailService;
    public PersonService(EmailService emailService, IPersonRepository personRepository) {
        this.emailService = emailService;
        this.personRepository = personRepository;
    }

    public ResponseEntity<ActionResult> create(PersonRegisterRequest request) {
        try{
            log.info("Criando usuário com email: {}", request.getEmail());

            var personEntity = toEntity(request);
            personEntity.generateValidationCode();
            Person personSaved = personRepository.save(personEntity);
            log.info("Usuário criado com sucesso, enviando email de confirmação");

            Context context = new Context();
            context.setVariable("name", personSaved.getName());
            context.setVariable("validationCode", personSaved.getValidationCode());

            try {
                emailService.sendTemplateEmail(
                        personSaved.getEmail(),
                        "Cadastro Efetuado com Sucesso", context,
                        "emailWelcome");
            } catch (MessagingException e) {
                log.error("Erro ao enviar email de confirmação de cadastro", e);
            }
            return ActionResult.returnSuccess("Cadastro efetuado com sucesso", personSaved);

        } catch (Exception e) {
            log.error("Erro ao criar usuário", e);
            return ActionResult.returnBadRequest("Erro ao criar usuário, tente novamente mais tarde");
        }
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
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public ResponseEntity<ActionResult> confirmRegistration(String email, int validationCode) {
        try{
            log.info("Confirmando cadastro para o email: {}", email);
            Person person = personRepository.findByEmail(email)
                    .orElse(null);

            if (person == null)
                return ActionResult.returnNotFound("Usuário não encontrado");

            person.confirmRegistration(validationCode);

            log.info("Cadastro confirmado com sucesso");
            return ActionResult.returnSuccess("Cadastro Confirmado com sucesso!", personRepository.save(person));
        } catch (Exception e) {
            log.error("Erro ao confirmar cadastro", e);
            return ActionResult.returnBadRequest("Erro ao confirmar cadastro, tente novamente mais tarde");
        }
    }

    public ResponseEntity<ActionResult> changePassword(ChangePasswordPersonRequest request){
        try{
            log.info("Alterando senha para o email: {}", request.getEmail());
            Person person = personRepository.findByEmailAndValidationCode(request.getEmail(), request.getRecoveryCode())
                    .orElse(null);

            if (person == null)
                return ActionResult.returnBadRequest("Código de validação inválido");

            person.setPassword(request.getNewPassword());
            person.resetValidationCode();
            personRepository.save(person);

            log.info("Senha alterada com sucesso");
            return ActionResult.returnSuccess("Senha alterada com sucesso", null);
        } catch (Exception e) {
            log.error("Erro ao alterar senha", e);
            return ActionResult.returnBadRequest("Erro ao alterar senha, tente novamente mais tarde");
        }
    }

    public ResponseEntity<ActionResult> sendRecoveryCode(RecoverPasswordRequest request){
        try {
            log.info("Recuperando senha para o email: {}", request.getEmail());
            var person = personRepository.findByEmail(request.getEmail()).orElse(null);

            if(person == null)
                return ActionResult.returnNotFound("Usuário não encontrado.");

            person.generateValidationCode();
            personRepository.save(person);
            log.info("Código de validação gerado e persistido");

            Context context = new Context();
            context.setVariable("name", person.getName());
            context.setVariable("validationCode", person.getValidationCode());

            try {
                emailService.sendTemplateEmail(
                        person.getEmail(),
                        "Código de alteração de senha", context,
                        "index");
            } catch (MessagingException e) {
                log.error("Erro ao enviar email de recuperação de senha", e);
            }

            log.info("Email de recuperação de senha enviado");
            return ActionResult.returnSuccess("Código de validação enviado para o email", null);

        } catch (Exception e) {
            log.error("Erro ao enviar email de recuperação de senha", e);
            return ActionResult.returnBadRequest("Erro ao enviar email de recuperação de senha, tente novamente mais tarde");
        }
    }

    //region private
    private Person toEntity(PersonRegisterRequest request) {
        return Person.builder()
                .name(request.getNome())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .idade(request.getIdade())
                .password(request.getSenha())
                .build();
    }

    //endregion
}