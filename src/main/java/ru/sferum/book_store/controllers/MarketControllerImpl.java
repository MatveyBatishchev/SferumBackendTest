package ru.sferum.book_store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.sferum.book_store.models.Market;
import ru.sferum.book_store.services.MarketServiceImpl;

@RestController
public class MarketControllerImpl implements MarketController {

    private final MarketServiceImpl marketService;

    @Autowired
    public MarketControllerImpl(MarketServiceImpl marketService) {
        this.marketService = marketService;
    }

    @Override
    public Market show() {
        return marketService.find();
    }

    @Override
    public ResponseEntity<String> createDeal(String deal) {
        return marketService.createDeal(deal);
    }

}
