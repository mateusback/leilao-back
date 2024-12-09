package com.mateus_back.leilao.controller;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.model.entities.Auction;
import com.mateus_back.leilao.model.request.AuctionRequest;
import com.mateus_back.leilao.service.AuctionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auction")
@Tag(name = "Auction", description = "Controller responsável por gerenciar leilões")
@CrossOrigin
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public ResponseEntity<ActionResult> create(@RequestBody AuctionRequest auctionRequest) {
        return auctionService.create(auctionRequest);
    }

    @GetMapping
    public List<Auction> listAll() {
        return auctionService.listAll();
    }

    @GetMapping("/{id}")
    public Auction findById(@PathVariable Long id) {
        return auctionService.findById(id);
    }
}
