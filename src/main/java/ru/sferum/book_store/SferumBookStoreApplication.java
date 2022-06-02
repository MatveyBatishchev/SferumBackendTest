package ru.sferum.book_store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class SferumBookStoreApplication {

    private final static Logger logger = LoggerFactory.getLogger(SferumBookStoreApplication.class);

    public static void main(String[] args) {
        if (args.length > 1) {
            System.setProperty("log.name", args[1]);
            logger.info("Сustom logging file has been set");
        }
        else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
            System.setProperty("log.name", ".logs/sferum-" + dtf.format(LocalDateTime.now()) + "-logger.log");
            logger.info("Default logging file has been set");
        }
        SpringApplication.run(SferumBookStoreApplication.class, args);
    }

}

// java -jar target/app.jar C:/data.json

// java -jar target/app.jar C:/data.json C:/sferum.log

// curl -X POST http://localhost:8080/actuator/shutdown

// curl -X GET http://localhost:8080/account -H Content-Type:application/json

// curl -X GET http://localhost:8080/market -H Content-Type:application/json

// curl -v -X POST -H Content-Type:application/json -d "{""id"":0,""amount"":5}" http://localhost:8080/market/deal