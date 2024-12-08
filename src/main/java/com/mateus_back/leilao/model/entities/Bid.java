package com.mateus_back.leilao.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bid")
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bid_value")
    private float bidValue;
    @Column(name = "date_time")
    private String dateTime;
}
