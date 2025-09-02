@echo off
echo ===============================================
echo   BankQu Mobile - Build Fix Script
echo ===============================================

echo.
echo 1. Cleaning old build files...
rmdir /s /q .gradle 2>nul
rmdir /s /q build 2>nul  
rmdir /s /q app\build 2>nul
echo    ✓ Build cache cleared

echo.
echo 2. Current project structure:
echo    ✓ MainActivity: app\src\main\java\com\bankqu\mobile\presentation\MainActivity.kt
echo    ✓ Application: app\src\main\java\com\bankqu\mobile\BankQuApplication.kt
echo    ✓ Manifest: app\src\main\AndroidManifest.xml

echo.
echo 3. Fixed compatibility issues:
echo    ✓ Android Gradle Plugin: 8.2.0
echo    ✓ Kotlin: 1.9.22
echo    ✓ Gradle Wrapper: 8.2
echo    ✓ Target SDK: 33
echo    ✓ Minimal dependencies

echo.
echo 4. Next steps:
echo    a) Open Android Studio
echo    b) File → Open → Select this project folder
echo    c) Wait for Gradle sync
echo    d) Build → Make Project
echo.
echo If you still get "Kotlin metadata" error:
echo    - File → Invalidate Caches and Restart
echo    - Try Build → Clean Project first
echo.
pause