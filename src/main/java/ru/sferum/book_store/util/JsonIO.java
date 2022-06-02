package ru.sferum.book_store.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Paths;

@Component
public class JsonIO {

    private final Logger logger = LoggerFactory.getLogger(JsonIO.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> T readValueFromFile(String filename, Class<T> valueClass) {
        try {
            return mapper.readValue(Paths.get(filename).toFile(), valueClass);
        } catch (IOException e) {
            logger.error("Error reading object" + valueClass + "from json file " + filename);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading from json file", e);
        }
    }

    public void writeValueToFile(String filename, Object value) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(filename).toFile(), value);
        } catch (IOException e) {
            logger.error("Error writing " + value.getClass() + " object to json file " + filename);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error writing to json file", e);
        }
    }

}
