package playground.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "cache")
public class CacheConfigurationProperties {

    private boolean enabled;
    private long timeoutSeconds;
    private int redisPort;
    private String redisHost;
    private boolean ssl = false;
    private String password;
    private int redisDatabase;
    public CacheConfigurationProperties() {
        System.out.println("Here I am");
    }
    // Mapping of cacheNames to expira-after-write timeout in seconds
    private Map<String, Long> cacheExpirations = new HashMap<>();

    public Map<String, Long> getCacheExpirations() {
        return cacheExpirations;
    }

    public void setCacheExpirations(Map<String, Long> cacheExpirations) {
        this.cacheExpirations = cacheExpirations;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRedisDatabase() {
        return redisDatabase;
    }

    public void setRedisDatabase(int redisDatabase) {
        this.redisDatabase = redisDatabase;
    }
}