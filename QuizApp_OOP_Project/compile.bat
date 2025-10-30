@echo off
echo COMPILING...
if not exist build mkdir build
javac -cp "lib\mysql-connector-j-9.4.0.jar" -d build src\*.java
if %errorlevel% == 0 (
    echo SUCCESS! Run: run.bat
) else (
    echo FAILED!
)
pause