package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import layer_cache_db.jjustinsong.com.github.www.dashe_cache.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    // Additional query methods, if needed
}
