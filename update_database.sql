-- Update existing database to match current entities
USE bank_card_system;

-- Add Employee table if not exists
CREATE TABLE IF NOT EXISTS Employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'AGENT') NOT NULL
);

-- Add password column to Client table
ALTER TABLE Client ADD COLUMN IF NOT EXISTS password VARCHAR(100) NOT NULL DEFAULT 'client123';

-- Insert employees if not exist
INSERT IGNORE INTO Employee (username, email, password, nom, role) VALUES
('admin', 'admin@bank.com', 'admin123', 'Administrateur Système', 'ADMIN'),
('manager', 'manager@bank.com', 'manager123', 'Chef d\'Agence', 'MANAGER'),
('agent', 'agent@bank.com', 'agent123', 'Agent Bancaire', 'AGENT');

-- Update existing clients with passwords
UPDATE Client SET password = 'client123' WHERE password IS NULL OR password = '';

SELECT 'Database updated successfully!' as Status;