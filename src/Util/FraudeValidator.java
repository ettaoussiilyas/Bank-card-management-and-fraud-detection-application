package Util;

import Entity.Record.OperationCarte;
import Entity.SealedClass.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class FraudeValidator {
    
    private static final double HIGH_AMOUNT_THRESHOLD = 1000.0;
    private static final int SUSPICIOUS_TIME_WINDOW_MINUTES = 30;
    private static final int MAX_OPERATIONS_PER_HOUR = 10;
    
    public static boolean isHighAmountSuspicious(double montant, Carte carte) {
        if (carte instanceof CarteDebit) {
            return montant > HIGH_AMOUNT_THRESHOLD * 0.5;
        } else if (carte instanceof CarteCredit) {
            return montant > HIGH_AMOUNT_THRESHOLD * 2.0;
        } else if (carte instanceof CartePrepayee) {
            return montant > carte.getSolde();
        }
        return false;
    }
    
    public static boolean hasRapidSuccessiveOperations(List<OperationCarte> recentOperations) {
        if (recentOperations.size() < 2) return false;
        
        LocalDateTime now = LocalDateTime.now();
        long recentCount = recentOperations.stream()
            .mapToLong(op -> {
                LocalDateTime opTime = op.date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return DateLocationManager.getTimeDifferenceInMinutes(now, opTime);
            })
            .filter(minutes -> minutes <= 60)
            .count();
            
        return recentCount > MAX_OPERATIONS_PER_HOUR;
    }
    
    public static boolean hasSuspiciousLocationPattern(List<OperationCarte> recentOperations) {
        if (recentOperations.size() < 2) return false;
        
        for (int i = 0; i < recentOperations.size() - 1; i++) {
            OperationCarte op1 = recentOperations.get(i);
            OperationCarte op2 = recentOperations.get(i + 1);
            
            LocalDateTime time1 = op1.date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime time2 = op2.date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            
            long timeDiff = DateLocationManager.getTimeDifferenceInMinutes(time1, time2);
            
            if (timeDiff <= SUSPICIOUS_TIME_WINDOW_MINUTES && 
                DateLocationManager.areLocationsDifferent(op1.lieu(), op2.lieu())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean exceedsCardLimits(double montant, Carte carte) {
        if (carte instanceof CarteDebit) {
            return montant > 1000.0;
        } else if (carte instanceof CarteCredit) {
            return montant > 5000.0;
        } else if (carte instanceof CartePrepayee) {
            return montant > carte.getSolde();
        }
        return false;
    }
}