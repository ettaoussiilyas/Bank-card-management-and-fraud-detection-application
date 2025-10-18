package Service;

import DAO.ClientDAO;
import DAO.EmployeeDAO;
import Entity.Record.Client;
import Entity.Record.Employee;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    
    private final ClientDAO clientDao = new ClientDAO();
    private final EmployeeDAO employeeDao = new EmployeeDAO();
    
    public Optional<Employee> authenticateEmployee(String email, String password) {
        try {
            Optional<Employee> employee = employeeDao.findByEmail(email);
            if (employee.isPresent() && employee.get().password().equals(password)) {
                return employee;
            }
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    public Optional<Client> authenticateClient(String email, String password) {
        try {
            Optional<Client> client = clientDao.findByEmail(email);
            if (client.isPresent() && client.get().password().equals(password)) {
                return client;
            }
        } catch (SQLException e) {
            System.err.println("Client authentication error: " + e.getMessage());
        }
        return Optional.empty();
    }
}