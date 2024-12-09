package com.mateus_back.leilao.model.request;

import com.mateus_back.leilao.model.entities.Image;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
public class AuctionRequest {
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
    private Set<Image> images;
}
