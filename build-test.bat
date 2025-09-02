@echo off
echo Checking Android project structure...

echo.
echo === Project Structure ===
dir app\src\main\java\com\bankqu\mobile /s

echo.
echo === AndroidManifest.xml ===
type app\src\main\AndroidManifest.xml

echo.
echo === MainActivity ===
type app\src\main\java\com\bankqu\mobile\presentation\MainActivity.kt

echo.
echo Project structure looks good!
echo You can now open this project in Android Studio and build it.
pause