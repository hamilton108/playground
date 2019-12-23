package playground.dao;

import java.util.Map;

public interface CarSearchDAO {
    void populateCache();

    Map<byte[],byte[]> getBrands();
    Map<byte[],byte[]> getModelsBrandIds();
    Map<byte[],byte[]> getModelNames();
}
