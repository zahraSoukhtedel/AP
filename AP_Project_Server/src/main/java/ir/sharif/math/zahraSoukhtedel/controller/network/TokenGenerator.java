package ir.sharif.math.zahraSoukhtedel.controller.network;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    static private TokenGenerator instance;
    public static TokenGenerator getInstance() {
        if(instance == null)
            instance = new TokenGenerator();
        return instance;
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
