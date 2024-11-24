package com.mateus_back.leilao.model.request;

import lombok.Getter;

@Getter
public class ConfirmRegistrationRequest {
    private String email;
    private int passcode;
}
