package playground.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;
import playground.redis.RedisUtil;

import java.util.*;

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
        return redisConnection.hashCommands().hGetAll("brand:name".getBytes());
    }

    void demo() {
        Set<String> set = new java.util.HashSet<String>();
        set.add("Apple");
        set.add("Orange");
        set.add("Banana");

        System.out.println("Contents of Set ::"+set);
        String[] myArray = new String[set.size()];
        set.toArray(myArray);

        for(int i=0; i<myArray.length; i++){
            System.out.println("Element at the index "+(i+1)+" is ::"+myArray[i]);
        }
    }

    @Override
    public Map<Integer, String> getModels(int brandId) {
        Map<Integer, String> result = new HashMap<>();

        Set<byte[]> modelIds = getModelIds(brandId);

        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();

        byte[][] tmp = new byte[modelIds.size()][];

        modelIds.toArray(tmp);

        for (byte[] mid : modelIds) {
            List<byte[]> mnames = redisConnection.hashCommands().hMGet("model:name".getBytes(), tmp);
            for (byte[] nx : mnames) {
                System.out.println(RedisUtil.byte2string(nx));
            }
            break;
        }
        /*
        byte[][] tmp = new byte[modelIds.size()][];

        modelIds.toArray(tmp);

        for (byte[] x : tmp) {
            System.out.println(Arrays.toString(x));
        }

        List<byte[]> mnames = redisConnection.hashCommands().hMGet("model:name".getBytes(), tmp);

        if (mnames != null) {
            int i = 0;
            for (byte[] modelId : modelIds) {

                int mid = RedisUtil.byte2int(modelId);
                String mname = RedisUtil.byte2string(mnames.get(i));
                result.put(mid, mname);
                ++i;
            }
        }

         */
        return result;
    }


    private Set<byte[]> getModelIds(int brandId) {
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        byte[] key = String.format("brand:models:%d", brandId).getBytes();
        return redisConnection.sMembers(key);
    }

    /*
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
     */

    public LettuceConnectionFactory getLettuceConnectionFactory() {
        return lettuceConnectionFactory;
    }
}
