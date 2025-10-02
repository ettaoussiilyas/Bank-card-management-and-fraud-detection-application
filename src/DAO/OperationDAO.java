package DAO;

import DAO.Inteface.Dao;
import Entity.Record.OperationCarte;
import Entity.Enum.TypeOperation;
import Util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OperationDAO implements Dao<OperationCarte> {

    @Override
    public Optional<OperationCarte> getById(int id) throws SQLException {
        String sql = "SELECT id, date, montant, type, lieu, idCarte FROM OperationCarte WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new OperationCarte(
                        rs.getInt("id"),
                        rs.getDouble("montant"),
                        rs.getTimestamp("date"),
                        TypeOperation.valueOf(rs.getString("type")),
                        rs.getString("lieu"),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<OperationCarte> getAll() throws SQLException {
        List<OperationCarte> operations = new ArrayList<>();
        String sql = "SELECT id, date, montant, type, lieu, idCarte FROM OperationCarte";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                operations.add(new OperationCarte(
                    rs.getInt("id"),
                    rs.getDouble("montant"),
                    rs.getTimestamp("date"),
                    TypeOperation.valueOf(rs.getString("type")),
                    rs.getString("lieu"),
                    rs.getString("idCarte")
                ));
            }
        }
        return operations;
    }

    @Override
    public boolean save(OperationCarte operation) throws SQLException {
        String sql = "INSERT INTO OperationCarte (date, montant, type, lieu, idCarte) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setTimestamp(1, new Timestamp(operation.date().getTime()));
            ps.setDouble(2, operation.montant());
            ps.setString(3, operation.type().name());
            ps.setString(4, operation.lieu());
            ps.setString(5, operation.idCarte());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(OperationCarte operation) throws SQLException {
        String sql = "UPDATE OperationCarte SET date = ?, montant = ?, type = ?, lieu = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setTimestamp(1, new Timestamp(operation.date().getTime()));
            ps.setDouble(2, operation.montant());
            ps.setString(3, operation.type().name());
            ps.setString(4, operation.lieu());
            ps.setInt(5, operation.id());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM OperationCarte WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<OperationCarte> findByCarteId(String carteId) throws SQLException {
        List<OperationCarte> operations = new ArrayList<>();
        String sql = "SELECT id, date, montant, type, lieu, idCarte FROM OperationCarte WHERE idCarte = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, carteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operations.add(new OperationCarte(
                        rs.getInt("id"),
                        rs.getDouble("montant"),
                        rs.getTimestamp("date"),
                        TypeOperation.valueOf(rs.getString("type")),
                        rs.getString("lieu"),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return operations;
    }

    public List<OperationCarte> findByType(TypeOperation type) throws SQLException {
        List<OperationCarte> operations = new ArrayList<>();
        String sql = "SELECT id, date, montant, type, lieu, idCarte FROM OperationCarte WHERE type = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, type.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operations.add(new OperationCarte(
                        rs.getInt("id"),
                        rs.getDouble("montant"),
                        rs.getTimestamp("date"),
                        TypeOperation.valueOf(rs.getString("type")),
                        rs.getString("lieu"),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return operations;
    }

    public List<OperationCarte> findByDateRange(Date startDate, Date endDate) throws SQLException {
        List<OperationCarte> operations = new ArrayList<>();
        String sql = "SELECT id, date, montant, type, lieu, idCarte FROM OperationCarte WHERE date BETWEEN ? AND ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setTimestamp(1, new Timestamp(startDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operations.add(new OperationCarte(
                        rs.getInt("id"),
                        rs.getDouble("montant"),
                        rs.getTimestamp("date"),
                        TypeOperation.valueOf(rs.getString("type")),
                        rs.getString("lieu"),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return operations;
    }

    public List<OperationCarte> findByCarteAndDateRange(String carteId, Date startDate, Date endDate) throws SQLException {
        List<OperationCarte> operations = new ArrayList<>();
        String sql = "SELECT id, date, montant, type, lieu, idCarte FROM OperationCarte WHERE idCarte = ? AND date BETWEEN ? AND ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, carteId);
            ps.setTimestamp(2, new Timestamp(startDate.getTime()));
            ps.setTimestamp(3, new Timestamp(endDate.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operations.add(new OperationCarte(
                        rs.getInt("id"),
                        rs.getDouble("montant"),
                        rs.getTimestamp("date"),
                        TypeOperation.valueOf(rs.getString("type")),
                        rs.getString("lieu"),
                        rs.getString("idCarte")
                    ));
                }
            }
        }
        return operations;
    }
}