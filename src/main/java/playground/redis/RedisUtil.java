package playground.redis;

public class RedisUtil {

    public static String byte2string(byte[] b) {
        return new String(b);
    }

    public static int byte2int(byte[] b) {
        String k = new String(b);
        return Integer.parseInt(k);
    }

}
