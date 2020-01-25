package playground.redis;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableConfigurationProperties(CacheConfigurationProperties.class)
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    private static final Logger logger = getLogger( CacheConfig.class );

    private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }

    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public LettuceConnectionFactory redisConnectionFactory(CacheConfigurationProperties properties) {
        logger.info("Redis (/Lettuce) configuration enabled. With cache timeout " + properties.getTimeoutSeconds() + " seconds.");
        logger.info("Redis host: " + properties.getRedisHost());
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(properties.getRedisHost());
        redisStandaloneConfiguration.setPort(properties.getRedisPort());
        //redisStandaloneConfiguration.setDatabase(properties.getDatabase());
        redisStandaloneConfiguration.setDatabase(properties.getRedisDatabase());

        if (!StringUtils.isEmpty(properties.getPassword())){
            redisStandaloneConfiguration.setPassword(properties.getPassword());
        }

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfiguration = LettuceClientConfiguration.builder();
        if (properties.isSsl()) {
            lettuceClientConfiguration.useSsl();
        }

        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration.build());
    }

    /*
    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
     */

    /*
    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

     */

    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public  RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Integer.class));
        return redisTemplate;
    }

    /*
    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public RedisTemplate<String,String> redisTemplate1(RedisConnectionFactory cf) {
        return genericRedisTemplate(cf);
    }
    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public RedisTemplate<String,Integer> redisTemplate2(RedisConnectionFactory cf) {
        return genericRedisTemplate(cf);
    }

     */


    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public RedisCacheConfiguration cacheConfiguration(CacheConfigurationProperties properties) {
        return createCacheConfiguration(properties.getTimeoutSeconds());
    }


    @Bean
    @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, CacheConfigurationProperties properties) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        for (Entry<String, Long> cacheNameAndTimeout : properties.getCacheExpirations().entrySet()) {
            cacheConfigurations.put(cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
        }

        return RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(cacheConfiguration(properties))
            .withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
