@echo off
echo Testing compilation...

REM Create build directory
if not exist "build" mkdir build

REM Compile all Java files (without MySQL connector for now)
javac -d build src\**\*.java src\*.java

if %ERRORLEVEL% EQU 0 (
    echo ✅ Compilation successful!
    echo ✅ All classes compiled without errors
    echo.
    echo Next steps:
    echo 1. Download MySQL Connector JAR to lib\ directory
    echo 2. Run database_setup.sql in MySQL
    echo 3. Execute: build.bat
    echo 4. Run: java -jar BankCardSystem.jar
) else (
    echo ❌ Compilation failed!
    echo Check the error messages above
)

pause