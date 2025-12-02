@echo off
echo ==========================================
echo       Student Manager - Starting
echo ==========================================
echo.
echo [INFO] Ensure this computer has JRE (Java Runtime Environment) installed.
echo.
echo Starting Server...
echo Please open your browser and visit: http://localhost:8000
echo (Don't close this black window!)
echo.

REM 只运行，不编译！
java backend.app

pause