package DAO;

import DAO.Inteface.Dao;
import Entity.Record.Client;
import db.DataBaseConnection;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO implements Dao<Client> {

    @Override
    public Optional<Client> getById(int id) throws SQLException {
        String sql = "SELECT id, nom, email, telephone FROM client where id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if(resultSet.next()) {
                    return Optional.of(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("telephone")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, nom, email, telephone FROM client";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("telephone")
                ));
            }
        }

        return clients;
    }


    @Override
    public boolean save(Client client) throws SQLException {
        String sql = "INSERT INTO client (nom, email, telephone) VALUES (?, ?, ?)";
        try(
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);

        ){
            ps.setString(1, client.nom());
            ps.setString(2, client.email());
            ps.setString(3, client.telephone());
            int row = ps.executeUpdate();
            if(row > 0) return true;

        }
        return false;
    }

    @Override
    public boolean update(Client client) throws SQLException {
        String sql = "UPDATE client SET nom = ?, email = ?, telephone = ? WHERE id = ?";
        try (
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setString(1, client.nom());
            ps.setString(2, client.email());
            ps.setString(3, client.telephone());
            ps.setInt(4, client.id());
            int row = ps.executeUpdate();
            if(row > 0) return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM client WHERE id = ?";
        try(
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        }
    }

    public Optional<Client> findByEmail(String email) throws SQLException {
        String sql = "SELECT id, nom, email, telephone FROM client WHERE email = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Client> findByTelephone(String telephone) throws SQLException {
        String sql = "SELECT id, nom, email, telephone FROM client WHERE telephone = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                    ));
                }
            }
        }
        return Optional.empty();
    }
}
