package com.mateus_back.leilao.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class BaseResponse {

    private String message;
    private int status;
    private List<String> errors;
}
