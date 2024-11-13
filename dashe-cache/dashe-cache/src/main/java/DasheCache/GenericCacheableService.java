package DasheCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericCacheableService<T, ID> {

    private final GenericRepository<T, ID> repository;
    private final RedisCacheManager cacheManager;
    private final long defaultTtl = 3600; // Default cache time-to-live in seconds

    @Autowired
    public GenericCacheableService(GenericRepository<T, ID> repository, RedisCacheManager cacheManager) {
        this.repository = repository;
        this.cacheManager = cacheManager;
    }

    public T findById(ID id) {
        String key = "cache:" + id.toString();
        T cachedData = cacheManager.getFromCache(key, (Class<T>) id.getClass());
        if (cachedData != null) {
            return cachedData;
        } else {
            T data = repository.findById(id).orElse(null);
            if (data != null) {
                cacheManager.saveToCache(key, data, defaultTtl);
            }
            return data;
        }
    }
}