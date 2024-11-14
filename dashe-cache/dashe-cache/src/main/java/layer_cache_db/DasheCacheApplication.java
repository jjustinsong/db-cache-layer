package layer_cache_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class DasheCacheApplication implements CommandLineRunner {
    private final UserProfileService userProfileService;

    @Autowired
    public DasheCacheApplication(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DasheCacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Example usage of UserProfileService with caching enabled

        // Define an ID for testing purposes
        String userId = "5a9427648b0beebeb69579e7";

        // First retrieval (should fetch from MongoDB and cache the result)
        CacheDocument userProfile = userProfileService.getUserProfileById(userId);
        System.out.println("User profile fetched: " + userProfile);

        // Second retrieval (should fetch directly from cache)
        CacheDocument cachedUserProfile = userProfileService.getUserProfileById(userId);
        System.out.println("User profile fetched from cache: " + cachedUserProfile);
    }
}
