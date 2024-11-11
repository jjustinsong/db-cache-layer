package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

public interface CacheManager {
    <T> T get(String key, Class<T> type);
    <T> void set(String key, T data, long ttl);
    void delete(String key);
}
