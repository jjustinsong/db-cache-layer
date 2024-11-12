package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoService {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    @Autowired
    public MongoService(MongoConfig config) {
        this.mongoClient = MongoClients.create(config.getMongoUri());
        this.database = mongoClient.getDatabase(config.getDatabaseName());
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        mongoClient.close();
    }
}
