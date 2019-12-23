package playground.repository;

import playground.dto.VehicleBrand;
import playground.dao.CarSearchDAO;
import playground.dto.VehicleListModel;

import java.util.*;

public class CarSearchRepository {
    private final CarSearchDAO carSearchDAO;

    public CarSearchRepository(CarSearchDAO carSearchDAO) {
        this.carSearchDAO = carSearchDAO;
    }

    public List<VehicleBrand> getBrands() {
        List<VehicleBrand> result = new ArrayList<>();

        /*
        Map<byte[],byte[]> brands = carSearchDAO.getBrands();

        if (brands != null) {
            for (Map.Entry<byte[], byte[]> entry : brands.entrySet()) {
                int key = byte2int(entry.getKey());
                String v = new String(entry.getValue());
                VehicleBrand vehicleBrand = new VehicleBrand();
                vehicleBrand.setBrandName(v);
                vehicleBrand.setBrandIdentifier(key);
                result.add(vehicleBrand);
            }
        }
         */

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
        return result;
    }


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


    private Map<Integer,String> getBrandsNormalized() {
        Map<Integer,String> result = new HashMap<>();
        Map<byte[],byte[]> brands = carSearchDAO.getBrands();

        if (brands != null) {
            for (Map.Entry<byte[], byte[]> entry : brands.entrySet()) {
                result.put(byte2int(entry.getKey()), byte2string(entry.getValue()));
            }
        }
        return result;
    }

    private void printByteMap(Map<byte[], byte[]> map) {
        for (Map.Entry<byte[],byte[]> mb : map.entrySet()) {
            String k = new String(mb.getKey());
            String v = new String(mb.getValue());
            System.out.println(String.format("%s => %s", k, v));
        }
    }

    private String byte2string(byte[] b) {
        return new String(b);
    }

    private int byte2int(byte[] b) {
        String k = new String(b);
        return Integer.parseInt(k);
    }
}
