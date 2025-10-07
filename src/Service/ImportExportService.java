package Service;

import Entity.Record.OperationCarte;
import Entity.SealedClass.Carte;
import Entity.Enum.TypeOperation;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportExportService {

    private final CarteService carteService;
    private final OperationService operationService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ImportExportService(){
        carteService = new CarteService();
        operationService = new OperationService();
    }

    // Import operations from CSV file
    public boolean importOperationsFromCSV(String filePath) {
        List<String> errors = new ArrayList<>();
        int successCount = 0;
        int errorCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                try {
                    if (processOperationLine(line)) {
                        successCount++;
                    } else {
                        errorCount++;
                        errors.add("Ligne invalide: " + line);
                    }
                } catch (Exception e) {
                    errorCount++;
                    errors.add("Erreur ligne: " + line + " - " + e.getMessage());
                }
            }

            // Log results
            System.out.println("Import terminé:");
            System.out.println("- Succès: " + successCount);
            System.out.println("- Erreurs: " + errorCount);
            
            if (!errors.isEmpty()) {
                System.out.println("Erreurs détaillées:");
                errors.forEach(System.out::println);
            }

            return errorCount == 0;

        } catch (IOException e) {
            System.err.println("Erreur lecture fichier: " + e.getMessage());
            return false;
        }
    }

    // Process single operation line from CSV
    private boolean processOperationLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 5) {
                return false;
            }

            // Parse CSV: date,montant,type,lieu,idCarte
            Date date = dateFormat.parse(parts[0].trim());
            double montant = Double.parseDouble(parts[1].trim());
            TypeOperation type = TypeOperation.valueOf(parts[2].trim().toUpperCase());
            String lieu = parts[3].trim();
            int idCarte = Integer.parseInt(parts[4].trim());

            // Validate card exists and is active
            if (!carteService.validateCarteForOperation(idCarte)) {
                return false;
            }

            // Record operation
            return operationService.recordOperation(montant, type, lieu, idCarte);

        } catch (ParseException | IllegalArgumentException e) {
            return false;
        }
    }

    // Export operations to CSV file
    public boolean exportOperationsToCSV(String filePath, int carteId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            writer.println("Date,Montant,Type,Lieu,CarteID");

            // Get operations for card
            List<OperationCarte> operations = operationService.getOperationsByCarteId(carteId);

            // Write data
            for (OperationCarte operation : operations) {
                writer.printf("%s,%.2f,%s,%s,%d%n",
                    dateFormat.format(operation.date()),
                    operation.montant(),
                    operation.type(),
                    operation.lieu(),
                    operation.idCarte()
                );
            }

            System.out.println("Export réussi: " + operations.size() + " opérations exportées");
            return true;

        } catch (IOException e) {
            System.err.println("Erreur export: " + e.getMessage());
            return false;
        }
    }

    // Export all operations to CSV
    public boolean exportAllOperationsToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            writer.println("ID,Date,Montant,Type,Lieu,CarteID");

            // Get all operations
            List<OperationCarte> operations = operationService.getAllOperations();

            // Write data
            for (OperationCarte operation : operations) {
                writer.printf("%d,%s,%.2f,%s,%s,%d%n",
                    operation.id(),
                    dateFormat.format(operation.date()),
                    operation.montant(),
                    operation.type(),
                    operation.lieu(),
                    operation.idCarte()
                );
            }

            System.out.println("Export complet réussi: " + operations.size() + " opérations exportées");
            return true;

        } catch (IOException e) {
            System.err.println("Erreur export complet: " + e.getMessage());
            return false;
        }
    }

    // Import cards from CSV file
    public boolean importCardsFromCSV(String filePath) {
        List<String> errors = new ArrayList<>();
        int successCount = 0;
        int errorCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                try {
                    if (processCardLine(line)) {
                        successCount++;
                    } else {
                        errorCount++;
                        errors.add("Ligne carte invalide: " + line);
                    }
                } catch (Exception e) {
                    errorCount++;
                    errors.add("Erreur ligne carte: " + line + " - " + e.getMessage());
                }
            }

            System.out.println("Import cartes terminé:");
            System.out.println("- Succès: " + successCount);
            System.out.println("- Erreurs: " + errorCount);

            return errorCount == 0;

        } catch (IOException e) {
            System.err.println("Erreur lecture fichier cartes: " + e.getMessage());
            return false;
        }
    }

    // Process single card line from CSV
    private boolean processCardLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 2) {
                return false;
            }

            // Parse CSV: typeCarte,clientId (ID auto-generated)
            String typeCarte = parts[0].trim();
            int clientId = Integer.parseInt(parts[1].trim());

            // Create card
            return carteService.createCarte(typeCarte, clientId);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validate CSV file format
    public boolean validateCSVFormat(String filePath, String expectedHeader) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            return firstLine != null && firstLine.equals(expectedHeader);
        } catch (IOException e) {
            return false;
        }
    }

    // Get import statistics
    public String getImportStatistics(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            long lineCount = reader.lines().count() - 1; // Exclude header
            return "Fichier: " + filePath + " contient " + lineCount + " enregistrements";
        } catch (IOException e) {
            return "Erreur lecture fichier: " + e.getMessage();
        }
    }
}