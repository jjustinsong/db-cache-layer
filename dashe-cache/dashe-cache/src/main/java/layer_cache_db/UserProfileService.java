package layer_cache_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import layer_cache_db.CacheDocument;
import layer_cache_db.CacheDocumentRepository;
import layer_cache_db.GenericCacheableService;

@Service
public class UserProfileService {

    private final GenericCacheableService<CacheDocument, String> cacheableDataService;

    @Autowired
    public <T> UserProfileService(CacheDocumentRepository userProfileRepository, RedisCacheService redisCacheService,
            Class<T> type) {
        this.cacheableDataService = new GenericCacheableService<>(userProfileRepository, redisCacheService,
                CacheDocument.class);
    }

    public CacheDocument getUserProfileById(String id) {
        return cacheableDataService.findById(id);
    }
}
