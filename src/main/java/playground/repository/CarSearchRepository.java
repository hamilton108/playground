package playground.repository;

import playground.dto.VehicleBrand;
import playground.dao.CarSearchDAO;
import playground.dto.VehicleListModel;
import playground.redis.RedisUtil;

import java.util.*;

public class CarSearchRepository {
    private final CarSearchDAO carSearchDAO;

    public CarSearchRepository(CarSearchDAO carSearchDAO) {
        this.carSearchDAO = carSearchDAO;
    }

    public List<VehicleBrand> getBrands() {
        List<VehicleBrand> result = new ArrayList<>();

        Map<Integer,String> brands = getBrandsNormalized();
        for (Map.Entry<Integer, String> entry : brands.entrySet()) {
            VehicleBrand vehicleBrand = new VehicleBrand();
            vehicleBrand.setBrandName(entry.getValue());
            vehicleBrand.setBrandIdentifier(entry.getKey());
            result.add(vehicleBrand);
        }
        return result;
    }


    public List<VehicleListModel> getModels(List<Integer> brandIds) {
        List<VehicleListModel> result = new ArrayList<>();

        int brandId = brandIds.get(0);

        Map<Integer,String> models = carSearchDAO.getModels(brandId);

        for (Map.Entry<Integer,String> item : models.entrySet()) {
            var vlm = new VehicleListModel();
            vlm.setModelIdentifier(item.getKey());
            vlm.setModelName(item.getValue());
            result.add(vlm);
        }
        return result;

        /*
        Map<Integer,Integer> modelBrands = getModelBrandsNormalized();
        Map<Integer,String> modelNames = getModelNamesNormalized();

        for (Integer brandId : brandIds) {
            for (Map.Entry<Integer,Integer> mb : modelBrands.entrySet()) {
                if (brandId.equals(mb.getValue())) {
                    VehicleListModel model = new VehicleListModel();
                    model.setModelName(modelNames.get(mb.getKey()));
                    model.setModelIdentifier(mb.getKey());
                    result.add(model);
                }
            }
        }

         */

        /*
        Map<byte[],byte[]> modelBrands = carSearchDAO.getModelsBrandIds();
        Map<byte[],byte[]> modelNames = carSearchDAO.getModelNames();

        for (Integer brandId : brandIds) {
            byte[] key = brandId.toString().getBytes();
            for (Map.Entry<byte[],byte[]> mb : modelBrands.entrySet()) {
                System.out.println(String.format("%s == %s?",
                        Arrays.toString(key),
                        Arrays.toString(mb.getValue())));
                if (Arrays.equals(mb.getValue(),key)) {
                    byte[] curName = modelNames.get(mb.getKey());
                    VehicleListModel model = new VehicleListModel();
                    model.setModelName(Arrays.toString(curName));
                    model.setModelIdentifier(byte2int(mb.getKey()));
                    result.add(model);
                }
            }
        }
         */
    }

    private Map<Integer,String> getBrandsNormalized() {
        Map<Integer,String> result = new HashMap<>();
        Map<byte[],byte[]> brands = carSearchDAO.getBrands();

        if (brands != null) {
            for (Map.Entry<byte[], byte[]> entry : brands.entrySet()) {
                result.put(RedisUtil.byte2int(entry.getKey()), RedisUtil.byte2string(entry.getValue()));
            }
        }
        return result;
    }

    /*
    private Map<Integer,Integer> getModelBrandsNormalized() {
        Map<Integer,Integer> result = new HashMap<>();

        Map<byte[],byte[]> modelBrands = carSearchDAO.getModelsBrandIds();
        for (Map.Entry<byte[],byte[]> entry : modelBrands.entrySet()) {
            result.put(byte2int(entry.getKey()), byte2int(entry.getValue()));
        }
        return result;
    }

    private Map<Integer,String> getModelNamesNormalized() {
        Map<Integer,String> result = new HashMap<>();

        Map<byte[],byte[]> modelNames = carSearchDAO.getModelNames();
        for (Map.Entry<byte[],byte[]> entry : modelNames.entrySet()) {
            result.put(byte2int(entry.getKey()), byte2string(entry.getValue()));
        }

        return result;
    }
     */

    private void printByteMap(Map<byte[], byte[]> map) {
        for (Map.Entry<byte[],byte[]> mb : map.entrySet()) {
            String k = new String(mb.getKey());
            String v = new String(mb.getValue());
            System.out.println(String.format("%s => %s", k, v));
        }
    }

}
