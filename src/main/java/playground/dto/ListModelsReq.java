
package playground.dto;

public class ListModelsReq {

    protected String vehicleClass;
    protected String modelYear;
    protected int brandIdentifier;
    protected String vehicleIndicator;
    protected int maxListlengthRes;

    /**
     * Gets the value of the vehicleClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleClass() {
        return vehicleClass;
    }

    /**
     * Sets the value of the vehicleClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleClass(String value) {
        this.vehicleClass = value;
    }

    /**
     * Gets the value of the modelYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelYear() {
        return modelYear;
    }

    /**
     * Sets the value of the modelYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelYear(String value) {
        this.modelYear = value;
    }

    /**
     * Gets the value of the brandIdentifier property.
     * 
     */
    public int getBrandIdentifier() {
        return brandIdentifier;
    }

    /**
     * Sets the value of the brandIdentifier property.
     * 
     */
    public void setBrandIdentifier(int value) {
        this.brandIdentifier = value;
    }

    /**
     * Gets the value of the vehicleIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleIndicator() {
        return vehicleIndicator;
    }

    /**
     * Sets the value of the vehicleIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleIndicator(String value) {
        this.vehicleIndicator = value;
    }

    /**
     * Gets the value of the maxListlengthRes property.
     * 
     */
    public int getMaxListlengthRes() {
        return maxListlengthRes;
    }

    /**
     * Sets the value of the maxListlengthRes property.
     * 
     */
    public void setMaxListlengthRes(int value) {
        this.maxListlengthRes = value;
    }

}
