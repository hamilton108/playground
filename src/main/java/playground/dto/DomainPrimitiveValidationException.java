package playground.dto;

public class DomainPrimitiveValidationException extends RuntimeException {

    private static final long serialVersionUID = -8460356990632230194L;
    private final FaultCodes faultCodes;

    public DomainPrimitiveValidationException(String message, Throwable throwable, FaultCodes faultCodes) {
        super(message,throwable);
        this.faultCodes = faultCodes;
    }
    public DomainPrimitiveValidationException(FaultCodes faultCodes) {
        this.faultCodes = faultCodes;
    }

    public FaultCodes getFaultCodes()  {
        return faultCodes;
    }

}
