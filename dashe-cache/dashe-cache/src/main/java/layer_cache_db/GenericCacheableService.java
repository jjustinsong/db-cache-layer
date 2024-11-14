package layer_cache_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Generic service class for managing cacheable operations.
 *
 * @param <T>  The type of the entity.
 * @param <ID> The type of the entity's identifier.
 */
@Service
public class GenericCacheableService<T, ID> {

    private final GenericRepository<T, ID> repository;
    private final RedisCacheService cacheService;
    private final long defaultTtl = 3600; // Default cache time-to-live in seconds
    // private final Class<T> type;
    private final String cacheName;

    /**
     * Constructor for GenericCacheableService.
     *
     * @param repository   The repository for CRUD operations.
     * @param cacheService The RedisCacheService for cache operations.
     * @param type         The Class type of the entity.
     */
    @Autowired
    public GenericCacheableService(GenericRepository<T, ID> repository,
            RedisCacheService cacheService) {
        this.repository = repository;
        this.cacheService = cacheService;
        this.cacheName = "generic_cache"; // Define a consistent cache name or parameterize as needed
    }

    /**
     * Finds an entity by ID, utilizing the cache.
     *
     * @param id The identifier of the entity.
     * @return The entity if found, otherwise null.
     */
    public T findById(ID id) {
        String key = "cache:" + id.toString();
        T cachedData = cacheService.getFromCache(cacheName, key, type);
        if (cachedData != null) {
            return cachedData;
        } else {
            T data = repository.findById(id).orElse(null);
            if (data != null) {
                cacheService.saveToCache(cacheName, key, data, defaultTtl);
            }
            return data;
        }
    }
}
