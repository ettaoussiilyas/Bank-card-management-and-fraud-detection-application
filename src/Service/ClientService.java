package Service;

import DAO.ClientDAO;
import Entity.Record.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ClientService {

    private final ClientDAO clientDao;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{8,15}$");

    public ClientService(){
        clientDao = new ClientDAO();
    }

    public boolean createClient(String nom, String email, String telephone, String password) {
        Client client = new Client(0, nom, email, telephone, password);
        return createClient(client);
    }
    
    public boolean createClient(String nom, String email, String telephone) {
        return createClient(nom, email, telephone, "client123");
    }

    public boolean createClient(Client client){
        if(!validateClientData(client)) {
            System.err.println("Données client invalides:");
            if(client.nom() == null || client.nom().trim().isEmpty()) System.err.println("- Nom requis");
            if(client.email() == null || !EMAIL_PATTERN.matcher(client.email()).matches()) System.err.println("- Email invalide");
            if(client.telephone() == null || !PHONE_PATTERN.matcher(client.telephone()).matches()) System.err.println("- Téléphone invalide (8-15 chiffres requis)");
            if(client.password() == null || client.password().trim().isEmpty()) System.err.println("- Mot de passe requis");
            return false;
        }
        
        try {
            if(clientDao.findByEmail(client.email()).isPresent()) {
                System.err.println("Email déjà utilisé: " + client.email());
                return false;
            }

            if(clientDao.findByTelephone(client.telephone()).isPresent()) {
                System.err.println("Téléphone déjà utilisé: " + client.telephone());
                return false;
            }
            
            return clientDao.save(client);
            
        } catch (SQLException e) {
            System.err.println("Database error while creating client: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClient(Client client){
        if(!validateClientData(client)) {
            return false;
        }
        
        try {
            if(clientDao.getById(client.id()).isEmpty()) {
                return false;
            }
            
            Optional<Client> existingEmail = clientDao.findByEmail(client.email());
            if(existingEmail.isPresent() && existingEmail.get().id() != client.id()) {
                return false;
            }
            
            Optional<Client> existingPhone = clientDao.findByTelephone(client.telephone());
            if(existingPhone.isPresent() && existingPhone.get().id() != client.id()) {
                return false;
            }
            
            return clientDao.update(client);
            
        } catch (SQLException e) {
            System.err.println("Error updating client: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteClient(int clientId){
        try {
            return clientDao.delete(clientId);
        } catch (SQLException e) {
            System.err.println("Error deleting client: " + e.getMessage());
            return false;
        }
    }

    public Optional<Client> getClientById(int id){
        try {
            return clientDao.getById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving client: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Client> findClientByEmail(String email){
        try {
            return clientDao.findByEmail(email);
        } catch (SQLException e) {
            System.err.println("Error finding client by email: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Client> findClientByTelephone(String telephone){
        try {
            return clientDao.findByTelephone(telephone);
        } catch (SQLException e) {
            System.err.println("Error finding client by telephone: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<Client> getAllClients(){
        try {
            return clientDao.getAll();
        } catch (SQLException e) {
            System.err.println("Error retrieving all clients: " + e.getMessage());
            return List.of();
        }
    }

    private boolean validateClientData(Client client) {
        if(client == null) return false;
        if(client.nom() == null || client.nom().trim().isEmpty()) return false;
        if(client.email() == null || !EMAIL_PATTERN.matcher(client.email()).matches()) return false;
        if(client.telephone() == null || !PHONE_PATTERN.matcher(client.telephone()).matches()) return false;
        if(client.password() == null || client.password().trim().isEmpty()) return false;
        return true;
    }
}