package ru.sferum.book_store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sferum.book_store.models.Account;
import ru.sferum.book_store.util.JsonIO;

@Service
public class AccountServiceImpl implements AccountService {

    private final JsonIO jsonIO;

    @Value("${filepath.json.account}")
    private String accountFile;

    @Autowired
    public AccountServiceImpl(JsonIO jsonIO) {
        this.jsonIO = jsonIO;
    }

    @Override
    public Account find() {
        return jsonIO.readValueFromFile(accountFile, Account.class);
    }

}
