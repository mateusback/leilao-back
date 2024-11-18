package com.mateus_back.leilao.model.response;

import lombok.Data;

@Data
public class PersonAuthResponse {
    private String email;
    private String token;

    public PersonAuthResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public PersonAuthResponse() {
    }
}
