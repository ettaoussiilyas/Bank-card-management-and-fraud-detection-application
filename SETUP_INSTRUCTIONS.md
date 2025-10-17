# 🚀 Setup Instructions - Bank Card Management System

## 📋 Prerequisites
- **Java 17** or higher
- **MySQL Server** (8.0 or higher recommended)
- **MySQL Connector/J** (JDBC Driver)

## 🗄️ Database Setup

### Step 1: Install MySQL
1. Download and install MySQL Server from [mysql.com](https://dev.mysql.com/downloads/mysql/)
2. Start MySQL service
3. Connect to MySQL as root user

### Step 2: Create Database
```bash
mysql -u root -p < database_setup.sql
```
Or manually execute the SQL commands in `database_setup.sql`

### Step 3: Configure Connection
Update database credentials in `src/Util/DataBaseConnection.java` if needed:
```java
private static final String url = "jdbc:mysql://localhost:3306/bank_card_system?useSSL=false&serverTimezone=UTC";
private static final String user = "root";
private static final String password = ""; // Update with your MySQL password
```

## 📚 Dependencies Setup

### Download MySQL Connector/J
1. Download `mysql-connector-j-8.2.0.jar` from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/)
2. Place the JAR file in the `lib/` directory

## 🔨 Build & Run

### Option 1: Using Build Script (Windows)
```bash
build.bat
java -jar BankCardSystem.jar
```

### Option 2: Manual Build
```bash
# Compile
javac -d build -cp "lib/*" src/**/*.java src/*.java

# Create JAR
jar cfm BankCardSystem.jar MANIFEST.MF -C build .

# Run
java -cp "lib/*;BankCardSystem.jar" Main
```

## ✅ Verification

### Test Database Connection
Run the application and verify:
1. "Connexion à la base de données réussie!" message appears
2. Menu system loads properly
3. You can create a client and see it in the database

### Test Core Features
1. **Create Client**: Menu option 1
2. **Issue Card**: Menu option 2  
3. **Perform Operation**: Menu option 3
4. **View History**: Menu option 4
5. **Fraud Analysis**: Menu option 5

## 🐛 Troubleshooting

### Common Issues:

**"ClassNotFoundException: com.mysql.cj.jdbc.Driver"**
- Solution: Ensure MySQL Connector JAR is in `lib/` directory

**"Access denied for user 'root'"**
- Solution: Update password in `DataBaseConnection.java`

**"Unknown database 'bank_card_system'"**
- Solution: Run `database_setup.sql` script

**Compilation errors**
- Solution: Ensure Java 17+ is installed and JAVA_HOME is set

## 📊 Sample Data
The setup script includes sample data:
- 3 clients
- 4 cards (different types)
- 4 operations
- 3 fraud alerts

## 🎯 Ready to Test!
Once setup is complete, the application should run without errors and all menu options should be functional.