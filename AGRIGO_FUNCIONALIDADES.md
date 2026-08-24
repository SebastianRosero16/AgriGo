# AgriGoSJ — Funcionalidades Completas

## ¿Qué es AgriGoSJ?

AgriGoSJ es una plataforma agrícola integral que conecta agricultores, agrotiendas y compradores. Permite gestionar cultivos, publicar y comprar productos agrícolas, comparar precios de insumos y obtener recomendaciones personalizadas mediante inteligencia artificial.

---

## Arquitectura

### Backend (Spring Boot — Microservicios)

| Servicio | Puerto | Responsabilidad |
|---|---|---|
| auth-service | 8081 | Autenticación, registro y verificación por email |
| farmer-service | 8082 | Gestión de cultivos y productos del agricultor |
| store-service | 8083 | Gestión de insumos de agrotiendas |
| ai-recommendation-service | 8085 | Motor de recomendaciones agrícolas con IA |
| product-marketplace-service | 8086 | Marketplace de productos y órdenes |

### Frontend (React + TypeScript)
- Vite + TailwindCSS
- Proxy configurado para enrutar cada ruta al microservicio correspondiente
- Zustand para manejo de estado global (carrito, autenticación)

---

## Tipos de Usuario

### 1. Agricultor (`FARMER`)
Accede a `/farmer/dashboard`

### 2. Agrotienda (`STORE`)
Accede a `/store/dashboard`

### 3. Comprador (`BUYER`)
Accede a `/buyer/dashboard`

### 4. Administrador (`ADMIN`)
Accede a `/admin/dashboard`

---

## Funcionalidades por Módulo

---

### Autenticación (`/auth`)

#### Registro de Usuario
- Formulario con: nombre completo, usuario, correo electrónico, contraseña y tipo de cuenta
- Validación en tiempo real de todos los campos
- Verificación de disponibilidad de usuario y correo antes de proceder
- **Verificación por email real:** al registrarse se envía un código de 6 dígitos al correo del usuario via Gmail SMTP
- El código expira en 10 minutos
- Sin código verificado no se completa el registro
- Redirección automática al dashboard según el tipo de cuenta elegido

#### Inicio de Sesión
- Login por nombre de usuario o correo electrónico
- JWT Token almacenado en localStorage
- Redirección automática al dashboard del rol correspondiente
- Mensaje de error descriptivo para credenciales incorrectas

#### Recuperación de Contraseña
- Flujo de recuperación por correo electrónico

#### Seguridad
- Tokens JWT con expiración de 24 horas
- Prevención de navegación hacia atrás después del login
- CORS habilitado para comunicación frontend-backend

---

### Panel del Agricultor (`/farmer`)

#### Dashboard Principal
- Contador de cultivos activos registrados
- Contador de productos publicados en el marketplace
- Acciones rápidas: agregar cultivo, publicar producto, pedir recomendación IA, ver marketplace, asistente de compras

#### Gestión de Cultivos (`/farmer/crops`)
- **Crear cultivo** con los campos:
  - Nombre del cultivo
  - Tipo: Cereal, Hortaliza, Frutal, Leguminosa, Otro
  - Fecha de siembra
  - Área en hectáreas
  - Ubicación (parcela)
  - Estado actual: Plántula, Crecimiento Vegetativo, Floreciendo, Fructificando, Listo para Cosechar
  - Clima: Tropical, Subtropical, Templado, Frío, Árido, Semiárido, Húmedo
  - Notas adicionales
- **Editar cultivo** existente con todos sus campos
- **Eliminar cultivo** con confirmación modal
- Visualización en tarjetas con badge de estado con color diferenciado
- Implementado con estructura de datos LinkedList

#### Mis Productos (`/farmer/products`)
- **Publicar producto** en el marketplace con:
  - Nombre, descripción, categoría, precio en COP, unidad de medida, stock disponible
  - Subida de imagen desde el dispositivo (Base64, máx 2MB) o URL
  - Validaciones estrictas: nombre mín 3 chars, descripción mín 10 chars, categoría solo letras, precio positivo
- **Editar** y **eliminar** productos publicados
- Historial de acciones implementado con estructura de datos Stack
- Indicador de stock: verde (>10), amarillo (1-10), rojo (agotado)

#### Recomendaciones IA (`/farmer/ai`)
- Selección del cultivo sobre el que se quiere consultar
- Chat interactivo en español con contexto del cultivo seleccionado
- El asistente responde preguntas específicas sobre:
  - **Riego:** frecuencia, cantidad, ajustes por clima y etapa
  - **Fertilización:** plan completo por tipo de cultivo y etapa
  - **Plagas y enfermedades:** diagnóstico y tratamientos recomendados
  - **Cosecha:** ciclos, indicadores de madurez, estimación según etapa actual
  - **Suelo:** pH, preparación, materia orgánica
  - **Clima:** adaptaciones y cuidados según condiciones
  - **Siembra:** densidades, profundidades, distancias
  - **Etapa actual:** cuidados específicos para cada fase del cultivo
- Preguntas fuera del contexto agrícola son rechazadas con mensaje claro
- Sistema anti-spam de 3 segundos entre mensajes
- Motor de IA basado en reglas agrícolas para Colombia (maíz, tomate, papa, café, arroz y más)

#### Mis Órdenes (`/farmer/orders`)
- Historial de órdenes realizadas
- Ver detalle completo de cada orden
- Cancelar órdenes

---

### Panel de la Agrotienda (`/store`)

