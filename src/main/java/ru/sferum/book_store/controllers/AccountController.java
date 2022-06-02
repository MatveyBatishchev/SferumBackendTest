package ru.sferum.book_store.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sferum.book_store.models.Account;

@RequestMapping("/account")
public interface AccountController {

    @GetMapping(produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Account show();

}
