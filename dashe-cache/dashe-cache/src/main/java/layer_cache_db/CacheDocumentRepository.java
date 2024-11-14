package layer_cache_db;

import layer_cache_db.CacheDocument;
import layer_cache_db.GenericRepository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDocumentRepository extends GenericRepository<CacheDocument, ObjectId> {
    // Additional custom methods can be defined here
}
