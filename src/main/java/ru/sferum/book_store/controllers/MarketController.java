package ru.sferum.book_store.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sferum.book_store.models.Market;

@RequestMapping("/market")
public interface MarketController {

    @GetMapping(produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Market show();

    @PostMapping(value = "/deal", consumes="application/json")
    ResponseEntity<String> createDeal(@RequestBody String deal);

}
