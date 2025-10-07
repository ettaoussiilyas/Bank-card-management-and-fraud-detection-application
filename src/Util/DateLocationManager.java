package Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DateLocationManager {
    
    private static final List<String> LOCATIONS = Arrays.asList(
        "Paris", "Lyon", "Marseille", "Toulouse", "Nice", "Nantes", "Strasbourg",
        "Montpellier", "Bordeaux", "Lille", "Rennes", "Reims", "Le Havre",
        "Saint-Étienne", "Toulon", "Grenoble", "Dijon", "Angers", "Nîmes", "Villeurbanne"
    );
    
    private static final Random random = new Random();
    
    // Get current timestamp formatted
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    // Get random location
    public static String getRandomLocation() {
        return LOCATIONS.get(random.nextInt(LOCATIONS.size()));
    }
    
    // Check if two locations are different (for fraud detection)
    public static boolean areLocationsDifferent(String location1, String location2) {
        return !location1.equalsIgnoreCase(location2);
    }
    
    // Calculate time difference in minutes
    public static long getTimeDifferenceInMinutes(LocalDateTime time1, LocalDateTime time2) {
        return Math.abs(java.time.Duration.between(time1, time2).toMinutes());
    }
}