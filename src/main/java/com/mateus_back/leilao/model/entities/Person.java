package com.mateus_back.leilao.model.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@Table(name = "person")
@JsonIgnoreProperties({"authorities"})
public class Person implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{name.required}")
    private String name;

    @Email(message = "{name.invalid}")
    private String email;

    @CPF
    private String cpf;

    @Min(0)
    private int idade;

    @Setter(value = AccessLevel.NONE)
    private boolean confirmado;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @JsonIgnore
    @Column(name = "validation_code")
    private String validationCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validationCodeValidity;

    @OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter(value = AccessLevel.NONE)
    private List<PersonProfile> personProfile;

    public void confirmRegistration(String validationCode) {
        if (this.isConfirmado()) {
            throw new IllegalArgumentException("Usuário já confirmado");
        }
        if (this.getValidationCode() == null) {
            throw new IllegalArgumentException("Código de validação não encontrado");
        }
        if (!this.getValidationCode().equals(validationCode)) {
            throw new IllegalArgumentException("Código de validação inválido");
        }

        this.confirmado = true;
    }

    public void generateValidationCode() {
        this.validationCode = "aula";
        this.validationCodeValidity = new Date(new Date().getTime() + (20*60*1000));
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Senha inválida");
        }
        if(passwordEncoder.matches(password, this.password)) {
            throw new IllegalArgumentException("Senha já cadastrada");
        }

        this.password = passwordEncoder.encode(password);
    }

    public void setPersonProfile(List<PersonProfile> lpp) {
        for (PersonProfile p : lpp) {
            p.setPerson(this);
        }
        personProfile = lpp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return personProfile.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getProfile().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
