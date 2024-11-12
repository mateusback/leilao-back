package com.mateus_back.leilao.model.request;

import lombok.Getter;

@Getter
public class ChangePasswordPersonRequest {
    private String email;
    private String newPassword;
    private String recoveryCode;
}