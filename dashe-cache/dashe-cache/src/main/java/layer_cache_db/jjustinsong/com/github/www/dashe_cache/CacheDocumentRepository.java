package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import com.yourpackage.model.CacheData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDocumentRepository extends MongoRepository<CacheDocument, String> {
    // Custom query methods can be defined here if needed
}
