package DAO;

import DAO.Inteface.Dao;
import Entity.Record.Client;
import Util.DataBaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//public class ClientDAO implements Dao<Client> {
public class ClientDAO {

    /*List<Client>  clients = new ArrayList<>();

    @Override
    public void save(Client client) {
        String query = "INSERT INTO Client (nom, email, telephone) VALUES ('" + client.nom() + "', '" + client.email() + "', '" + client.telephone() + "')";
        DataBaseConnection.getConnection();
        clients.add(client);
    }

    @Override
    public void update(Client client, String[] params) {
    }

    @Override
    public void delete(Client client) {
        clients.remove(client);
    }

    @Override
    public Optional<Client> getById(long id) {
        return clients.stream().filter(client -> client.id() == id).findFirst();
    }

    @Override
    public List<Client> getAll() {
        return clients;
    }

    public Optional<Client> findByEmail(String email) {
        return clients.stream().filter(client -> client.email() == email).findFirst();
    }

    public  Optional<Client> findByTelephone(String telephone){
        return clients.stream().filter(client -> client.telephone() == telephone).findFirst();
    }*/




}
