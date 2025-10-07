package Service;

import Entity.Record.OperationCarte;
import Entity.SealedClass.Carte;
import Entity.Enum.StatuCarte;
import Entity.Enum.TypeOperation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

public class RapportService {

    private final CarteService carteService;
    private final OperationService operationService;
    private final FraudeService fraudeService;

    public RapportService(){
        carteService = new CarteService();
        operationService = new OperationService();
        fraudeService = new FraudeService();
    }

    // Generate top 5 most used cards report
    public List<Integer> getTop5MostUsedCards() {
        List<Carte> allCards = carteService.getAllCartes();
        
        return allCards.stream()
            .collect(Collectors.toMap(
                carte -> Integer.parseInt(carte.getId()),
                carte -> operationService.getOperationsByCarteId(Integer.parseInt(carte.getId())).size()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    // Generate blocked/suspicious cards report
    public List<Carte> getBlockedAndSuspiciousCards() {
        return carteService.getAllCartes().stream()
            .filter(carte -> carte.getStatut() == StatuCarte.BLOQUEE || 
                           carte.getStatut() == StatuCarte.SUSPENDUE)
            .collect(Collectors.toList());
    }

    // Generate transaction statistics
    public Map<TypeOperation, Long> getTransactionStatistics() {
        List<OperationCarte> allOperations = operationService.getAllOperations();
        
        return allOperations.stream()
            .collect(Collectors.groupingBy(
                OperationCarte::type,
                Collectors.counting()
            ));
    }

    // Generate daily transaction volume
    public double getDailyTransactionVolume() {
        List<OperationCarte> todayOperations = operationService.getOperationsByDateRange(
            new java.util.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000),
            new java.util.Date()
        );
        
        return todayOperations.stream()
            .mapToDouble(OperationCarte::montant)
            .sum();
    }

    // Generate cards by status report
    public Map<StatuCarte, Long> getCardsByStatus() {
        List<Carte> allCards = carteService.getAllCartes();
        
        return allCards.stream()
            .collect(Collectors.groupingBy(
                Carte::getStatut,
                Collectors.counting()
            ));
    }

    // Generate high-risk cards report
    public List<Integer> getHighRiskCards() {
        List<Carte> allCards = carteService.getAllCartes();
        
        return allCards.stream()
            .filter(carte -> {
                // Cards with recent fraud alerts
                return !fraudeService.getAlertsForCarte(Integer.parseInt(carte.getId())).isEmpty();
            })
            .map(carte -> Integer.parseInt(carte.getId()))
            .collect(Collectors.toList());
    }

    // Generate monthly transaction report
    public String generateMonthlyReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== RAPPORT MENSUEL BANCAIRE ===\n\n");
        
        // Transaction statistics
        Map<TypeOperation, Long> stats = getTransactionStatistics();
        report.append("STATISTIQUES TRANSACTIONS:\n");
        stats.forEach((type, count) -> 
            report.append("- ").append(type).append(": ").append(count).append(" transactions\n"));
        
        // Card status
        Map<StatuCarte, Long> cardStats = getCardsByStatus();
        report.append("\nSTATUT DES CARTES:\n");
        cardStats.forEach((status, count) -> 
            report.append("- ").append(status).append(": ").append(count).append(" cartes\n"));
        
        // Top cards
        List<Integer> topCards = getTop5MostUsedCards();
        report.append("\nTOP 5 CARTES LES PLUS UTILISÉES:\n");
        for(int i = 0; i < topCards.size(); i++) {
            report.append((i+1)).append(". Carte ").append(topCards.get(i)).append("\n");
        }
        
        // Risk assessment
        List<Integer> riskCards = getHighRiskCards();
        report.append("\nCARTES À RISQUE: ").append(riskCards.size()).append(" cartes\n");
        
        // Volume
        double volume = getDailyTransactionVolume();
        report.append("\nVOLUME TRANSACTIONS QUOTIDIEN: ").append(String.format("%.2f", volume)).append("€\n");
        
        return report.toString();
    }

    // Generate security report
    public String generateSecurityReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== RAPPORT SÉCURITÉ ===\n\n");
        
        List<Carte> blockedCards = getBlockedAndSuspiciousCards();
        report.append("CARTES BLOQUÉES/SUSPENDUES: ").append(blockedCards.size()).append("\n");
        
        List<Integer> riskCards = getHighRiskCards();
        report.append("CARTES À RISQUE: ").append(riskCards.size()).append("\n");
        
        int criticalAlerts = fraudeService.getCriticalAlerts().size();
        report.append("ALERTES CRITIQUES: ").append(criticalAlerts).append("\n");
        
        if(criticalAlerts > 0) {
            report.append("\n⚠️  ACTION REQUISE: Vérifier les alertes critiques\n");
        }
        
        return report.toString();
    }

    // Generate performance metrics
    public Map<String, Object> getPerformanceMetrics() {
        return Map.of(
            "totalCards", carteService.getAllCartes().size(),
            "totalOperations", operationService.getAllOperations().size(),
            "dailyVolume", getDailyTransactionVolume(),
            "activeCards", carteService.getAllCartes().stream()
                .filter(carte -> carte.getStatut() == StatuCarte.ACTIVE)
                .count(),
            "riskCards", getHighRiskCards().size()
        );
    }
}