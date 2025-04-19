package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPService {
    private static final Map<String, String> otpStorage = new HashMap<>();
    private static final int OTP_LENGTH = 6;
    private static final long OTP_EXPIRY_TIME = 5 * 60 * 1000; // 5 minutes in milliseconds

    // Generate and store OTP
    public static String generateOTP(String phoneNumber) {
        String otp = generateRandomOTP();
        otpStorage.put(phoneNumber, otp + ":" + System.currentTimeMillis());
        return otp;
    }

    // Verify OTP
    public static boolean verifyOTP(String phoneNumber, String userEnteredOTP) {
        String storedValue = otpStorage.get(phoneNumber);
        if (storedValue == null) {
            return false;
        }

        String[] parts = storedValue.split(":");
        String storedOTP = parts[0];
        long generatedTime = Long.parseLong(parts[1]);

        // Check if OTP is expired
        if (System.currentTimeMillis() - generatedTime > OTP_EXPIRY_TIME) {
            otpStorage.remove(phoneNumber);
            return false;
        }

        return storedOTP.equals(userEnteredOTP);
    }

    // Clear OTP after successful verification
    public static void clearOTP(String phoneNumber) {
        otpStorage.remove(phoneNumber);
    }

    private static String generateRandomOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    // This would be replaced with actual SMS sending logic
    public static void sendOTP(String phoneNumber, String otp) {
        // In a real implementation, you would integrate with an SMS gateway here
        System.out.println("Sending OTP " + otp + " to phone number " + phoneNumber);
        // Example: Twilio, Nexmo, or other SMS API integration
    }
}