package com.mateus_back.leilao.model.builders;

import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.model.entities.PersonProfile;
import com.mateus_back.leilao.model.entities.Profile;

import java.util.Collections;

public class PersonBuilder {

    private final Person person;

    private PersonBuilder() {
        this.person = new Person();
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public PersonBuilder withName(String name) {
        this.person.setName(name);
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.person.setEmail(email);
        return this;
    }

    public PersonBuilder withCpf(String cpf) {
        this.person.setCpf(cpf);
        return this;
    }

    public PersonBuilder withIdade(int idade) {
        this.person.setIdade(idade);
        return this;
    }

    public PersonBuilder withPassword(String password) {
        this.person.setPassword(password);
        return this;
    }

    public PersonBuilder withDefaultProfile() {
        PersonProfile defaultProfile = new PersonProfile();
        defaultProfile.setProfile(new Profile());
        this.person.setPersonProfile(Collections.singletonList(defaultProfile));
        return this;
    }

    public Person build() {
        return this.person;
    }
}
