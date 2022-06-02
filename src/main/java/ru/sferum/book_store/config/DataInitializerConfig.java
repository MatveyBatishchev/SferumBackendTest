package ru.sferum.book_store.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import ru.sferum.book_store.models.Account;
import ru.sferum.book_store.models.Market;
import ru.sferum.book_store.models.Product;
import ru.sferum.book_store.serilalizers.ProductDeserializer;
import ru.sferum.book_store.util.JsonIO;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
public class DataInitializerConfig implements ApplicationRunner {

    private final JsonIO jsonIO;

    private final static ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(DataInitializerConfig.class);

    @Value("${filepath.json.account}")
    private String accountFile;

    @Value("${filepath.json.products}")
    private String productsFile;

    @Autowired
    public DataInitializerConfig(JsonIO jsonIO) {
        this.jsonIO = jsonIO;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {

        if (args.getSourceArgs().length > 0) {
            File dataFile = new File(args.getSourceArgs()[0]);
            logger.info("Initial json data file has been initialized with path " + args.getSourceArgs()[0]);

            mapper.registerModule(new SimpleModule().addDeserializer(Product.class, new ProductDeserializer()));

            JsonNode node = mapper.readTree(dataFile);
            Account account = mapper.treeToValue(node.get("account"), Account.class);
            List<Product> products = mapper.treeToValue(node.get("books"), mapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            logger.debug("Initial data file has been succesfully parsed");

            jsonIO.writeValueToFile(accountFile, account);
            jsonIO.writeValueToFile(productsFile, new Market(products));
            logger.debug("Application data files hav been initilized and  filled");
        }
        else {
            logger.error("Initial json data file has not been initialized", new IllegalArgumentException("no file.json found in args"));
        }


    }
}
