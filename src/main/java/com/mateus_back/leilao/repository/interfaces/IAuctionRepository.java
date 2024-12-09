package com.mateus_back.leilao.repository.interfaces;

import com.mateus_back.leilao.model.entities.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuctionRepository extends JpaRepository<Auction, Long> {

}
