package playground.dto;

import org.springframework.data.redis.core.RedisHash;

public class VehicleBrand {
    private String brandName;
    private int brandIdentifier;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getBrandIdentifier() {
        return brandIdentifier;
    }

    public void setBrandIdentifier(int brandIdentifier) {
        this.brandIdentifier = brandIdentifier;
    }
}
