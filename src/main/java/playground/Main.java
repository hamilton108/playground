package playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private RedisTemplate<String, Map<String,String>> redisTemplate;
    //private RedisTemplate<String, String> redisTemplate;
    private LettuceConnectionFactory lettuceConnectionFactory;



    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //System.out.println("lettuceConnectionFactory: " + lettuceConnectionFactory.getHostName());
        try {
            String key = "brands";
            //String key = "s1";
            //System.out.println(redisTemplate.getConnectionFactory().getConnection().ping());
            //redisTemplate.opsForHash().put
            //RedisCommands<String, Object> commands = lettuceConnectionFactory.getConnection().sync();

            if (redisTemplate.hasKey(key)) {
                System.out.println("exists");
                RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
                Map<byte[],byte[]> hash = redisConnection.hashCommands().hGetAll(key.getBytes());
                if (hash != null) {
                    for (Map.Entry<byte[], byte[]> entry : hash.entrySet()) {
                        //Base64.getEncoder().encodeToString(entry.getKey());
                        String k = new String(entry.getKey());
                        String v = new String(entry.getValue());
                        System.out.println(String.format("Key: %s, val: %s", k, v));
                        Integer ix = Integer.parseInt(k);
                    }
                }

                //System.out.println("OK: " + redisTemplate.opsForHash().get(key,"1"));


                //Map<Object,Object> hashes = redisTemplate.opsForHash().entries(key);
                //System.out.println(hashes);
            } else {
                System.out.println(key + " NOPE!");
            }
            //redisTemplate.opsForValue().set(key, "abc");
        }
        catch (org.springframework.data.redis.RedisConnectionFailureException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setLettuceConnectionFactory(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }
}
