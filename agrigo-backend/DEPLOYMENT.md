# 🚀 Guía de Deployment - Paso a Paso

## ✅ PASO 1: Compilar el Proyecto (YA HECHO)

Ya compilamos todos los servicios. Los archivos JAR están listos en:
- `auth-service/target/auth-service-1.0.0.jar`
- `farmer-service/target/farmer-service-1.0.0.jar`
- `store-service/target/store-service-1.0.0.jar`
- `payment-service/target/payment-service-1.0.0.jar`
- `ai-recommendation-service/target/ai-recommendation-service-1.0.0.jar`
- `product-marketplace-service/target/product-marketplace-service-1.0.0.jar`
- `inventory-service/target/inventory-service-1.0.0.jar`
- `notification-service/target/notification-service-1.0.0.jar`

---

## 📦 PASO 2: Crear Cuenta en Senode

1. Abre tu navegador y ve a: **https://senode.com**
2. Click en "Sign Up" o "Registrarse"
3. Completa el formulario con tu email y contraseña
4. Verifica tu email
5. Inicia sesión en Senode

---

## 🗄️ PASO 3: Crear Base de Datos MySQL

### 3.1 Crear el servicio MySQL
1. En el dashboard de Senode, busca "Create New Service" o "Nuevo Servicio"
2. Selecciona **"MySQL 8.0"**
3. Dale un nombre: `agrigo-mysql`
4. Selecciona el plan (puede ser el gratuito para empezar)
5. Click en "Create" o "Crear"

### 3.2 Obtener credenciales
Senode te dará algo como esto (GUÁRDALAS):
```
Host: mysql-xxxxx.senode.com
Puerto: 3306
Usuario: admin_xxxxx
Contraseña: xxxxxxxxxx
```

### 3.3 Conectar a la base de datos
Tienes 2 opciones:

**Opción A - Desde Senode (más fácil):**
1. En Senode, busca tu servicio MySQL
2. Click en "Connect" o "Conectar"
3. Se abrirá una consola SQL
4. Copia TODO el contenido del archivo `init-db.sql`
5. Pégalo en la consola y ejecuta

**Opción B - Desde tu computadora:**
1. Descarga MySQL Workbench o DBeaver
2. Crea nueva conexión con los datos de Senode
3. Abre el archivo `init-db.sql`
4. Ejecuta el script completo

---

## 🔑 PASO 4: Obtener API Keys (Opcional pero Recomendado)

### Para Hugging Face (IA gratuita):
1. Ve a: https://huggingface.co
2. Crea cuenta gratis
3. Ve a Settings → Access Tokens
4. Crea un nuevo token
5. Copia el token (lo usaremos después)

### Para Bancolombia y Nequi:
- Necesitas contactar a Bancolombia y Nequi para obtener credenciales de producción
- Por ahora puedes dejar estos campos vacíos (el sistema funcionará sin pagos reales)

---

## 🚢 PASO 5: Deploy de Cada Microservicio

Vamos a subir los 8 servicios uno por uno. Te muestro el proceso completo para el primero:

### 5.1 AUTH SERVICE (Puerto 8081)

1. **En Senode, click en "New Service" o "Nuevo Servicio"**

2. **Selecciona "Docker"**

3. **Configuración básica:**
   - Nombre: `auth-service`
   - Puerto: `8081`

4. **Subir archivos:**
   - Sube el Dockerfile: `auth-service/Dockerfile`
   - Sube el JAR: `auth-service/target/auth-service-1.0.0.jar`

5. **Variables de entorno** (click en "Add Environment Variable"):
   ```
   DB_URL=jdbc:mysql://[TU_HOST_MYSQL]:3306/agrigo_auth
   DB_USERNAME=[TU_USUARIO_MYSQL]
   DB_PASSWORD=[TU_PASSWORD_MYSQL]
   JWT_SECRET=agrigo-secret-key-2024-super-secure-minimum-256-bits-long
   ```
   
   **IMPORTANTE:** Reemplaza `[TU_HOST_MYSQL]`, `[TU_USUARIO_MYSQL]` y `[TU_PASSWORD_MYSQL]` con los datos que te dio Senode en el Paso 3.2

