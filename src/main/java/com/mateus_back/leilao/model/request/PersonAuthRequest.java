package com.mateus_back.leilao.model.request;

import lombok.Data;

@Data
public class PersonAuthRequest {
    private String email;
    private String password;
}