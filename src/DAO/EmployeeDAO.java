package DAO;

import DAO.Inteface.Dao;
import Entity.Record.Employee;
import db.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements Dao<Employee> {

    @Override
    public Optional<Employee> getById(int id) throws SQLException {
        String sql = "SELECT id, username, email, password, nom, role FROM Employee WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new Employee(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("role")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, username, email, password, nom, role FROM Employee";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                employees.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("nom"),
                    rs.getString("role")
                ));
            }
        }
        return employees;
    }

    @Override
    public boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee (username, email, password, nom, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, employee.username());
            ps.setString(2, employee.email());
            ps.setString(3, employee.password());
            ps.setString(4, employee.nom());
            ps.setString(5, employee.role());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET username = ?, email = ?, password = ?, nom = ?, role = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, employee.username());
            ps.setString(2, employee.email());
            ps.setString(3, employee.password());
            ps.setString(4, employee.nom());
            ps.setString(5, employee.role());
            ps.setInt(6, employee.id());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Optional<Employee> findByEmail(String email) throws SQLException {
        String sql = "SELECT id, username, email, password, nom, role FROM Employee WHERE email = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new Employee(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("role")
                    ));
                }
            }
        }
        return Optional.empty();
    }
}