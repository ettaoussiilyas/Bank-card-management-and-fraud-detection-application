/*
CREATE TABLE Client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20) NOT NULL
);
*/
/*
CREATE TABLE Carte (
    id VARCHAR(80) PRIMARY KEY,
    numero VARCHAR(16) UNIQUE NOT NULL,
    dateExpiration DATE NOT NULL,
    statut ENUM('ACTIVE', 'SUSPENDUE', 'BLOQUEE') NOT NULL,
    typeCarte VARCHAR(20) NOT NULL,
    idClient INT NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
*/
/*CREATE TABLE OperationCarte (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    type ENUM('ACHAT', 'RETRAIT', 'PAIEMENT_EN_LIGNE') NOT NULL,
    lieu VARCHAR(100) NOT NULL,
    idCarte VARCHAR(80) NOT NULL,
    FOREIGN KEY (idCarte) REFERENCES Carte(id)
);*/
/*CREATE TABLE OperationCarte (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    type ENUM('ACHAT', 'RETRAIT', 'PAIEMENT_EN_LIGNE') NOT NULL,
    lieu VARCHAR(100) NOT NULL,
    idCarte VARCHAR(80) NOT NULL,
    FOREIGN KEY (idCarte) REFERENCES Carte(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);*/
/*CREATE TABLE AlerteFraude (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description TEXT NOT NULL,
    niveau ENUM('INFO', 'AVERTISSEMENT', 'CRITIQUE') NOT NULL,
    idCarte VARCHAR(80) NOT NULL,
    FOREIGN KEY (idCarte) REFERENCES Carte(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);*/

/*
-- Insert Clients
INSERT INTO Client (nom, email, telephone) VALUES
('Ahmed Benali', 'ahmed.benali@email.com', '0612345678'),
('Fatima Zahra', 'fatima.zahra@email.com', '0623456789'),
('Mohamed Alami', 'mohamed.alami@email.com', '0634567890');

-- Insert Cartes (using string IDs)
INSERT INTO Carte (id, numero, dateExpiration, statut, typeCarte, idClient) VALUES
('C1', '1234567890123456', '2025-12-31', 'ACTIVE', 'DEBIT', 1),
('C2', '2345678901234567', '2026-06-30', 'ACTIVE', 'CREDIT', 1),
('C3', '3456789012345678', '2025-09-30', 'ACTIVE', 'PREPAYEE', 2),
('C4', '4567890123456789', '2024-12-31', 'SUSPENDUE', 'DEBIT', 3);

-- Insert Operations
INSERT INTO OperationCarte (date, montant, type, lieu, idCarte) VALUES
('2024-01-15 10:30:00', 150.00, 'ACHAT', 'Casablanca', 'C1'),
('2024-01-15 14:20:00', 500.00, 'RETRAIT', 'Rabat', 'C1'),
('2024-01-16 09:15:00', 75.50, 'PAIEMENT_EN_LIGNE', 'Paris', 'C2'),
('2024-01-16 16:45:00', 200.00, 'ACHAT', 'Marrakech', 'C3');

-- Insert Alertes Fraude
INSERT INTO AlerteFraude (description, niveau, idCarte) VALUES
('Montant élevé détecté', 'AVERTISSEMENT', 'C1'),
('Transactions dans deux pays différents', 'CRITIQUE', 'C2'),
('Dépassement de plafond journalier', 'INFO', 'C3');
*/

