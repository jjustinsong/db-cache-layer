# Dashe Cache

Dashe Cache is a Spring Boot-based database query caching package designed to enhance application performance by leveraging Redis for caching database queries. It provides a generic and reusable caching mechanism for MongoDB repositories, ensuring efficient data retrieval and reduced latency.

## Features
- Generic Caching Service: Easily cache any MongoDB entity with minimal configuration.
- Redis Integration: Utilizes Redis for high-performance caching.
- Configurable TTL: Set default Time-To-Live (TTL) for cached entries.
- Seamless Spring Boot Integration: Leverages Spring's caching abstraction for effortless integration.
- Flexible Repository Support: Compatible with any repository extending `GenericRepository`.

## Prerequisites
Before integrating Dashe Cache into your project, ensure you have the following:

- Java 8 or higher
- Spring Boot 2.5.x or higher
- MongoDB: For data persistence.
- Redis: For caching mechanisms.
- Maven or Gradle: For dependency management.

## Installation
Add the Dashe Cache package to your project dependencies. If the package is hosted on a Maven repository, include the following in your `pom.xml`:
```
<dependency>
    <groupId>com.yourcompany</groupId>
    <artifactId>layer-cache-db</artifactId>
    <version>1.0.0</version>
</dependency>
```
For Gradle, add the following to your `build.gradle`:
```
implementation 'com.yourcompany:layer-cache-db:1.0.0'
```

## Configuration
### Redis Configuration
You can customize Redis settings in your `application.properties` or `application.yml` as needed. Example configurations:
```
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=yourpassword
spring.redis.timeout=60000
```
### MongoDB Configuration
Ensure MongoDB is properly configured in your `application.properties` or `application.yml`:
```
spring.data.mongodb.uri=mongodb://localhost:27017/yourdatabase
```

## Usage
Dashe Cache provides a straightforward way to integrate caching into your Spring Boot application with minimal setup. Follow the steps below to get started.
### Defining Entities
Create your MongoDB entity by annotating it with `@Document`. For example, let's define a `CacheDocument` entity:
```
package com.yourapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class CacheDocument {
    @Id
    private String id;
    private String name;

    public CacheDocument() {}

    // Getters and Setters...

    @Override
    public String toString() {
        return id + ": " + name;
    }
}
```
### Creating Repositories
Extend the `GenericRepository` interface to create a repository for your entity. For `CacheDocument`, the repository would look like this:
```
package com.yourapp.repository;

import com.yourapp.model.CacheDocument;
import layer_cache_db.GenericRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDocumentRepository extends GenericRepository<CacheDocument, ObjectId> {
    // Additional custom methods can be defined here
}
```
### Implementing Caching Service
Utilize the `GenericCacheableService` to manage cached operations for your entity. Here's how to implement a service for `CacheDocument`:
```
package com.yourapp.service;

import com.yourapp.model.CacheDocument;
import com.yourapp.repository.CacheDocumentRepository;
import layer_cache_db.GenericCacheableService;
import layer_cache_db.RedisCacheService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class CacheDocumentService extends GenericCacheableService<CacheDocument, ObjectId> {

    public CacheDocumentService(CacheDocumentRepository repository, RedisCacheService cacheService) {
        super(repository, cacheService, CacheDocument.class);
    }

    // Additional business logic methods can be added here
}
```
### Using the Service
Inject the ```CacheDocumentService``` into your controllers or other services to perform cached operations:
```
package com.yourapp.controller;

import com.yourapp.model.CacheDocument;
import com.yourapp.service.CacheDocumentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cache-documents")
public class CacheDocumentController {

    private final CacheDocumentService cacheDocumentService;

    @Autowired
    public CacheDocumentController(CacheDocumentService cacheDocumentService) {
        this.cacheDocumentService = cacheDocumentService;
    }

    @GetMapping("/{id}")
    public CacheDocument getCacheDocument(@PathVariable ObjectId id) {
        return cacheDocumentService.findById(id);
    }

    // Additional endpoints (create, update, delete) can be added here
}
```
## API Reference
### `GenericCacheableService<T, ID>`
A generic service class for managing cacheable operations.


**Constructor**
```
public GenericCacheableService(GenericRepository<T, ID> repository,
                                RedisCacheService cacheService,
                                Class<T> type)
```
- `repository`: The repository for CRUD operations.
- `cacheService`: The RedisCacheService for cache operations.
- `type`: The Class type of the entity.

**Methods**
- `T findById(ID id)`: Retrieves an entity by its identifier. It first checks the cache; if not found, it fetches from the database and caches the result.
```T entity = genericCacheableService.findById(id);```
### `GenericRepository<T, ID>`
A generic repository interface extending Spring Data's `MongoRepository` for CRUD operations.


**Example**
```
@Repository
public interface CacheDocumentRepository extends GenericRepository<CacheDocument, ObjectId> {
    // Custom query methods
}
```
### `RedisCacheService`
Service class for managing Redis cache operations.


**Methods**
- `<T> T getFromCache(String cacheName, String key, Class<T> type)`: Retrieves an object from a specific cache.
- `<T> void saveToCache(String cacheName, String key, T data, long ttlInSeconds)`: Saves an object to a specific cache with an expiration time.
- `void delete(String cacheName, String key)`: Deletes a cached object by key from a specific cache.
- `boolean exists(String cacheName, String key)`: Checks if a key exists in a specific cache.
- `void expire(String cacheName, String key, long ttl)`: Sets the expiration time for a key in a specific cache. *Note: TTL configuration per key may require additional setup.*

### `RedisConfig`
The `RedisConfig` class is part of the Dashe Cache package and is automatically configured. Users do not need to create or modify this class. It handles the necessary Redis configurations, including serializers and cache manager settings.
