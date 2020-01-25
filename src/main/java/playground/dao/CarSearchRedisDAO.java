package playground.dao;

import java.util.Map;
import java.util.Set;

public interface CarSearchRedisDAO {
    Map<byte[],byte[]> getBrands();
    Map<Integer,String> getBrands(int year);
    //Set<byte[]> getModelIds(int brandId);
    Map<Integer, String> getModels(int brandId);
    Map<Integer, String> getVariants(int brandId, int modelId);
    void insertBrandYears(Map<String, Set<Integer>> brandYears);
    void insertBrandModels(Map<String, Set<String>> brandModels);
    void insertModelVariants(Map<String, Set<String>> variants);
}

