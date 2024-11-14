package layer_cache_db;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import layer_cache_db.CacheDocument;
import layer_cache_db.GenericRepository;

@EnableMongoRepositories
public interface CacheDocumentRepository extends GenericRepository<CacheDocument, String> {
    // Additional custom methods can be defined here
}
