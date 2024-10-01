package com.mateus_back.leilao.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "person_profile")
@Data
public class PersonProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
