@echo off
echo Compiling...
javac -d . backend/Student.java backend/FileUtil.java backend/app.java

if %errorlevel% neq 0 (
    echo [ERROR] Compilation Failed!
    pause
    exit /b
)

echo Starting Server...
java backend.app
pause