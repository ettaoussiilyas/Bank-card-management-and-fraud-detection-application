@echo off
echo Building Bank Card Management System...

REM Create build directory
if not exist "build" mkdir build

REM Compile all Java files
javac -d build -cp "lib/*" src/**/*.java src/*.java

REM Create JAR file
jar cfm BankCardSystem.jar MANIFEST.MF -C build .

echo Build completed! Run with: java -jar BankCardSystem.jar
pause