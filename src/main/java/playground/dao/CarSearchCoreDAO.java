package playground.dao;

import ch.qos.logback.core.util.SystemInfo;
import playground.dto.*;

import java.util.List;

public interface CarSearchCoreDAO {
    List<VehicleBrand> getCarBrands(ModelYear modelYear, SystemInfo systemInfo);
    List<VehicleListModel> getCarModels(List<ListModelsReq> reqs, SystemInfo systemInfo);
    List<VehicleVariant> getCarVariants(List<ListVariantsReq> reqs, SystemInfo systemInfo);
}
