package com.mateus_back.leilao.model.request;

import lombok.Getter;

@Getter
public class AddCategoryRequest {
    private String name;
    private String observation;
    private long personId;
}
