# 🎨 DEPLOYMENT EN RENDER - LA FORMA MÁS FÁCIL

## ✅ Ventajas de Render
- 100% GRATIS (sin tarjeta)
- PostgreSQL incluido gratis
- Deploy automático desde GitHub
- SSL gratis
- Muy estable

---

## 🚀 PASO 1: CREAR CUENTA (2 minutos)

1. Ve a: **https://render.com**
2. Click en **"Get Started"**
3. Selecciona **"Sign up with GitHub"**
4. Autoriza Render

---

## 🗄️ PASO 2: CREAR BASE DE DATOS (2 minutos)

1. En Render, click en **"New +"** (arriba a la derecha)
2. Selecciona **"PostgreSQL"**
3. Llena:
   - **Name:** `agrigo-db`
   - **Database:** `agrigo`
   - **User:** `agrigo`
   - **Region:** Oregon (US West)
   - **Plan:** **Free** ✅
4. Click en **"Create Database"**
5. Espera 1 minuto
6. **COPIA** la **"Internal Database URL"** (la necesitarás)

---

## 🚢 PASO 3: DESPLEGAR SERVICIOS (15 minutos)

### 3.1 AUTH SERVICE

1. Click en **"New +" → "Web Service"**
2. Conecta tu repositorio **"agrigo-backend"**
3. Configuración:
   - **Name:** `agrigo-auth`
   - **Region:** Oregon (US West)
   - **Branch:** `main`
   - **Root Directory:** `auth-service`
   - **Runtime:** `Java`
   - **Build Command:**
     ```
     mvn clean package -DskipTests
     ```
   - **Start Command:**
     ```
     java -jar target/auth-service-1.0.0.jar
     ```
   - **Plan:** **Free** ✅

4. **Environment Variables** (click en "Advanced"):
   ```
   DATABASE_URL = [pega la Internal Database URL]
   JWT_SECRET = agrigo-secret-key-2024-super-secure-minimum-256-bits-long
   PORT = 8081
   ```

5. Click en **"Create Web Service"**
6. Espera 5-10 minutos (el primer deploy es lento)
7. Cuando termine, copia la URL: `https://agrigo-auth.onrender.com`

---

### 3.2 FARMER SERVICE

Repite el proceso:
- **Name:** `agrigo-farmer`
- **Root Directory:** `farmer-service`
- **Start Command:** `java -jar target/farmer-service-1.0.0.jar`
- **Variables:**
  ```
  DATABASE_URL = [misma URL de la BD]
  PORT = 8082
  ```

---

### 3.3 STORE SERVICE

- **Name:** `agrigo-store`
- **Root Directory:** `store-service`
- **Start Command:** `java -jar target/store-service-1.0.0.jar`
- **Variables:**
  ```
  DATABASE_URL = [misma URL de la BD]
  PORT = 8083
  ```

---

### 3.4 PAYMENT SERVICE

- **Name:** `agrigo-payment`
- **Root Directory:** `payment-service`
- **Start Command:** `java -jar target/payment-service-1.0.0.jar`
- **Variables:**
  ```
  DATABASE_URL = [misma URL de la BD]
  BANCOLOMBIA_API_KEY = 
  NEQUI_API_KEY = 
  NEQUI_CLIENT_ID = 
  PORT = 8084
  ```

---

### 3.5 AI RECOMMENDATION SERVICE

- **Name:** `agrigo-ai`
- **Root Directory:** `ai-recommendation-service`
- **Start Command:** `java -jar target/ai-recommendation-service-1.0.0.jar`
- **Variables:**
  ```
  HUGGINGFACE_API_KEY = 
  PORT = 8085
  ```

---

### 3.6 PRODUCT MARKETPLACE SERVICE

- **Name:** `agrigo-marketplace`
- **Root Directory:** `product-marketplace-service`
- **Start Command:** `java -jar target/product-marketplace-service-1.0.0.jar`
- **Variables:**
  ```
  DATABASE_URL = [misma URL de la BD]
  PORT = 8086
  ```

---

### 3.7 INVENTORY SERVICE

- **Name:** `agrigo-inventory`
- **Root Directory:** `inventory-service`
- **Start Command:** `java -jar target/inventory-service-1.0.0.jar`
- **Variables:**
  ```
  DATABASE_URL = [misma URL de la BD]
  PORT = 8087
  ```

---

### 3.8 NOTIFICATION SERVICE

- **Name:** `agrigo-notification`
- **Root Directory:** `notification-service`
- **Start Command:** `java -jar target/notification-service-1.0.0.jar`
- **Variables:**
  ```
  DATABASE_URL = [misma URL de la BD]
  PORT = 8088
  ```

---

## ✅ PASO 4: VERIFICAR

1. Todos los servicios deben estar en estado **"Live"** (verde)
2. Prueba los endpoints:

```bash
# Login
curl -X POST https://agrigo-auth.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"farmer1@agrigo.com","password":"password"}'

# Listar agricultores
curl https://agrigo-farmer.onrender.com/api/farmers
```

---

## 📝 URLS DE PRODUCCIÓN

```
AUTH: https://agrigo-auth.onrender.com
FARMER: https://agrigo-farmer.onrender.com
STORE: https://agrigo-store.onrender.com
PAYMENT: https://agrigo-payment.onrender.com
AI: https://agrigo-ai.onrender.com
MARKETPLACE: https://agrigo-marketplace.onrender.com
INVENTORY: https://agrigo-inventory.onrender.com
NOTIFICATION: https://agrigo-notification.onrender.com
```

---

## ⚠️ IMPORTANTE

- El plan gratuito **duerme después de 15 minutos** de inactividad
- El primer request después de dormir tarda ~30 segundos
- Para mantenerlo activo 24/7, necesitas el plan de pago ($7/mes por servicio)

---

## 🎉 ¡LISTO!

Tu backend está en producción con Render.
