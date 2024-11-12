package com.mateus_back.leilao.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonAuthResponse extends BaseResponse {
    private String email;
    private String token;

    public PersonAuthResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public PersonAuthResponse() {
    }
}
