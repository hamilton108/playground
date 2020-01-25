package playground.dto;

import java.util.ArrayList;
import java.util.List;

public class VehicleListModelWrapper {
    private List<VehicleVariant> vehicleVariants = new ArrayList<>();
    private final VehicleListModel vehicleListModel;

    public VehicleListModelWrapper(VehicleListModel vehicleListModel) {
        this.vehicleListModel = vehicleListModel;
    }

    public List<VehicleVariant> getVariants() {
        return vehicleVariants;
    }

    public int getId() {
        return vehicleListModel.getModelIdentifier();
    }
    public String getName() {
        return vehicleListModel.getModelName();
    }

    public void setVariants(List<VehicleVariant> vehicleVariants) {
        this.vehicleVariants = vehicleVariants ;
    }
}
