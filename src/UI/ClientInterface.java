package UI;

import Service.*;
import Entity.Record.OperationCarte;
import Entity.Record.Client;
import Entity.SealedClass.Carte;
import Entity.Record.AlerteFraude;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientInterface {
    
    private final Scanner scanner = new Scanner(System.in);
    private final CarteService carteService = new CarteService();
    private final OperationService operationService = new OperationService();
    private final FraudeService fraudeService = new FraudeService();
    private int clientId;
    
    public void setClientId(int id) {
        this.clientId = id;
    }
    
    public void afficherMenuClient() {
        while (true) {
            System.out.println("\n=== ESPACE CLIENT ===");
            System.out.println("1. Mes cartes");
            System.out.println("2. Historique des opérations");
            System.out.println("3. Mes alertes");
            System.out.println("0. Déconnexion");
            System.out.print("Votre choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            switch (choix) {
                case 1 -> afficherMesCartes();
                case 2 -> afficherHistorique();
                case 3 -> afficherMesAlertes();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide!");
            }
        }
    }
    
    private void afficherMesCartes() {
        List<Carte> cartes = carteService.getCartesByClientId(clientId);
        System.out.println("\n=== MES CARTES ===");
        cartes.forEach(carte -> 
            System.out.printf("ID: %s | Numéro: %s | Type: %s | Statut: %s%n",
                carte.getId(), carte.getNumero(), carte.getTypeCarte(), carte.getStatut())
        );
    }
    
    private void afficherHistorique() {
        System.out.print("ID de la carte: ");
        int carteId = scanner.nextInt();
        scanner.nextLine();
        
        List<OperationCarte> operations = operationService.getOperationsByCarteId(carteId);
        System.out.println("\n=== HISTORIQUE ===");
        operations.forEach(op -> 
            System.out.printf("Date: %s | Montant: %.2f€ | Type: %s | Lieu: %s%n",
                op.date(), op.montant(), op.type(), op.lieu())
        );
    }
    
    private void afficherMesAlertes() {
        List<Carte> cartes = carteService.getCartesByClientId(clientId);
        System.out.println("\n=== MES ALERTES ===");
        cartes.forEach(carte -> {
            List<AlerteFraude> alertes = fraudeService.getAlertsForCarte(Integer.parseInt(carte.getId()));
            alertes.forEach(alerte -> 
                System.out.printf("Carte %s | %s: %s%n",
                    carte.getId(), alerte.niveau(), alerte.description())
            );
        });
    }
}