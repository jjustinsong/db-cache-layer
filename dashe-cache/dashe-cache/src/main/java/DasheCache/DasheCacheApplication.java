package DasheCache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@EnableCaching
public class DasheCacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(DasheCacheApplication.class, args);
	}

}