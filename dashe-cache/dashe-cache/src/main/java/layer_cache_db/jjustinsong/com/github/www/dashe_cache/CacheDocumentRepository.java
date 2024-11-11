package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import layer_cache_db.jjustinsong.com.github.www.dashe_cache.CacheDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CacheDocumentRepository extends MongoRepository<CacheDocument, String> {
    // Additional query methods, if needed
}
