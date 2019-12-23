package playground.dao;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MockCarSearchDAO implements CarSearchDAO {

    @Override
    public void populateCache() {

    }

    @Override
    public Map<byte[], byte[]> getBrands() {
        Map<byte[],byte[]> result = new HashMap<>();

        result.put("1".getBytes(),"Corvette".getBytes(StandardCharsets.UTF_8));
        result.put("2".getBytes(),"Cadillac".getBytes());
        result.put("3".getBytes(),"Tesla".getBytes());

        return result;
    }

    @Override
    public Map<byte[], byte[]> getModelsBrandIds() {
        Map<byte[],byte[]> result = new HashMap<>();

        result.put("1".getBytes(),"1".getBytes());
        result.put("2".getBytes(),"2".getBytes());
        result.put("3".getBytes(),"2".getBytes());
        result.put("4".getBytes(),"3".getBytes());

        return result;
    }

    @Override
    public Map<byte[], byte[]> getModelNames() {
        Map<byte[],byte[]> result = new HashMap<>();

        result.put("1".getBytes(),"Corvette Model".getBytes());
        result.put("2".getBytes(),"Cadillac Model 1".getBytes());
        result.put("3".getBytes(),"Cadillac Model 2".getBytes());
        result.put("4".getBytes(),"Tesla Model".getBytes());

        return result;
    }

}
