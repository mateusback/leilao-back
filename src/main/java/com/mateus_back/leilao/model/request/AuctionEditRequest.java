package com.mateus_back.leilao.model.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class AuctionEditRequest {
    private Long id;
    private String title;
    private String description;
    private Date startDateTime;
    private Date endDateTime;
    private String status;
    private String observation;
    private Float incrementValue;
    private Float minimumBid;
    private Long personId;
    private Long categoryId;
}
