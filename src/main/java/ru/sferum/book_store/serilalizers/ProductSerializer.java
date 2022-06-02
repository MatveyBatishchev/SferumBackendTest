package ru.sferum.book_store.serilalizers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.sferum.book_store.models.Product;
import java.io.IOException;

public class ProductSerializer extends StdSerializer<Product> {

    public ProductSerializer() {
        this(null);
    }

    public ProductSerializer(Class<Product> t) {
        super(t);
    }

    @Override
    public void serialize(Product value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value.getAmount() != 0) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", value.getId());
            provider.defaultSerializeField("book", value.getBook(), jgen);
            jgen.writeNumberField("price", value.getPrice());
            jgen.writeNumberField("amount", value.getAmount());
            jgen.writeEndObject();
        }
    }
}