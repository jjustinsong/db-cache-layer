package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheableDataService {

    private static final String USER_CACHE_PREFIX = "user::";
    private static final String DOC_CACHE_PREFIX = "document::";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheDocumentRepository documentRepository;

    public CacheDocument getDocumentById(String documentId) {
        String key = USER_CACHE_PREFIX + documentid;
        CacheDocument cachedDocument = cacheManager.get(key, CacheDocument.class);
        if (cachedDocument != null) {
            return cachedDocument;
        } else {
            CacheDocument document = documentRepository.findById(documentId).orElse(null);
            if (document != null) {
                cacheManager.set(key, document, 3600); // Caching for 1 hour
            }
            return document;
        }
    }
}
