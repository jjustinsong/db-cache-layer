package layer_cache_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import layer_cache_db.CacheDocument;
import layer_cache_db.CacheDocumentRepository;
import layer_cache_db.GenericCacheableService;
import org.bson.types.ObjectId;

@Service
public class UserProfileService {

    private final GenericCacheableService<CacheDocument, ObjectId> cacheableDataService;

    @Autowired
    public UserProfileService(CacheDocumentRepository userProfileRepository, RedisCacheService redisCacheService) {
        this.cacheableDataService = new GenericCacheableService<>(userProfileRepository, redisCacheService, CacheDocument.class);
    }

    public CacheDocument getUserProfileById(String id) {
        ObjectId objectId = new ObjectId(id);
        return cacheableDataService.findById(objectId);
    }
}
