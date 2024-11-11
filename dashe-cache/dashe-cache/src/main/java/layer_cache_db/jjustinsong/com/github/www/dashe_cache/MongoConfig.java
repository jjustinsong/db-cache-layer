package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    /**
     * Configures the MongoClientSettings with custom settings.
     *
     * @return Configured MongoClientSettings.
     */
    @Bean
    public MongoClientSettings mongoClientSettings() {
        // Example: Custom connection settings
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .build();
    }

    /**
     * Configures the MongoDatabaseFactory using the MongoClientSettings.
     *
     * @param mongoClientSettings The MongoClientSettings bean.
     * @return Configured MongoDatabaseFactory.
     */
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClientSettings mongoClientSettings) {
        return new SimpleMongoClientDatabaseFactory(mongoClientSettings, "demo_db");
    }

    /**
     * Configures the MongoTemplate bean for MongoDB operations.
     *
     * @param mongoDatabaseFactory The MongoDatabaseFactory bean.
     * @return Configured MongoTemplate.
     */
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
