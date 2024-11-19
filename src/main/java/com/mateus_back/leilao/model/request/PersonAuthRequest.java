package com.mateus_back.leilao.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonAuthRequest {
    String email;
    String password;
}