#### Dashboard Principal
- Estadísticas en tiempo real:
  - Total de insumos registrados
  - Insumos con stock bajo (menos de 10 unidades)
  - Insumos sin stock
  - Valor total del inventario
- Tabla de insumos recientes con estado visual
- Acciones rápidas de navegación

#### Gestión de Insumos (`/store/inputs`)
- **Crear insumo** con:
  - Nombre, tipo (Fertilizante, Pesticida, Semilla, Herbicida, Fungicida, Herramienta, Otro)
  - Descripción, precio, stock, unidad de medida
- **Editar insumo** completo
- **Actualizar stock** directamente con modal dedicado sin editar los demás campos
- **Eliminar insumo** con confirmación
- Búsqueda por nombre o tipo en tiempo real
- Filtros: Todos, Stock Bajo, Sin Stock
- Badge de estado con colores: verde (disponible), amarillo (stock bajo), rojo (sin stock)
- Los insumos publicados aparecen automáticamente en el Marketplace y Comparador de Precios

---

### Panel del Comprador (`/buyer`)

#### Dashboard Principal
- Accesos directos a Marketplace, Asistente IA y Mis Órdenes
- Carrito de compras con contador de items en el header
- Opción de cerrar sesión

#### Marketplace del Comprador (`/buyer/marketplace`)
- Listado de todos los productos publicados por agricultores
- Búsqueda de productos por nombre
- Filtro por categoría
- Por cada producto: nombre, vendedor, descripción, precio, unidad
- **Agregar al carrito** con notificación de confirmación
- **Comprar ahora** con modal de checkout directo
- Carrito lateral (drawer) con gestión de cantidades y checkout grupal

#### Checkout y Pagos
- **Compra individual** o **carrito completo**
- Formulario de datos de envío: dirección, ciudad, estado, código postal, teléfono, notas
- Integración con **Stripe** para pagos con tarjeta
- Soporte para múltiples métodos de pago: tarjeta, Nequi, efectivo
- Confirmación de orden con número único

#### Mis Órdenes (`/buyer/orders`)
- Historial completo de compras
- Estado de cada orden con badge de color:
  - Amarillo: Pendiente de pago
  - Azul: Pagado / En proceso
  - Verde: Enviado / Entregado
  - Rojo: Cancelado / Reembolsado
- Ver detalle completo: productos, cantidades, precios, dirección
- Eliminar/cancelar órdenes con confirmación modal

---

### Panel del Administrador (`/admin`)

#### Dashboard Principal
- Contadores de usuarios totales, agricultores, agrotiendas y compradores
- Gestión de usuarios y roles
- Vista de reportes del sistema

---

### Páginas Públicas (sin login)

#### Marketplace Público (`/marketplace`)
- Accesible sin cuenta
- Muestra productos agrícolas (compradores/público) o insumos (si es agricultor autenticado)
- Búsqueda y filtros por categoría
- Agregar al carrito y checkout (requiere login para comprar)

#### Comparador de Precios (`/price-comparator`)
- Muestra todos los insumos disponibles de todas las tiendas
- Búsqueda por nombre de insumo
- Agrupa los mismos productos de diferentes tiendas
- Indica el **mejor precio** con badge verde
- Calcula el **ahorro máximo** entre tiendas para el mismo producto
- Permite comprar directamente desde la comparación

#### Asistente de Compras IA (`/shopping-assistant`)
- Chat con IA para buscar productos con lenguaje natural
- Solo acepta consultas relacionadas con compras agrícolas
- Muestra productos sugeridos con precio y botón de compra directa
- Enlace al marketplace si no hay sugerencias específicas

---

## Estructuras de Datos Implementadas

| Estructura | Uso |
|---|---|
| LinkedList | Lista de cultivos del agricultor |
| Stack | Historial de acciones en productos |
| Queue | Cola de peticiones al servicio IA (anti-spam) |

---

## Flujo de Registro Completo

```
1. Usuario completa formulario de registro
2. Sistema verifica disponibilidad de usuario y correo
3. Sistema valida formato del email
4. Se envía código de 6 dígitos al correo del usuario (Gmail SMTP)
5. Usuario ingresa el código en la pantalla de verificación
6. Sistema valida el código (expira en 10 min)
7. Se crea la cuenta y se genera JWT
8. Usuario es redirigido a su dashboard según su rol
```

---

## Cómo Correr el Proyecto

### Backend (todos los servicios)
```cmd
cd agrigo-backend
start-all.bat
```

O individualmente:
```cmd
cd agrigo-backend/auth-service && mvn spring-boot:run        # puerto 8081
cd agrigo-backend/farmer-service && mvn spring-boot:run      # puerto 8082
cd agrigo-backend/store-service && mvn spring-boot:run       # puerto 8083
cd agrigo-backend/ai-recommendation-service && mvn spring-boot:run  # puerto 8085
cd agrigo-backend/product-marketplace-service && mvn spring-boot:run # puerto 8086
```

### Frontend
```cmd
cd AgriGoSJ-Frontend
npm run dev
```

Abrir: `http://localhost:3000`

---

## Tecnologías Utilizadas

### Frontend
- React 18, TypeScript, Vite
- TailwindCSS, Framer Motion
- React Router DOM v6
- Axios (HTTP client con interceptores y cola de peticiones)
- Zustand (estado global)
- React Toastify (notificaciones)
- Stripe.js (pagos)
- Recharts (gráficas)

### Backend
- Java 17, Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA
- H2 Database (en memoria para desarrollo)
- Spring Mail + Gmail SMTP (verificación de email)
- Maven (build tool)
