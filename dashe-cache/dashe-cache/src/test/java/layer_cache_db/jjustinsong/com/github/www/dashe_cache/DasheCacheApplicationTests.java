package layer_cache_db.jjustinsong.com.github.www.dashe_cache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import layer_cache_db.DasheCacheApplication;
import layer_cache_db.RedisConfig;

@SpringBootTest
@ContextConfiguration(classes = {DasheCacheApplication.class, RedisConfig.class})
class DasheCacheApplicationTests {

	@Test
	void contextLoads() {
	}

}
