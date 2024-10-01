package com.mateus_back.leilao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "validation_code")
    private String validationCode;

    @Column(name = "validation_code_expiration")
    private LocalDateTime validationCodeExpiration;

    @OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL)
    @Setter(lombok.AccessLevel.NONE)
    @JsonIgnore
    private List<PersonProfile> personProfile;

    public void setPersonProfile(List<PersonProfile> personProfile) {
        this.personProfile = personProfile;
        this.personProfile.forEach(personProfile1 -> personProfile1.setPerson(this));
    }
}
