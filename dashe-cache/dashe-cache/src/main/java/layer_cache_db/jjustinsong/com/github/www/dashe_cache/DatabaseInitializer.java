import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

public class DatabaseInitializer implements CommandLineRunner {
    private final MongoTemplate mongoTemplate;

    public DatabaseInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the collection exists; if not, create it
        if (!mongoTemplate.collectionExists("cacheLayer")) {
            mongoTemplate.createCollection("cacheLayer");
            System.out.println("Collection 'cacheLayer' created in MongoDB.");
        }
    }
}
