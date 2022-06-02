package ru.sferum.book_store.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private static int idSequence = 0;

    public Product() {
        this.id = idSequence++;
    }

    public Product(Book book, int price, int amount) {
        this.id = idSequence++;
        this.book = book;
        this.price = price;
        this.amount = amount;
    }

    private int id;

    private Book book;

    private int price;

    private int amount;
}
