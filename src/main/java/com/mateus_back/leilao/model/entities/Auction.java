package com.mateus_back.leilao.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "auction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private Date startDateTime;
    @Column(name = "end_date")
    private Date endDateTime;
    @Column(name = "status")
    private String status;
    @Column(name = "observation")
    private String observation;
    @Column(name = "increment_value")
    private Float incrementValue;
    @Column(name = "minimum_bid")
    private Float minimumBid;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "auction")
    private Set<Image> images;
    @OneToMany(mappedBy = "auction")
    private Set<Bid> bids;
}
