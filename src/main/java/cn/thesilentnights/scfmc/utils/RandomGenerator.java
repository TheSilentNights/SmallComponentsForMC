package cn.thesilentnights.scfmc.utils;

import java.security.SecureRandom;

public class RandomGenerator {
 
    private static final String UPPERCASE   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE   = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS      = "0123456789";
    private static final String SPECIAL     = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final String ALPHANUMERIC = UPPERCASE + LOWERCASE + DIGITS;
    private static final String ALL          = ALPHANUMERIC + SPECIAL;
 
    private final SecureRandom random = new SecureRandom();
 
    // ── Core generator ────────────────────────────────────────────────────────
 
    public String generate(int length, String charset) {
        if (length <= 0)           throw new IllegalArgumentException("Length must be positive.");
        if (charset == null || charset.isEmpty()) throw new IllegalArgumentException("Charset must not be empty.");
 
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(charset.charAt(random.nextInt(charset.length())));
        }
        return sb.toString();
    }
 
    // ── Convenience methods ───────────────────────────────────────────────────
 
    /** Letters and digits only (URL-safe). */
    public String alphanumeric(int length) {
        return generate(length, ALPHANUMERIC);
    }
 
    /** Digits only — useful for PINs / OTPs. */
    public String numeric(int length) {
        return generate(length, DIGITS);
    }
 
    /** Letters only (upper + lower). */
    public String alphabetic(int length) {
        return generate(length, UPPERCASE + LOWERCASE);
    }
 
    /** Letters, digits, and special characters — strong passwords. */
    public String password(int length) {
        return generate(length, ALL);
    }
 
    /** Hex string (lower-case) — useful for tokens / hashes. */
    public String hex(int length) {
        return generate(length, "0123456789abcdef");
    }
}
