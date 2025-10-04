package Service;

import DAO.AlerteDAO;
import Entity.Record.AlerteFraude;
import Entity.Record.OperationCarte;
import Entity.Enum.NiveauAlerte;
import Entity.Enum.TypeOperation;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class FraudeService {

    private final AlerteDAO alerteDao;
    private final OperationService operationService;
    private final CarteService carteService;

    // Fraud detection thresholds
    private static final double HIGH_AMOUNT_THRESHOLD = 5000.0;
    private static final int MAX_OPERATIONS_PER_HOUR = 10;
    private static final long LOCATION_TIME_THRESHOLD = 30; // minutes

    public FraudeService(){
        alerteDao = new AlerteDAO();
        operationService = new OperationService();
        carteService = new CarteService();
    }

    // Main fraud detection method
    public void analyzeCardOperations(int carteId) {
        List<OperationCarte> recentOperations = operationService.getRecentOperationsForCarte(carteId, 24);
        
        // Check various fraud patterns
        checkHighAmountTransactions(carteId, recentOperations);
        checkFrequentTransactions(carteId, recentOperations);
        checkSuspiciousLocations(carteId, recentOperations);
        checkUnusualPatterns(carteId, recentOperations);
    }

    // Detect high amount transactions
    private void checkHighAmountTransactions(int carteId, List<OperationCarte> operations) {
        for(OperationCarte operation : operations) {
            if(operation.montant() > HIGH_AMOUNT_THRESHOLD) {
                createAlert(carteId, 
                    "Montant élevé détecté: " + operation.montant() + "€", 
                    NiveauAlerte.AVERTISSEMENT);
                
                // Auto-suspend card for very high amounts
                if(operation.montant() > HIGH_AMOUNT_THRESHOLD * 2) {
                    carteService.suspendCarte(carteId);
                    createAlert(carteId, 
                        "Carte suspendue automatiquement - montant critique: " + operation.montant() + "€", 
                        NiveauAlerte.CRITIQUE);
                }
            }
        }
    }

    // Detect too many transactions in short time
    private void checkFrequentTransactions(int carteId, List<OperationCarte> operations) {
        List<OperationCarte> lastHourOps = operationService.getRecentOperationsForCarte(carteId, 1);
        
        if(lastHourOps.size() > MAX_OPERATIONS_PER_HOUR) {
            createAlert(carteId, 
                "Trop de transactions détectées: " + lastHourOps.size() + " en 1 heure", 
                NiveauAlerte.AVERTISSEMENT);
            
            // Block card if excessive transactions
            if(lastHourOps.size() > MAX_OPERATIONS_PER_HOUR * 2) {
                carteService.blockCarte(carteId);
                createAlert(carteId, 
                    "Carte bloquée - activité suspecte excessive", 
                    NiveauAlerte.CRITIQUE);
            }
        }
    }

    // Detect transactions in different locations within short time
    private void checkSuspiciousLocations(int carteId, List<OperationCarte> operations) {
        if(operations.size() < 2) return;
        
        for(int i = 0; i < operations.size() - 1; i++) {
            OperationCarte op1 = operations.get(i);
            OperationCarte op2 = operations.get(i + 1);
            
            // Check if different locations within short time
            if(!op1.lieu().equals(op2.lieu())) {
                long timeDiff = Math.abs(op1.date().getTime() - op2.date().getTime());
                long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(timeDiff);
                
                if(minutesDiff < LOCATION_TIME_THRESHOLD) {
                    createAlert(carteId, 
                        "Transactions dans lieux différents: " + op1.lieu() + " et " + op2.lieu() + 
                        " en " + minutesDiff + " minutes", 
                        NiveauAlerte.CRITIQUE);
                    
                    // Auto-block for suspicious location pattern
                    carteService.blockCarte(carteId);
                }
            }
        }
    }

    // Detect unusual transaction patterns
    private void checkUnusualPatterns(int carteId, List<OperationCarte> operations) {
        // Check for unusual transaction types
        Set<TypeOperation> typesUsed = new HashSet<>();
        for(OperationCarte op : operations) {
            typesUsed.add(op.type());
        }
        
        // Alert if all transaction types used in short time (unusual)
        if(typesUsed.size() >= 3) {
            createAlert(carteId, 
                "Pattern inhabituel: tous types de transactions utilisés", 
                NiveauAlerte.INFO);
        }
        
        // Check for round amounts (potential fraud indicator)
        long roundAmounts = operations.stream()
            .mapToDouble(OperationCarte::montant)
            .filter(amount -> amount % 100 == 0)
            .count();
            
        if(roundAmounts > operations.size() * 0.8) {
            createAlert(carteId, 
                "Montants ronds suspects détectés", 
                NiveauAlerte.AVERTISSEMENT);
        }
    }

    // Create fraud alert
    private void createAlert(int carteId, String description, NiveauAlerte niveau) {
        try {
            AlerteFraude alert = new AlerteFraude(0, description, niveau, carteId);
            alerteDao.save(alert);
            
            System.out.println("ALERTE FRAUDE [" + niveau + "]: " + description + " (Carte: " + carteId + ")");
            
        } catch (SQLException e) {
            System.err.println("Error creating fraud alert: " + e.getMessage());
        }
    }

    // Get all alerts for a card
    public List<AlerteFraude> getAlertsForCarte(int carteId) {
        try {
            return alerteDao.findByCarteId(carteId);
        } catch (SQLException e) {
            System.err.println("Error retrieving alerts: " + e.getMessage());
            return List.of();
        }
    }

    // Get critical alerts
    public List<AlerteFraude> getCriticalAlerts() {
        try {
            return alerteDao.findCriticalAlerts();
        } catch (SQLException e) {
            System.err.println("Error retrieving critical alerts: " + e.getMessage());
            return List.of();
        }
    }

    // Manual fraud analysis trigger
    public void performManualFraudCheck(int carteId) {
        System.out.println("Démarrage analyse manuelle fraude pour carte: " + carteId);
        analyzeCardOperations(carteId);
        System.out.println("Analyse fraude terminée pour carte: " + carteId);
    }
}