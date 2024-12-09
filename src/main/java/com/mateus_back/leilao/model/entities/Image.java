package com.mateus_back.leilao.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "image")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false, updatable = false)
    private Date registerDate;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    @JsonBackReference
    private Auction auction;

    public Image(String imageName, String imagePath, Auction auction) {
        this.registerDate = new Date();
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.auction = auction;
    }
}
