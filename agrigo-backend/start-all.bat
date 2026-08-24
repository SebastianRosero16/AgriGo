@echo off
echo ========================================
echo   AgriGo Backend - Iniciando servicios
echo ========================================

REM Cargar variables del .env
for /f "usebackq tokens=1,2 delims==" %%A in (".env") do (
    if not "%%A"=="" if not "%%A:~0,1%"=="#" set "%%A=%%B"
)

echo Instalando common...
call mvn install -pl common -am -q

echo.
echo [1/5] Iniciando auth-service (puerto 8081)...
start "auth-service" cmd /k "cd /d %~dp0auth-service && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-DMAIL_USERNAME=%MAIL_USERNAME% -Dspring-boot.run.jvmArguments=-DMAIL_PASSWORD=%MAIL_PASSWORD%"

timeout /t 5 /nobreak > nul

echo [2/5] Iniciando farmer-service (puerto 8082)...
start "farmer-service" cmd /k "cd /d %~dp0farmer-service && mvn spring-boot:run"

timeout /t 3 /nobreak > nul

echo [3/5] Iniciando store-service (puerto 8083)...
start "store-service" cmd /k "cd /d %~dp0store-service && mvn spring-boot:run"

timeout /t 3 /nobreak > nul

echo [4/5] Iniciando ai-recommendation-service (puerto 8085)...
start "ai-service" cmd /k "cd /d %~dp0ai-recommendation-service && mvn spring-boot:run"

timeout /t 3 /nobreak > nul

echo [5/5] Iniciando product-marketplace-service (puerto 8086)...
start "marketplace-service" cmd /k "cd /d %~dp0product-marketplace-service && mvn spring-boot:run"

echo.
echo ========================================
echo Servicios iniciando...
echo Puertos:
echo   auth:        http://localhost:8081
echo   farmer:      http://localhost:8082
echo   store:       http://localhost:8083
echo   ai:          http://localhost:8085
echo   marketplace: http://localhost:8086
echo ========================================
