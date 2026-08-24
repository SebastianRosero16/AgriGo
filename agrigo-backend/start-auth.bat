@echo off
REM Cargar variables del .env
for /f "usebackq tokens=1,* delims==" %%A in (".env") do (
    if not "%%A"=="" (
        set "line=%%A"
        if not "!line:~0,1!"=="#" set "%%A=%%B"
    )
)

echo Iniciando auth-service con configuracion de email...
echo MAIL_USERNAME=%MAIL_USERNAME%

cd /d %~dp0auth-service
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.mail.username=%MAIL_USERNAME% --spring.mail.password=%MAIL_PASSWORD% --app.mail.from=%MAIL_USERNAME%"
