package com.desafio.boleto.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class GerarUUID {
    public static String gerarUUIDTamanhoQuatorze() {
        byte[] randomBytes = new byte[12];
        new SecureRandom().nextBytes(randomBytes);

        randomBytes[6] &= 0x0F;
        randomBytes[6] |= 0x40;
        randomBytes[8] &= 0x3F;
        randomBytes[8] |= 0x80;

        String base64String = Base64.getEncoder().encodeToString(randomBytes);

        base64String = base64String
                .replace("=", "")
                .replace("/", "")
                .replace("-", "");

        if (base64String.length() >= 14) {
            base64String = base64String.substring(0, 14);
        }
        return base64String;
    }

}
