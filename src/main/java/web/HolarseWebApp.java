package web;

import javax.cache.CacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
public class HolarseWebApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HolarseWebApp.class, args);
    }

}
