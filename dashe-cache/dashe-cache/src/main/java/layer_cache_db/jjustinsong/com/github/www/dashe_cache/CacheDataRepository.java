package com.yourpackage.repository;

import com.yourpackage.model.CacheData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDataRepository extends MongoRepository<CacheData, String> {
    // Custom query methods can be defined here if needed
}
