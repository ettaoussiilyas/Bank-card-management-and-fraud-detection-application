package UI;

import Service.*;
import Entity.Record.Client;
import Entity.SealedClass.Carte;
import Entity.Record.OperationCarte;
import Entity.Enum.TypeOperation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuPrincipal {
    
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService = new ClientService();
    private final CarteService carteService = new CarteService();
    private final OperationService operationService = new OperationService();
    private final FraudeService fraudeService = new FraudeService();
    private final RapportService rapportService = new RapportService();
    private final ImportExportService importExportService = new ImportExportService();
    
    public void afficherMenu() {
        while (true) {
            System.out.println("\n=== SYSTÈME DE GESTION DES CARTES BANCAIRES ===");
            System.out.println("1. Créer un client");
            System.out.println("2. Émettre une carte");
            System.out.println("3. Effectuer une opération");
            System.out.println("4. Consulter historique d'une carte");
            System.out.println("5. Analyser les fraudes");
            System.out.println("6. Bloquer/Suspendre une carte");
            System.out.println("7. Rapports et statistiques");
            System.out.println("8. Import/Export");
            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choix) {
                case 1 -> creerClient();
                case 2 -> emettrearte();
                case 3 -> effectuerOperation();
                case 4 -> consulterHistorique();
                case 5 -> analyserFraudes();
                case 6 -> gererCarte();
                case 7 -> afficherRapports();
                case 8 -> gererImportExport();
                case 0 -> {
                    System.out.println("Au revoir!");
                    return;
                }
                default -> System.out.println("Choix invalide!");
            }
        }
    }
    
    private void creerClient() {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Téléphone: ");
        String telephone = scanner.nextLine();
        
        if (clientService.createClient(nom, email, telephone)) {
            System.out.println("Client créé avec succès!");
        } else {
            System.out.println("Erreur lors de la création du client.");
        }
    }
    
    private void emettrearte() {
        System.out.print("ID du client: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Type de carte:");
        System.out.println("1. DEBIT");
        System.out.println("2. CREDIT");
        System.out.println("3. PREPAYEE");
        System.out.print("Votre choix: ");
        
        int typeChoix = scanner.nextInt();
        scanner.nextLine();
        
        String typeCarte = switch (typeChoix) {
            case 1 -> "DEBIT";
            case 2 -> "CREDIT";
            case 3 -> "PREPAYEE";
            default -> null;
        };
        
        if (typeCarte != null && carteService.createCarte(typeCarte, clientId)) {
            System.out.println("Carte émise avec succès!");
        } else {
            System.out.println("Erreur lors de l'émission de la carte.");
        }
    }
    
    private void effectuerOperation() {
        System.out.print("ID de la carte: ");
        int carteId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.println("Type d'opération:");
        System.out.println("1. ACHAT");
        System.out.println("2. RETRAIT");
        System.out.println("3. PAIEMENT_EN_LIGNE");
        System.out.print("Votre choix: ");
        
        int typeChoix = scanner.nextInt();
        scanner.nextLine();
        
        TypeOperation type = switch (typeChoix) {
            case 1 -> TypeOperation.ACHAT;
            case 2 -> TypeOperation.RETRAIT;
            case 3 -> TypeOperation.PAIEMENT_EN_LIGNE;
            default -> null;
        };
        
        System.out.print("Lieu: ");
        String lieu = scanner.nextLine();
        
        if (type != null && operationService.recordOperation(montant, type, lieu, carteId)) {
            System.out.println("Opération enregistrée avec succès!");
        } else {
            System.out.println("Erreur lors de l'opération.");
        }
    }
    
    private void consulterHistorique() {
        System.out.print("ID de la carte: ");
        int carteId = scanner.nextInt();
        scanner.nextLine();
        
        List<OperationCarte> operations = operationService.getOperationsByCarteId(carteId);
        
        if (operations.isEmpty()) {
            System.out.println("Aucune opération trouvée pour cette carte.");
        } else {
            System.out.println("\n=== HISTORIQUE DES OPÉRATIONS ===");
            operations.forEach(op -> 
                System.out.printf("Date: %s | Montant: %.2f€ | Type: %s | Lieu: %s%n",
                    op.date(), op.montant(), op.type(), op.lieu())
            );
        }
    }
    
    private void analyserFraudes() {
        System.out.print("ID de la carte (0 pour toutes): ");
        int carteId = scanner.nextInt();
        scanner.nextLine();
        
        if (carteId == 0) {
            fraudeService.analyzeAllCards();
            System.out.println("Analyse de fraude lancée pour toutes les cartes.");
        } else {
            fraudeService.analyzeCardForFraud(carteId);
            System.out.println("Analyse de fraude lancée pour la carte " + carteId);
        }
    }
    
    private void gererCarte() {
        System.out.print("ID de la carte: ");
        int carteId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Action:");
        System.out.println("1. Bloquer");
        System.out.println("2. Suspendre");
        System.out.println("3. Activer");
        System.out.print("Votre choix: ");
        
        int action = scanner.nextInt();
        scanner.nextLine();
        
        boolean success = switch (action) {
            case 1 -> carteService.blockCarte(carteId);
            case 2 -> carteService.suspendCarte(carteId);
            case 3 -> carteService.activateCarte(carteId);
            default -> false;
        };
        
        if (success) {
            System.out.println("Action effectuée avec succès!");
        } else {
            System.out.println("Erreur lors de l'action.");
        }
    }
    
    private void afficherRapports() {
        System.out.println("\n=== RAPPORTS ET STATISTIQUES ===");
        System.out.println("1. Top 5 cartes les plus utilisées");
        System.out.println("2. Cartes bloquées/suspectes");
        System.out.println("3. Statistiques générales");
        System.out.print("Votre choix: ");
        
        int choix = scanner.nextInt();
        scanner.nextLine();
        
        switch (choix) {
            case 1 -> {
                List<Integer> topCartes = rapportService.getTop5MostUsedCards();
                System.out.println("Top 5 cartes les plus utilisées: " + topCartes);
            }
            case 2 -> {
                List<Integer> cartesBloquees = rapportService.getBlockedCards();
                System.out.println("Cartes bloquées: " + cartesBloquees);
            }
            case 3 -> {
                System.out.println("Statistiques générales:");
                System.out.println("- Nombre total de cartes: " + rapportService.getTotalCardsCount());
                System.out.println("- Nombre total d'opérations: " + rapportService.getTotalOperationsCount());
            }
        }
    }
    
    private void gererImportExport() {
        System.out.println("\n=== IMPORT/EXPORT ===");
        System.out.println("1. Importer opérations (CSV)");
        System.out.println("2. Exporter opérations (CSV)");
        System.out.println("3. Importer cartes (CSV)");
        System.out.print("Votre choix: ");
        
        int choix = scanner.nextInt();
        scanner.nextLine();
        
        switch (choix) {
            case 1 -> {
                System.out.print("Chemin du fichier CSV: ");
                String filePath = scanner.nextLine();
                if (importExportService.importOperationsFromCSV(filePath)) {
                    System.out.println("Import réussi!");
                } else {
                    System.out.println("Erreur lors de l'import.");
                }
            }
            case 2 -> {
                System.out.print("ID de la carte (0 pour toutes): ");
                int carteId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Chemin du fichier de sortie: ");
                String outputPath = scanner.nextLine();
                
                boolean success = carteId == 0 ? 
                    importExportService.exportAllOperationsToCSV(outputPath) :
                    importExportService.exportOperationsToCSV(outputPath, carteId);
                    
                if (success) {
                    System.out.println("Export réussi!");
                } else {
                    System.out.println("Erreur lors de l'export.");
                }
            }
            case 3 -> {
                System.out.print("Chemin du fichier CSV: ");
                String filePath = scanner.nextLine();
                if (importExportService.importCardsFromCSV(filePath)) {
                    System.out.println("Import des cartes réussi!");
                } else {
                    System.out.println("Erreur lors de l'import des cartes.");
                }
            }
        }
    }
}