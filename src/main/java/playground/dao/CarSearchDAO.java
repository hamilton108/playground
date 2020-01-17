package playground.dao;

import java.util.Map;
import java.util.Set;

public interface CarSearchDAO {
    void populateCache();

    Map<byte[],byte[]> getBrands();
    //Set<byte[]> getModelIds(int brandId);
    Map<Integer, String> getModels(int brandId);
    Map<Integer, String> getVariants(int brandId, int modelId);
}

