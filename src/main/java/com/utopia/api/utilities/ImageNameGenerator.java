package com.utopia.api.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ImageNameGenerator {
    public static String generateUniqueName(long userId) {
        try {
            String data = userId + "_" + System.currentTimeMillis();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error (" + e.getCause() + "): " + e.getMessage());
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}