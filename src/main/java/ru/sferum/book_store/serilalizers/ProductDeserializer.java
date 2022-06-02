package ru.sferum.book_store.serilalizers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.sferum.book_store.models.Book;
import ru.sferum.book_store.models.Product;

import java.io.IOException;

public class ProductDeserializer extends StdDeserializer<Product> {

        public ProductDeserializer() {
            this(null);
        }

        public ProductDeserializer(Class<Product> vc) {
            super(vc);
        }

        @Override
        public Product deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            String author = node.get("author").asText();
            String name = node.get("name").asText();
            int price = node.get("price").asInt();
            int amount = node.get("amount").asInt();
            return new Product(new Book(author, name), price, amount);
        }
}
