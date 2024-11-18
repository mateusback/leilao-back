package com.mateus_back.leilao.model.request;

import lombok.Getter;

@Getter
public class PersonRegisterRequest {
    String nome;
    String email;
    int idade;
    String Cpf;
    String senha;
}
