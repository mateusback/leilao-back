package com.mateus_back.leilao.repository.interfaces;

import com.mateus_back.leilao.model.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfileRepository extends JpaRepository<Profile, Long> {
}
