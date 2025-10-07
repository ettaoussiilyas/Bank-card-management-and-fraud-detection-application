# Instructions de Build et d'Exécution

## Prérequis
- Java 17 ou supérieur
- MySQL Server
- MySQL Connector/J (JDBC Driver)

## Configuration de la Base de Données
1. Créer une base de données MySQL nommée `bank_card_system`
2. Exécuter le script `Query.sql` pour créer les tables
3. Configurer les paramètres de connexion dans `DataBaseConnection.java`

## Compilation et Exécution

### Option 1: Compilation manuelle
```bash
# Compiler les sources
javac -d build -cp "lib/*" src/**/*.java src/*.java

# Créer le JAR
jar cfm BankCardSystem.jar MANIFEST.MF -C build .

# Exécuter
java -jar BankCardSystem.jar
```

### Option 2: Script de build (Windows)
```bash
build.bat
java -jar BankCardSystem.jar
```

## Fonctionnalités Disponibles
1. ✅ Gestion des clients
2. ✅ Émission de cartes (Débit, Crédit, Prépayée)
3. ✅ Enregistrement d'opérations
4. ✅ Consultation d'historique
5. ✅ Détection de fraude automatique
6. ✅ Gestion du statut des cartes
7. ✅ Rapports et statistiques
8. ✅ Import/Export CSV

## Fichiers de Test
- `sample_operations.csv`: Exemple d'opérations à importer
- `sample_cards.csv`: Exemple de cartes à importer

## Architecture Respectée
- ✅ Couche Entity (Records + Sealed Classes)
- ✅ Couche DAO (CRUD + JDBC)
- ✅ Couche Service (Logique métier)
- ✅ Couche UI (Menu textuel)
- ✅ Couche Utilitaire (Validation, génération)