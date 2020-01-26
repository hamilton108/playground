package playground;

import ch.qos.logback.core.util.SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import playground.cron.CronRedisMigrationAssistant;
import playground.dao.CarSearchCoreDAO;
import playground.dao.DefaultCarSearchRedisDAO;
import playground.dao.MockCarSearchCoreDAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private RedisTemplate<String,Object> redisTemplate;
    //private RedisTemplate<String, Map<String,String>> redisTemplate;
    //private RedisTemplate<String, String> redisTemplate;
    private LettuceConnectionFactory lettuceConnectionFactory;

    public Main(LettuceConnectionFactory lettuceConnectionFactory,
                RedisTemplate<String,Object> redisTemplate) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
        this.redisTemplate = redisTemplate;
    }



    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            demo8();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void demo8() throws Exception {
        var redisDAO = new DefaultCarSearchRedisDAO(lettuceConnectionFactory,redisTemplate);
        var coreDAO = new MockCarSearchCoreDAO();
        var systemInfo = new SystemInfo();
        var  assistant =
                new CronRedisMigrationAssistant(CronRedisMigrationAssistant.modelYearsFor(2019), coreDAO, systemInfo);
        /*
        var brands = assistant.getBrandYears();
        System.out.println(brands);
        redisDAO.insertBrandYears(brands);
        var models = assistant.getBrandModels();
        System.out.println(models);
        redisDAO.insertBrandModels(models);
        var variants = assistant.getModelVariants();
        System.out.println(variants);
        redisDAO.insertModelVariants(variants);
        var brandNames = assistant.getBrandNames();
        System.out.println(brandNames);
        redisDAO.insertBrandNames(brandNames);

        var modelNames = assistant.getModelNames();
        System.out.println(modelNames);
        redisDAO.insertModelNames(modelNames);
         */
        var variantNames = assistant.getVariantNames();
        System.out.println(variantNames);
        redisDAO.insertVariantNames(variantNames);
    }

    public void demo7() throws Exception {
        SetOperations<String,Object> set1 = redisTemplate.opsForSet();
        Set<Integer> sx = new HashSet<>();
        sx.add(1);
        sx.add(11);
        sx.add(21);
        sx.add(31);
        sx.add(41);
        sx.add(41);
        sx.add(41);
        set1.add("demo7c", sx.toArray());
    }

    public void demo6() throws Exception {
        //*
        Map<String,Integer> brands = new HashMap<>();
        brands.put("A",1);
        brands.put("B",2);
        brands.put("C",3);
        HashOperations<String,String,Integer> ops1 = redisTemplate.opsForHash();
        ops1.putAll("demo6a", brands);

         /**/


        //*
        Map<String,String> brands2 = new HashMap<>();
        brands2.put("A","x1");
        brands2.put("B","x2");
        brands2.put("C","x3");
        HashOperations<String,String,String> ops2 = redisTemplate.opsForHash();
        ops2.putAll("demo6b", brands2);
        /**/

    }
    public void demo5() throws Exception {
        Map<String,Integer> brands = new HashMap<>();
        brands.put("A",1);
        brands.put("B",2);
        brands.put("C",3);
        HashOperations<String,String,Integer> ops = redisTemplate.opsForHash();
        ops.putAll("demo5c", brands);
        //redisTemplate.opsForHash().putAll("demo5", brands);
    }
        /*
    public void demo4() throws Exception {
        Map<String,Integer> brands = new HashMap<>();
        brands.put("A",1);
        brands.put("B",2);
        brands.put("C",3);
        redisTemplate.opsForHash().putAll("demo4", brands);
    }

    public void demo3() throws Exception {
        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");
        test.add("3");
        test.add("4");
        redisTemplate.opsForHash().put("map3", "mapx", test.toString()); // [1, 2, 3, 4]
        //redisTemplate.opsForHash().put("test", "isAdmin", true); // true
    }

    public void demo2() throws Exception {
        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        redisTemplate.opsForList().leftPushAll("test3", test);
        System.out.println(redisTemplate.opsForList().range("test3", 0, -1));
    }

    public void demo1() {
        String key = "brand:name";

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
        } else {
            System.out.println(key + " NOPE!");
        }
        //redisTemplate.opsForValue().set(key, "abc");

    }
         */

    @Autowired
    public void setLettuceConnectionFactory(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
