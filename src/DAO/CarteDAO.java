package DAO;

import DAO.Inteface.Dao;
import Entity.Enum.StatuCarte;
import Entity.SealedClass.*;
import Util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarteDAO implements Dao<Carte> {

    @Override
    public Optional<Carte> getById(int id) throws SQLException {
        String sql = "SELECT id, numero, dateExpiration, statut, typeCarte, idClient FROM Carte WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, String.valueOf(id));
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(createCarteFromResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Carte> getAll() throws SQLException {
        List<Carte> cartes = new ArrayList<>();
        String sql = "SELECT id, numero, dateExpiration, statut, typeCarte, idClient FROM Carte";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                cartes.add(createCarteFromResultSet(rs));
            }
        }
        return cartes;
    }

    @Override
    public boolean save(Carte carte) throws SQLException {
        String sql = "INSERT INTO Carte (id, numero, dateExpiration, statut, typeCarte, idClient) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, carte.getId());
            ps.setString(2, carte.getNumero());
            ps.setString(3, carte.getDateExpiration());
            ps.setString(4, carte.getStatut().name());
            ps.setString(5, carte.getTypeCarte());
            ps.setInt(6, carte.getIdClient());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Carte carte) throws SQLException {
        String sql = "UPDATE Carte SET numero = ?, dateExpiration = ?, statut = ?, typeCarte = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, carte.getNumero());
            ps.setString(2, carte.getDateExpiration());
            ps.setString(3, carte.getStatut().name());
            ps.setString(4, carte.getTypeCarte());
            ps.setString(5, carte.getId());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Carte WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, String.valueOf(id));
            return ps.executeUpdate() > 0;
        }
    }

    // README requirement: recherche par client
    public List<Carte> findByClientId(int clientId) throws SQLException {
        List<Carte> cartes = new ArrayList<>();
        String sql = "SELECT id, numero, dateExpiration, statut, typeCarte, idClient FROM Carte WHERE idClient = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cartes.add(createCarteFromResultSet(rs));
                }
            }
        }
        return cartes;
    }

    public Optional<Carte> findByNumero(String numero) throws SQLException {
        String sql = "SELECT id, numero, dateExpiration, statut, typeCarte, idClient FROM Carte WHERE numero = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(createCarteFromResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    public boolean updateStatut(String carteId, StatuCarte statut) throws SQLException {
        String sql = "UPDATE Carte SET statut = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, statut.name());
            ps.setString(2, carteId);
            return ps.executeUpdate() > 0;
        }
    }

    private Carte createCarteFromResultSet(ResultSet rs) throws SQLException {
        String typeCarte = rs.getString("typeCarte");
        
        return switch (typeCarte.toUpperCase()) {
            case "DEBIT" -> new CarteDebit(
                rs.getString("id"),
                rs.getString("numero"),
                rs.getString("dateExpiration"),
                StatuCarte.valueOf(rs.getString("statut")),
                typeCarte,
                rs.getInt("idClient"),
                0.0f
            );
            case "CREDIT" -> new CarteCredit(
                rs.getString("id"),
                rs.getString("numero"),
                rs.getString("dateExpiration"),
                StatuCarte.valueOf(rs.getString("statut")),
                typeCarte,
                rs.getInt("idClient"),
                0.0f
            );
            case "PREPAYEE" -> new CartePrepayee(
                rs.getString("id"),
                rs.getString("numero"),
                rs.getString("dateExpiration"),
                StatuCarte.valueOf(rs.getString("statut")),
                typeCarte,
                rs.getInt("idClient"),
                0.0f
            );
            default -> throw new SQLException("Type de carte inconnu: " + typeCarte);
        };
    }
}