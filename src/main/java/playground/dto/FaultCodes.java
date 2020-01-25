package playground.dto;

/**
 *
 * @author bgl
 */
public interface FaultCodes {
    
    public String getMessage();
    public String getCode();
    public FaultCategory getFaultCategory();
    public boolean isStrictlyInternal();

}
