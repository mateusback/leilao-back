package com.mateus_back.leilao.model.request;

import lombok.Data;

@Data
public class RecoverPasswordRequest {
    private String email;
}