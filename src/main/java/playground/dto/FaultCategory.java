package playground.dto;

/**
 *
 * @author BG5
 */
public enum FaultCategory {
    
    INVALID_INPUT_DATA("InvalidInputData"),
    TEMPORARY_PROBLEM("TemporaryProblem"),
    UNSUPPORTED("Unsupported");
    
    private final String value;

    private FaultCategory(String value) {
        this.value = value;
    }
    
    public String value() {
        return value;
    }

    public static ServiceClientType fromValue(String v) {
        for (ServiceClientType c: ServiceClientType.values()) {
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
