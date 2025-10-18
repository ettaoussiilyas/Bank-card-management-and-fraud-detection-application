-- Database Setup Script for Bank Card Management System
-- Execute this script to create the database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS bank_card_system;
USE bank_card_system;

-- Create Employee table
CREATE TABLE IF NOT EXISTS Employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'AGENT') NOT NULL
);

-- Create Client table
CREATE TABLE IF NOT EXISTS Client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Create Carte table
CREATE TABLE IF NOT EXISTS Carte (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero VARCHAR(16) UNIQUE NOT NULL,
    dateExpiration DATE NOT NULL,
    statut ENUM('ACTIVE', 'SUSPENDUE', 'BLOQUEE') NOT NULL,
    typeCarte VARCHAR(20) NOT NULL,
    idClient INT NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Create OperationCarte table
CREATE TABLE IF NOT EXISTS OperationCarte (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    type ENUM('ACHAT', 'RETRAIT', 'PAIEMENT_EN_LIGNE') NOT NULL,
    lieu VARCHAR(100) NOT NULL,
    idCarte INT NOT NULL,
    FOREIGN KEY (idCarte) REFERENCES Carte(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Create AlerteFraude table
CREATE TABLE IF NOT EXISTS AlerteFraude (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description TEXT NOT NULL,
    niveau ENUM('INFO', 'AVERTISSEMENT', 'CRITIQUE') NOT NULL,
    idCarte INT NOT NULL,
    FOREIGN KEY (idCarte) REFERENCES Carte(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Insert sample data
INSERT INTO Employee (username, email, password, nom, role) VALUES
('admin', 'admin@bank.com', 'admin123', 'Administrateur Système', 'ADMIN'),
('manager', 'manager@bank.com', 'manager123', 'Chef d\'Agence', 'MANAGER'),
('agent', 'agent@bank.com', 'agent123', 'Agent Bancaire', 'AGENT');

INSERT INTO Client (nom, email, telephone, password) VALUES
('Ahmed Benali', 'ahmed.benali@email.com', '0612345678', 'client123'),
('Fatima Zahra', 'fatima.zahra@email.com', '0623456789', 'client123'),
('Mohamed Alami', 'mohamed.alami@email.com', '0634567890', 'client123');

INSERT INTO Carte (numero, dateExpiration, statut, typeCarte, idClient) VALUES
('1234567890123456', '2025-12-31', 'ACTIVE', 'DEBIT', 1),
('2345678901234567', '2026-06-30', 'ACTIVE', 'CREDIT', 1),
('3456789012345678', '2025-09-30', 'ACTIVE', 'PREPAYEE', 2),
('4567890123456789', '2024-12-31', 'SUSPENDUE', 'DEBIT', 3);

INSERT INTO OperationCarte (date, montant, type, lieu, idCarte) VALUES
('2024-01-15 10:30:00', 150.00, 'ACHAT', 'Casablanca', 1),
('2024-01-15 14:20:00', 500.00, 'RETRAIT', 'Rabat', 1),
('2024-01-16 09:15:00', 75.50, 'PAIEMENT_EN_LIGNE', 'Paris', 2),
('2024-01-16 16:45:00', 200.00, 'ACHAT', 'Marrakech', 3);

INSERT INTO AlerteFraude (description, niveau, idCarte) VALUES
('Montant élevé détecté', 'AVERTISSEMENT', 1),
('Transactions dans deux pays différents', 'CRITIQUE', 2),
('Dépassement de plafond journalier', 'INFO', 3);

SELECT 'Database setup completed successfully!' as Status;