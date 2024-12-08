package com.mateus_back.leilao.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "image")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "register_date")
    private Date registerDate;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;
}
