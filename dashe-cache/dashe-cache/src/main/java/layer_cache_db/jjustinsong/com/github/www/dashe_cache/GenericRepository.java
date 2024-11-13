package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends MongoRepository<T, ID> {
}