package com.mateus_back.leilao.model.request;

import lombok.Getter;

@Getter
public class PersonRegisterRequest {
    String name;
    String email;
    int age;
    String cpf;
    String password;
}
