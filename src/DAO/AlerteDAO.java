package DAO;

import DAO.Inteface.Dao;
import Entity.Record.AlerteFraude;
import Entity.Enum.NiveauAlerte;
import Util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlerteDAO implements Dao<AlerteFraude> {

    @Override
    public Optional<AlerteFraude> getById(int id) throws SQLException {
        String sql = "SELECT id, description, niveau, idCarte FROM AlerteFraude WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new AlerteFraude(
                        rs.getInt("id"),
                        rs.getString("description"),
                        NiveauAlerte.valueOf(rs.getString("niveau")),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AlerteFraude> getAll() throws SQLException {
        List<AlerteFraude> alertes = new ArrayList<>();
        String sql = "SELECT id, description, niveau, idCarte FROM AlerteFraude";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                alertes.add(new AlerteFraude(
                    rs.getInt("id"),
                    rs.getString("description"),
                    NiveauAlerte.valueOf(rs.getString("niveau")),
                    rs.getString("idCarte")
                ));
            }
        }
        return alertes;
    }

    @Override
    public boolean save(AlerteFraude alerte) throws SQLException {
        String sql = "INSERT INTO AlerteFraude (description, niveau, idCarte) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, alerte.description());
            ps.setString(2, alerte.niveau().name());
            ps.setString(3, alerte.idCarte());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(AlerteFraude alerte) throws SQLException {
        String sql = "UPDATE AlerteFraude SET description = ?, niveau = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, alerte.description());
            ps.setString(2, alerte.niveau().name());
            ps.setInt(3, alerte.id());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM AlerteFraude WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<AlerteFraude> findByCarteId(String carteId) throws SQLException {
        List<AlerteFraude> alertes = new ArrayList<>();
        String sql = "SELECT id, description, niveau, idCarte FROM AlerteFraude WHERE idCarte = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, carteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alertes.add(new AlerteFraude(
                        rs.getInt("id"),
                        rs.getString("description"),
                        NiveauAlerte.valueOf(rs.getString("niveau")),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return alertes;
    }

    public List<AlerteFraude> findByNiveau(NiveauAlerte niveau) throws SQLException {
        List<AlerteFraude> alertes = new ArrayList<>();
        String sql = "SELECT id, description, niveau, idCarte FROM AlerteFraude WHERE niveau = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, niveau.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alertes.add(new AlerteFraude(
                        rs.getInt("id"),
                        rs.getString("description"),
                        NiveauAlerte.valueOf(rs.getString("niveau")),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return alertes;
    }

    public List<AlerteFraude> findCriticalAlerts() throws SQLException {
        return findByNiveau(NiveauAlerte.CRITIQUE);
    }
}