/*
CREATE TABLE Client (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nom VARCHAR(100) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        telephone VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE Carte (
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

CREATE TABLE OperationCarte (
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

CREATE TABLE AlerteFraude (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              description TEXT NOT NULL,
                              niveau ENUM('INFO', 'AVERTISSEMENT', 'CRITIQUE') NOT NULL,
                              idCarte INT NOT NULL,
                              FOREIGN KEY (idCarte) REFERENCES Carte(id)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE
);
*/

/*
-- Insert Clients
INSERT INTO Client (nom, email, telephone) VALUES
('Ahmed Benali', 'ahmed.benali@email.com', '0612345678'),
('Fatima Zahra', 'fatima.zahra@email.com', '0623456789'),
('Mohamed Alami', 'mohamed.alami@email.com', '0634567890');

-- Insert Cartes
INSERT INTO Carte (numero, dateExpiration, statut, typeCarte, idClient) VALUES
('1234567890123456', '2025-12-31', 'ACTIVE', 'DEBIT', 1),
('2345678901234567', '2026-06-30', 'ACTIVE', 'CREDIT', 1),
('3456789012345678', '2025-09-30', 'ACTIVE', 'PREPAYEE', 2),
('4567890123456789', '2024-12-31', 'SUSPENDUE', 'DEBIT', 3);

-- Insert Operations
INSERT INTO OperationCarte (date, montant, type, lieu, idCarte) VALUES
('2024-01-15 10:30:00', 150.00, 'ACHAT', 'Casablanca', 1),
('2024-01-15 14:20:00', 500.00, 'RETRAIT', 'Rabat', 1),
('2024-01-16 09:15:00', 75.50, 'PAIEMENT_EN_LIGNE', 'Paris', 2),
('2024-01-16 16:45:00', 200.00, 'ACHAT', 'Marrakech', 3);

-- Insert Alertes Fraude
INSERT INTO AlerteFraude (description, niveau, idCarte) VALUES
('Montant élevé détecté', 'AVERTISSEMENT', 1),
('Transactions dans deux pays différents', 'CRITIQUE', 2),
('Dépassement de plafond journalier', 'INFO', 3);
*/

