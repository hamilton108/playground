package playground.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultCarSearchDAO implements CarSearchDAO {

    private final LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    public DefaultCarSearchDAO(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }

    @Override
    public void populateCache() {

    }

    @Override
    public Map<byte[], byte[]> getBrands() {
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        return redisConnection.hashCommands().hGetAll("brands".getBytes());
    }

    @Override
    public Map<byte[], byte[]> getModelsBrandIds() {
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        return redisConnection.hashCommands().hGetAll("models:brand".getBytes());
    }

    @Override
    public Map<byte[], byte[]> getModelNames() {
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        return redisConnection.hashCommands().hGetAll("models:name".getBytes());
    }

    public LettuceConnectionFactory getLettuceConnectionFactory() {
        return lettuceConnectionFactory;
    }
}
