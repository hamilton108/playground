
package playground.dto;

public enum ServiceClientType {
    WEB_SHOP("WebShop"),
    COMPASS("COMPASS"),
    FINANSPORTALEN("Finansportalen"),
    PARTNERPORTAL("Partnerportal"),
    TRIOLINK("Triolink"),
    VOLVIA("Volvia"),
    BILOKONOMI("Bilokonomi"),
    EUROWIZ("Eurowiz"),
    FINNNO("Finnno"),
    SKANDIABANKEN("Skandiabanken"),
    PREG_LIFE("PregLife"),
    SANTANDER("Santander"),
    IF_API("IfApi"),
    NORPOL("Norpol");

    private final String value;

    ServiceClientType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceClientType fromValue(String v) {
        for (ServiceClientType c: ServiceClientType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
