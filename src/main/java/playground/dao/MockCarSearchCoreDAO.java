package playground.dao;

import ch.qos.logback.core.util.SystemInfo;
import playground.dto.*;

import java.util.*;

public class MockCarSearchCoreDAO implements CarSearchCoreDAO {
    private final Map<Integer,List<Integer>> yearBrandIds;

    public MockCarSearchCoreDAO() {
        yearBrandIds = new HashMap<>();
        yearBrandIds.put(2002, Arrays.asList(1,2,3,4));
        yearBrandIds.put(2010, Arrays.asList(2,3,4,5,6));
        yearBrandIds.put(2019, Arrays.asList(5,6,7,8,9));
    }

    @Override
    public List<VehicleBrand> getCarBrands(ModelYear modelYear, SystemInfo systemInfo) {
        List<VehicleBrand> result = new ArrayList<>();
        for (int brandId : yearBrandIds.get(modelYear.getYear())) {
            result.add(createBrand(brandId));
        }
        return result;
    }

    @Override
    public List<VehicleListModel> getCarModels(List<ListModelsReq> reqs, SystemInfo systemInfo) {
        List<VehicleListModel> result = new ArrayList<>();

        for (ListModelsReq req : reqs) {
            int addToId = (req.getBrandIdentifier() * 100) + 1;
            for (int i=0; i<5; ++i) {
                result.add(createModel(addToId+i));
            }
        }

        return result;
    }

    @Override
    public List<VehicleVariant> getCarVariants(List<ListVariantsReq> reqs, SystemInfo systemInfo) {
        List<VehicleVariant> result = new ArrayList<>();
        ListVariantsReq req = reqs.get(0);
        int addToId = (req.getModelIdentifier()*100) + 1;
        for (int i=0; i<5; ++i) {
            result.add(createVariant(String.format("%d", addToId + i)));
        }
        return result;
    }

    private VehicleBrand createBrand(int id) {
        VehicleBrand result = new VehicleBrand();
        result.setBrandIdentifier(id);
        result.setBrandName(String.format("B%d", id));
        return result;
    }
    private VehicleListModel createModel(int id) {
        VehicleListModel result = new VehicleListModel();
        result.setModelIdentifier(id);
        result.setModelName(String.format("M%d", id));
        return result;
    }
    private VehicleVariant createVariant(String id) {
        VehicleVariant result = new VehicleVariant();
        result.setVariantIdentifier(id);
        result.setVariantName(String.format("V%s", id));
        return result;
    }
}