6. **Click en "Deploy" o "Desplegar"**

7. **Espera 2-3 minutos** hasta que el estado sea "Running" o "Ejecutando"

8. **Prueba que funcione:**
   - Senode te dará una URL como: `https://auth-service-xxxxx.senode.app`
   - Prueba en tu navegador: `https://auth-service-xxxxx.senode.app/api/auth/login`
   - Deberías ver un error de "Method not allowed" (es normal, significa que está funcionando)

---

### 5.2 FARMER SERVICE (Puerto 8082)

Repite el proceso anterior pero con estos datos:

- **Nombre:** `farmer-service`
- **Puerto:** `8082`
- **Dockerfile:** `farmer-service/Dockerfile`
- **JAR:** `farmer-service/target/farmer-service-1.0.0.jar`
- **Variables de entorno:**
  ```
  DB_URL=jdbc:mysql://[TU_HOST_MYSQL]:3306/agrigo_farmer
  DB_USERNAME=[TU_USUARIO_MYSQL]
  DB_PASSWORD=[TU_PASSWORD_MYSQL]
  ```

---

### 5.3 STORE SERVICE (Puerto 8083)

- **Nombre:** `store-service`
- **Puerto:** `8083`
- **Dockerfile:** `store-service/Dockerfile`
- **JAR:** `store-service/target/store-service-1.0.0.jar`
- **Variables de entorno:**
  ```
  DB_URL=jdbc:mysql://[TU_HOST_MYSQL]:3306/agrigo_store
  DB_USERNAME=[TU_USUARIO_MYSQL]
  DB_PASSWORD=[TU_PASSWORD_MYSQL]
  ```

---

### 5.4 PAYMENT SERVICE (Puerto 8084)

- **Nombre:** `payment-service`
- **Puerto:** `8084`
- **Dockerfile:** `payment-service/Dockerfile`
- **JAR:** `payment-service/target/payment-service-1.0.0.jar`
- **Variables de entorno:**
  ```
  DB_URL=jdbc:mysql://[TU_HOST_MYSQL]:3306/agrigo_payment
  DB_USERNAME=[TU_USUARIO_MYSQL]
  DB_PASSWORD=[TU_PASSWORD_MYSQL]
  BANCOLOMBIA_API_KEY=
  NEQUI_API_KEY=
  NEQUI_CLIENT_ID=
  ```
  (Deja vacíos los de Bancolombia y Nequi por ahora)

---

### 5.5 AI RECOMMENDATION SERVICE (Puerto 8085)

- **Nombre:** `ai-recommendation-service`
- **Puerto:** `8085`
- **Dockerfile:** `ai-recommendation-service/Dockerfile`
- **JAR:** `ai-recommendation-service/target/ai-recommendation-service-1.0.0.jar`
- **Variables de entorno:**
  ```
  HUGGINGFACE_API_KEY=[TU_TOKEN_HUGGINGFACE]
  ```
  (Usa el token que obtuviste en el Paso 4)

---

### 5.6 PRODUCT MARKETPLACE SERVICE (Puerto 8086)

- **Nombre:** `product-marketplace-service`
- **Puerto:** `8086`
- **Dockerfile:** `product-marketplace-service/Dockerfile`
- **JAR:** `product-marketplace-service/target/product-marketplace-service-1.0.0.jar`
- **Variables de entorno:**
  ```
  DB_URL=jdbc:mysql://[TU_HOST_MYSQL]:3306/agrigo_marketplace
  DB_USERNAME=[TU_USUARIO_MYSQL]
  DB_PASSWORD=[TU_PASSWORD_MYSQL]
  ```

---

### 5.7 INVENTORY SERVICE (Puerto 8087)

