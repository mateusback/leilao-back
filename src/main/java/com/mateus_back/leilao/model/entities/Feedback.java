package com.mateus_back.leilao.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_name")
    private Date registerDate;
    @Column(name = "rating")
    private int rating;
    @Column(name = "date_time")
    private Date dateTime;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
