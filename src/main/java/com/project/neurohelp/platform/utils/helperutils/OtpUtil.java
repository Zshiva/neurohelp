package com.project.neurohelp.platform.utils.helperutils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class OtpUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private OtpUtil() {
    }

    /** Generates a numeric OTP with the given number of digits (e.g., 6). */
    public static String generateNumericOtp(int digits) {
        if (digits <= 0) {
            throw new IllegalArgumentException("digits must be > 0");
        }
        int bound = (int) Math.pow(10, digits);
        int value = SECURE_RANDOM.nextInt(bound);
        return String.format("%0" + digits + "d", value);
    }

    /**
     * Hash OTP using SHA-256 with a per-user salt.
     * This is a simple approach that avoids storing OTP in plaintext.
     */
    public static String sha256WithSalt(String otp, String salt) {
        if (otp == null || salt == null) {
            throw new IllegalArgumentException("otp and salt must not be null");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest((salt + ":" + otp).getBytes(StandardCharsets.UTF_8));
            return toHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

