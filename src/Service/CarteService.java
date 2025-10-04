package Service;

import DAO.CarteDAO;
import DAO.ClientDAO;
import Entity.Enum.StatuCarte;
import Entity.SealedClass.*;
import Util.CarteNumberGenerator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CarteService {

    private final CarteDAO carteDao;
    private final ClientDAO clientDao;

    public CarteService(){
        carteDao = new CarteDAO();
        clientDao = new ClientDAO();
    }

    public boolean createCarte(String typeCarte, int clientId) {
        try {
            if(clientDao.getById(clientId).isEmpty()) {
                return false;
            }

            String numeroCard = CarteNumberGenerator.generateCardNumber();
            String dateExpiration = LocalDate.now().plusYears(3).toString();

            Carte carte = switch (typeCarte.toUpperCase()) {
                case "DEBIT" -> new CarteDebit("0", numeroCard, dateExpiration, 
                    StatuCarte.ACTIVE, typeCarte, clientId, 0.0f);
                case "CREDIT" -> new CarteCredit("0", numeroCard, dateExpiration, 
                    StatuCarte.ACTIVE, typeCarte, clientId, 0.0f);
                case "PREPAYEE" -> new CartePrepayee("0", numeroCard, dateExpiration, 
                    StatuCarte.ACTIVE, typeCarte, clientId, 0.0f);
                default -> null;
            };

            if(carte == null) return false;

            return carteDao.save(carte);

        } catch (SQLException e) {
            System.err.println("Error creating card: " + e.getMessage());
            return false;
        }
    }

    public boolean activateCarte(int carteId) {
        return updateCarteStatut(carteId, StatuCarte.ACTIVE);
    }

    public boolean blockCarte(int carteId) {
        return updateCarteStatut(carteId, StatuCarte.BLOQUEE);
    }

    public boolean suspendCarte(int carteId) {
        return updateCarteStatut(carteId, StatuCarte.SUSPENDUE);
    }

    public boolean updateCarteStatut(int carteId, StatuCarte statut) {
        try {
            return carteDao.updateStatut(carteId, statut);
        } catch (SQLException e) {
            System.err.println("Error updating card status: " + e.getMessage());
            return false;
        }
    }

    public Optional<Carte> getCarteById(int carteId) {
        try {
            return carteDao.getById(carteId);
        } catch (SQLException e) {
            System.err.println("Error retrieving card: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Get all cards for a client
    public List<Carte> getCartesByClientId(int clientId) {
        try {
            return carteDao.findByClientId(clientId);
        } catch (SQLException e) {
            System.err.println("Error retrieving client cards: " + e.getMessage());
            return List.of();
        }
    }

    // Find card by number
    public Optional<Carte> findCarteByNumero(String numero) {
        try {
            return carteDao.findByNumero(numero);
        } catch (SQLException e) {
            System.err.println("Error finding card by number: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Check if card is active and can perform operations
    public boolean isCarteActive(int carteId) {
        Optional<Carte> carte = getCarteById(carteId);
        return carte.isPresent() && carte.get().getStatut() == StatuCarte.ACTIVE;
    }

    // Validate card for operations
    public boolean validateCarteForOperation(int carteId) {
        Optional<Carte> carte = getCarteById(carteId);
        if(carte.isEmpty()) return false;
        
        // Check if card is active
        if(carte.get().getStatut() != StatuCarte.ACTIVE) return false;
        
        // Check expiration date
        LocalDate expiration = LocalDate.parse(carte.get().getDateExpiration());
        return expiration.isAfter(LocalDate.now());
    }

    // Get all cards
    public List<Carte> getAllCartes() {
        try {
            return carteDao.getAll();
        } catch (SQLException e) {
            System.err.println("Error retrieving all cards: " + e.getMessage());
            return List.of();
        }
    }
}