- **Nombre:** `inventory-service`
- **Puerto:** `8087`
- **Dockerfile:** `inventory-service/Dockerfile`
- **JAR:** `inventory-service/target/inventory-service-1.0.0.jar`
- **Variables de entorno:**
  ```
  DB_URL=jdbc:mysql://[TU_HOST_MYSQL]:3306/agrigo_inventory
  DB_USERNAME=[TU_USUARIO_MYSQL]
  DB_PASSWORD=[TU_PASSWORD_MYSQL]
  ```

---

### 5.8 NOTIFICATION SERVICE (Puerto 8088)

- **Nombre:** `notification-service`
- **Puerto:** `8088`
- **Dockerfile:** `notification-service/Dockerfile`
- **JAR:** `notification-service/target/notification-service-1.0.0.jar`
- **Variables de entorno:**
  ```
  DB_URL=jdbc:mysql://[TU_HOST_MYSQL]:3306/agrigo_notification
  DB_USERNAME=[TU_USUARIO_MYSQL]
  DB_PASSWORD=[TU_PASSWORD_MYSQL]
  ```

---

## ✅ PASO 6: Verificar que Todo Funciona

### 6.1 Revisar el estado
En el dashboard de Senode, todos los servicios deben estar en estado "Running" (verde)

### 6.2 Probar los endpoints
Senode te habrá dado URLs para cada servicio. Prueba estos endpoints:

**Auth Service:**
```bash
POST https://auth-service-xxxxx.senode.app/api/auth/login
Body: {"email": "farmer1@agrigo.com", "password": "password"}
```

**Farmer Service:**
```bash
GET https://farmer-service-xxxxx.senode.app/api/farmers
```

**AI Recommendations:**
```bash
POST https://ai-service-xxxxx.senode.app/api/recommendations
Body: {"cropType": "RICE", "soilType": "Clay", "climateZone": "Tropical"}
```

### 6.3 Usar Postman o Thunder Client
1. Descarga Postman (https://postman.com)
2. Importa las URLs de tus servicios
3. Prueba cada endpoint

---

## 📝 PASO 7: Guardar las URLs

Crea un archivo `URLS.txt` con todas tus URLs de Senode:

```
AUTH_SERVICE_URL=https://auth-service-xxxxx.senode.app
FARMER_SERVICE_URL=https://farmer-service-xxxxx.senode.app
STORE_SERVICE_URL=https://store-service-xxxxx.senode.app
PAYMENT_SERVICE_URL=https://payment-service-xxxxx.senode.app
AI_SERVICE_URL=https://ai-service-xxxxx.senode.app
MARKETPLACE_SERVICE_URL=https://marketplace-service-xxxxx.senode.app
INVENTORY_SERVICE_URL=https://inventory-service-xxxxx.senode.app
NOTIFICATION_SERVICE_URL=https://notification-service-xxxxx.senode.app
```

Las necesitarás para conectar el frontend.

---

## 🔧 Solución de Problemas

### Si un servicio no inicia:
1. Ve a Senode → Tu servicio → "Logs"
2. Busca errores en rojo
3. Problemas comunes:
   - **"Connection refused"**: Revisa las credenciales de MySQL
   - **"Port already in use"**: Cambia el puerto
   - **"File not found"**: Verifica que subiste el JAR correcto

### Si la base de datos no conecta:
1. Verifica que el host MySQL sea correcto
2. Asegúrate de que el puerto sea 3306
3. Revisa usuario y contraseña

### Para ver logs en tiempo real:
```bash
# En Senode, cada servicio tiene una sección "Logs"
# Click ahí para ver qué está pasando
```

---

## 🎉 ¡Listo!

Tu backend está desplegado en Senode. Ahora puedes:
1. ✅ Probar todos los endpoints
2. ✅ Conectar el frontend (siguiente paso)
3. ✅ Monitorear en el dashboard de Senode

---

## 📞 Soporte

Si tienes problemas:
- Revisa los logs en Senode
- Verifica las variables de entorno
- Asegúrate de que MySQL esté corriendo
- Contacta soporte de Senode si es necesario
