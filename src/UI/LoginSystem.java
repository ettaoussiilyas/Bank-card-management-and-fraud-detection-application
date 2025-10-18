package UI;

import Service.AuthService;
import Entity.Record.Employee;
import Entity.Record.Client;
import java.util.Optional;
import java.util.Scanner;

public class LoginSystem {
    
    private final Scanner scanner = new Scanner(System.in);
    
    public void start() {
        while (true) {
            System.out.println("\n=== SYSTÈME BANCAIRE ===");
            System.out.println("1. Connexion");
            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            switch (choix) {
                case 1 -> login();
                case 0 -> {
                    System.out.println("Au revoir!");
                    return;
                }
                default -> System.out.println("Choix invalide!");
            }
        }
    }
    
    private void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();
        
        AuthService auth = new AuthService();
        
        Optional<Employee> employee = auth.authenticateEmployee(email, password);
        if (employee.isPresent()) {
            System.out.println("Bienvenue " + employee.get().nom() + " (" + employee.get().role() + ")");
            MenuPrincipal menu = new MenuPrincipal();
            menu.afficherMenu();
            return;
        }
        
        Optional<Client> client = auth.authenticateClient(email, password);
        if (client.isPresent()) {
            System.out.println("Bienvenue " + client.get().nom());
            ClientInterface clientInterface = new ClientInterface();
            clientInterface.setClientId(client.get().id());
            clientInterface.afficherMenuClient();
            return;
        }
        
        System.out.println("Identifiants incorrects!");
    }
}