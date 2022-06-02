package ru.sferum.book_store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.sferum.book_store.models.Account;
import ru.sferum.book_store.services.AccountServiceImpl;

@RestController
public class AccountControllerImpl implements AccountController {

    private final AccountServiceImpl accountService;

    @Autowired
    public AccountControllerImpl(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public Account show() {
        return accountService.find();
    }

}
