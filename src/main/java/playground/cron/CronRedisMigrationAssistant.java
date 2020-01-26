package playground.cron;

import ch.qos.logback.core.util.SystemInfo;
import playground.dao.CarSearchCoreDAO;
import playground.dto.*;
import playground.search.CarSearchUtil;

import java.util.*;

public class CronRedisMigrationAssistant {
    private final CarSearchCoreDAO carSearchCoreDAO;;
    private final List<ModelYear> modelYears;
    private final SystemInfo systemInfo;
    private final Map<ModelYear,List<VehicleBrandWrapper>> yearBrands;

    public CronRedisMigrationAssistant(List<ModelYear> modelYears,
                                       CarSearchCoreDAO carSearchCoreDAO,
                                       SystemInfo systemInfo) {
        this.carSearchCoreDAO = carSearchCoreDAO;
        this.systemInfo = systemInfo;
        this.modelYears = modelYears;
        this.yearBrands = initYearBrands();
    }

    public Set<VehicleBrandWrapper> getBrands() {
        Set<VehicleBrandWrapper> result = new HashSet<>();
        for (var brands : yearBrands.values()) {
            result.addAll(brands);
        }
        return result;
    }

    public Map<String, Set<Integer>> getBrandYears() {
        Map<String, Set<Integer>> result = new HashMap<>();

        for (Map.Entry<ModelYear,List<VehicleBrandWrapper>> entry : yearBrands.entrySet()) {
            List<VehicleBrandWrapper> brands = entry.getValue();

            String key = String.format("brand:%d", entry.getKey().getYear());

            Set<Integer> redisItems = new HashSet<>();

            for (var brand : brands) {
                redisItems.add(brand.getId());
            }

            if (redisItems.size() > 0) {
                result.put(key, redisItems);
            }
        }

        return result;
    }

    public Map<String, Set<String>> getBrandModels() {
        Map<String, Set<String>> result = new HashMap<>();

        for (VehicleBrandWrapper brand : getBrands()) {
            String key = String.format("brand:models:%d", brand.getId());

            List<VehicleListModelWrapper> models = brand.getModels();

            Set<String> redisItems = new HashSet<>();

            for (VehicleListModelWrapper model : models) {
                String redisItem = String.format("%d:%d", brand.getId(), model.getId());
                redisItems.add(redisItem);
            }

            if (redisItems.size() > 0) {
                result.put(key, redisItems);
            }
        }

        return result;
    }

    public Map<String, Set<String>> getModelVariants() {
        Map<String, Set<String>> result = new HashMap<>();

        for (VehicleBrandWrapper brand : getBrands()) {

            for (VehicleListModelWrapper model : brand.getModels()) {

                String key = String.format("%d:%d", brand.getId(), model.getId());

                List<VehicleVariant> variants = model.getVariants();

                Set<String> redisItems = new HashSet<>();

                for (var variant : variants) {
                    redisItems.add(variant.getVariantIdentifier());
                }
                if (redisItems.size() > 0) {
                    result.put(key, redisItems);
                }
            }
        }
        return result;
    }

    public Map<Integer,String> getBrandNames() {
        Map<Integer,String> result = new HashMap<>();

        for (var brand : getBrands()) {
            result.put(brand.getId(),brand.getName());
        }

        return result;
    }

    public Map<String,String> getModelNames() {
        Map<String,String> result = new HashMap<>();

        for (var brand : getBrands()) {
            int bid = brand.getId();
            for (var model : brand.getModels()) {
                String key = String.format("%d:%d", bid, model.getId());
                result.put(key, model.getName());
            }
        }

        return result;
    }

    public Map<String,String> getVariantNames() {
        Map<String,String> result = new HashMap<>();

        for (var brand : getBrands()) {
            for (var model : brand.getModels()) {
                for (var variant : model.getVariants())  {
                    result.put(variant.getVariantIdentifier(),variant.getVariantName());
                }
            }
        }

        return result;
    }

    /*
    public Map<String,String> getModelKeyNames() {
        Map<String,String> result = new HashMap<>();

        Set<VehicleBrandWrapper> brands = getBrands();

        for (var brand : brands) {
            for (var model : brand.getModels()) {
                String key = String.format("%s:%s", brand.getId(), model.getId());
                result.put(key, model.getName());
            }
        }

        return result;
    }

     */

    private Map<ModelYear,List<VehicleBrandWrapper>> initYearBrands() {
        Map<ModelYear,List<VehicleBrandWrapper>> result = new HashMap<>();
        for (ModelYear modelYear : modelYears) {
            List<VehicleBrandWrapper> brands = createBrandsFor(modelYear);
            result.put(modelYear, brands);
        }
        return result;
    }

    private List<VehicleBrandWrapper> createBrandsFor(ModelYear modelYear) {
        List<VehicleBrandWrapper> result = new ArrayList<>();

        List<VehicleBrand> coreBrands = carSearchCoreDAO.getCarBrands(modelYear, systemInfo);

        for (VehicleBrand coreBrand : coreBrands) {
            VehicleBrandWrapper vehicleBrandWrapper = new VehicleBrandWrapper(modelYear,coreBrand);

            List<ListModelsReq> modelsReq = new ArrayList<>();

            modelsReq.add(CarSearchUtil.listModelsReqFor(modelYear, coreBrand.getBrandIdentifier()));

            List<VehicleListModel> coreModels = carSearchCoreDAO.getCarModels(modelsReq,systemInfo);

            for (VehicleListModel coreModel : coreModels) {
                VehicleListModelWrapper modelWrapper = new VehicleListModelWrapper(coreModel);

                List<ListVariantsReq> reqs = new ArrayList<>();
                reqs.add(CarSearchUtil.listVariantsReqModelIds(modelYear,coreBrand.getBrandIdentifier(),coreModel.getModelIdentifier()));

                var coreVariants = carSearchCoreDAO.getCarVariants(reqs, systemInfo);
                modelWrapper.setVariants(coreVariants);

                vehicleBrandWrapper.addModel(modelWrapper);
            }

            result.add(vehicleBrandWrapper);
        }

        return result;
    }

    public static List<ModelYear> modelYearsFor(int... years) {
        List<ModelYear> result = new ArrayList<>();

        for (int year : years) {
            result.add(new ModelYear(year));
        }

        return result;
    }

}
