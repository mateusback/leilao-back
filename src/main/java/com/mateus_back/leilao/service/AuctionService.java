package com.mateus_back.leilao.service;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.model.entities.Auction;
import com.mateus_back.leilao.model.entities.Category;
import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.model.request.AuctionEditRequest;
import com.mateus_back.leilao.model.request.AuctionRequest;
import com.mateus_back.leilao.repository.interfaces.IAuctionRepository;
import com.mateus_back.leilao.repository.interfaces.ICategoryRepository;
import com.mateus_back.leilao.repository.interfaces.IPersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final IAuctionRepository auctionRepository;
    private final ICategoryRepository categoryRepository;
    private final IPersonRepository personRepository;

    public AuctionService(IAuctionRepository auctionRepository,
                          ICategoryRepository categoryRepository,
                          IPersonRepository personRepository) {
        this.auctionRepository = auctionRepository;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
    }
    public ResponseEntity<ActionResult> create(AuctionRequest request) {
        var auction = auctionRepository.save(ToEntity(request));
        return ActionResult.returnSuccess("Leilão criado com sucesso", auction);
    }

    public ResponseEntity<ActionResult> update(AuctionEditRequest request){
        Auction auction = auctionRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Leilão não encontrado"));

        auction.setTitle(request.getTitle());
        auction.setDescription(request.getDescription());
        auction.setStartDateTime(request.getStartDateTime());
        auction.setEndDateTime(request.getEndDateTime());
        auction.setStatus(request.getStatus());
        auction.setObservation(request.getObservation());
        auction.setIncrementValue(request.getIncrementValue());
        auction.setMinimumBid(request.getMinimumBid());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        auction.setCategory(category);

        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        auction.setPerson(person);

        auctionRepository.save(auction);
        return ActionResult.returnSuccess("Leilão atualizado com sucesso", auction);
    }


    private Auction ToEntity(AuctionRequest request) {
        var auction = Auction.builder().title(request.getTitle())
                .description(request.getDescription())
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .status(request.getStatus())
                .observation(request.getObservation())
                .incrementValue(request.getIncrementValue())
                .minimumBid(request.getMinimumBid())
                .build();

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        auction.setCategory(category);

        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        auction.setPerson(person);

        auction.setImages(request.getImages());
        return auction;
    }

    public List<Auction> listAll() {
        return auctionRepository.findAll();
    }
}
