package Service;

import DAO.OperationDAO;
import Entity.Record.OperationCarte;
import Entity.Enum.TypeOperation;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OperationService {

    private final OperationDAO operationDao;
    private final CarteService carteService;

    public OperationService(){
        operationDao = new OperationDAO();
        carteService = new CarteService();
    }

    // Record new operation with validation
    public boolean recordOperation(double montant, TypeOperation type, String lieu, int idCarte) {
        // Validate inputs
        if(montant <= 0 || lieu == null || lieu.trim().isEmpty()) {
            return false;
        }

        // Validate card can perform operations
        if(!carteService.validateCarteForOperation(idCarte)) {
            return false;
        }

        try {
            OperationCarte operation = new OperationCarte(
                0, // ID will be auto-generated
                montant,
                new Date(),
                type,
                lieu,
                idCarte
            );

            return operationDao.save(operation);

        } catch (SQLException e) {
            System.err.println("Error recording operation: " + e.getMessage());
            return false;
        }
    }

    // Get operation by ID
    public Optional<OperationCarte> getOperationById(int id) {
        try {
            return operationDao.getById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving operation: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Get all operations for a card
    public List<OperationCarte> getOperationsByCarteId(int carteId) {
        try {
            return operationDao.findByCarteId(carteId);
        } catch (SQLException e) {
            System.err.println("Error retrieving card operations: " + e.getMessage());
            return List.of();
        }
    }

    // Get operations by type
    public List<OperationCarte> getOperationsByType(TypeOperation type) {
        try {
            return operationDao.findByType(type);
        } catch (SQLException e) {
            System.err.println("Error retrieving operations by type: " + e.getMessage());
            return List.of();
        }
    }

    // Get operations in date range
    public List<OperationCarte> getOperationsByDateRange(Date startDate, Date endDate) {
        try {
            return operationDao.findByDateRange(startDate, endDate);
        } catch (SQLException e) {
            System.err.println("Error retrieving operations by date: " + e.getMessage());
            return List.of();
        }
    }

    // Get operations for card in date range
    public List<OperationCarte> getOperationsByCarteAndDateRange(int carteId, Date startDate, Date endDate) {
        try {
            return operationDao.findByCarteAndDateRange(carteId, startDate, endDate);
        } catch (SQLException e) {
            System.err.println("Error retrieving card operations by date: " + e.getMessage());
            return List.of();
        }
    }

    // Calculate total amount for card operations
    public double getTotalAmountForCarte(int carteId) {
        List<OperationCarte> operations = getOperationsByCarteId(carteId);
        return operations.stream()
                .mapToDouble(OperationCarte::montant)
                .sum();
    }

    // Get recent operations for fraud analysis
    public List<OperationCarte> getRecentOperationsForCarte(int carteId, int hours) {
        Date startDate = new Date(System.currentTimeMillis() - (hours * 60 * 60 * 1000L));
        Date endDate = new Date();
        return getOperationsByCarteAndDateRange(carteId, startDate, endDate);
    }

    // Get all operations
    public List<OperationCarte> getAllOperations() {
        try {
            return operationDao.getAll();
        } catch (SQLException e) {
            System.err.println("Error retrieving all operations: " + e.getMessage());
            return List.of();
        }
    }

    // Validate operation amount based on card type
    public boolean validateOperationAmount(int carteId, double montant, TypeOperation type) {
        // Basic validation
        if(montant <= 0) return false;
        
        // Additional business rules can be added here
        // For example: daily limits, transaction limits, etc.
        
        return true;
    }
}