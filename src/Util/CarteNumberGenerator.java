package Util;

import java.util.Random;

public class CarteNumberGenerator {
    
    private static final Random random = new Random();
    
    // Generate unique 16-digit card number
    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        
        // First digit indicates card type (4 = Visa-like, 5 = MasterCard-like)
        cardNumber.append(random.nextBoolean() ? "4" : "5");
        
        // Generate remaining 15 digits
        for (int i = 1; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        
        return cardNumber.toString();
    }
    
    // Generate card ID with prefix
    public static String generateCardId() {
        return "C" + System.currentTimeMillis() + random.nextInt(1000);
    }
}