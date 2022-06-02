package ru.sferum.book_store.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.sferum.book_store.models.Account;
import ru.sferum.book_store.models.CartItem;
import ru.sferum.book_store.models.Market;
import ru.sferum.book_store.models.Product;
import ru.sferum.book_store.util.JsonIO;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MarketServiceImpl implements MarketService {

    private final JsonIO jsonIO;

    private final Logger logger = LoggerFactory.getLogger(MarketServiceImpl.class);

    @Value("${filepath.json.account}")
    private String accountFile;

    @Value("${filepath.json.products}")
    private String productsFile;

    @Autowired
    public MarketServiceImpl(JsonIO jsonIO) {
        this.jsonIO = jsonIO;
    }

    @Override
    public Market find() {
        Market market = jsonIO.readValueFromFile(productsFile, Market.class);
        market.setProducts(market.getProducts().stream().filter(p -> p.getAmount() > 0).collect(Collectors.toList()));
        return market;
    }

    @Override
    public ResponseEntity<String> createDeal(String deal)  {
        Market market = find();
        Account account = jsonIO.readValueFromFile(accountFile, Account.class);

        ObjectNode node;
        int id;
        int amount;

        try {
            node = new ObjectMapper().readValue(deal, ObjectNode.class);
            id = node.get("id").asInt();
            amount = node.get("amount").asInt();
        } catch (Exception e) {
            logger.error("Can not process json request parameter " + deal);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not process json request parameter, appropriate example {\"id\": 0, \"amount\": 2}", e);
        }

        Product product = market.getProducts().stream().filter(elem -> elem.getId()==id).findAny().orElse(null);

        if (product == null) {
            logger.debug("No object with such id, return 404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no book with this id");
        }
        if (product.getPrice() * amount > account.getBalance()) {
            logger.debug("Not enough money on the account, return 404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is not enough money on the account to complete the deal");
        }
        if (product.getAmount() < amount) {
            logger.debug("Not enough product amount, return 404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is not enough product in stock to complete the deal, maximum=" + product.getAmount());
        }

        if (account.getCartItems() == null) account.setCartItems(new ArrayList<>());
        product.setAmount(product.getAmount() - amount);

        boolean isModified = false;
        for (CartItem cartItem : account.getCartItems()) {
            if (cartItem.getBook().equals(product.getBook())) {
                cartItem.setAmount(cartItem.getAmount() + amount);
                isModified = true;
                break;
            }
        }
        if (!isModified)
            account.getCartItems().add(new CartItem(product.getBook(), amount));

        account.setBalance(account.getBalance() - amount * product.getPrice());

        jsonIO.writeValueToFile(accountFile, account);
        jsonIO.writeValueToFile(productsFile, market);
        logger.debug("Successful market user deal");
        logger.debug("Data files has been modified");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
