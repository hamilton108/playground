package playground.search;

import playground.dto.ListModelsReq;
import playground.dto.ListVariantsReq;
import playground.dto.ModelYear;

public class CarSearchUtil {
    public static ListModelsReq listModelsReqFor(ModelYear year, int brandId) {
        ListModelsReq req = new ListModelsReq();
        req.setModelYear(year.toString());
        req.setVehicleIndicator("A");
        req.setVehicleClass("MK");
        req.setBrandIdentifier(brandId);
        return req;
    }
    public static ListVariantsReq listVariantsReqModelIds(ModelYear year, int brandId, int modelId) {
        ListVariantsReq req = new ListVariantsReq();
        req.setModelYear(year.toString());
        req.setVehicleIndicator("A");
        req.setVehicleClass("MK");
        req.setBrandIdentifier(brandId);
        req.setModelIdentifier(modelId);
        return req;
    }
}
