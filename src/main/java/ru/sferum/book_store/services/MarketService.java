package ru.sferum.book_store.services;

import org.springframework.http.ResponseEntity;
import ru.sferum.book_store.models.Market;

import java.io.IOException;

public interface MarketService {

    Market find() throws IOException;

    ResponseEntity<String> createDeal(String deal);

}
