package playground.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VehicleBrandWrapper {
    private final ModelYear modelYear;
    private final VehicleBrand vehicleBrand;


    List<VehicleListModelWrapper> models = new ArrayList<>();

    public VehicleBrandWrapper(ModelYear modelYear, VehicleBrand vehicleBrand) {
        this.modelYear = modelYear;
        this.vehicleBrand = vehicleBrand;
    }
    public void addModel(VehicleListModelWrapper model) {
        models.add(model);
    }

    public List<VehicleListModelWrapper> getModels() {
        return models;
    }

    public ModelYear getModelYear() {
        return modelYear;
    }

    public VehicleBrand getVehicleBrand() {
        return vehicleBrand;
    }

    public int getId() {
        return vehicleBrand.getBrandIdentifier();
    }

    public String getName() {
        return vehicleBrand.getBrandName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != getClass()) {
            return false;
        }

        VehicleBrandWrapper ox = (VehicleBrandWrapper)o;
        return (Objects.equals(vehicleBrand.getBrandIdentifier(), ox.getId()));
        /*
        return (Objects.equals(modelYear.getYear(), ox.getModelYear().getYear()) &&
                Objects.equals(vehicleBrand.getBrandIdentifier(), ox.getVehicleBrand().getBrandIdentifier()));
         */
    }

    @Override
    public int hashCode() {
        //return Objects.hash(getModelYear().getYear(), getVehicleBrand().getBrandIdentifier());
        return Objects.hashCode(getVehicleBrand().getBrandIdentifier());
    }

}
