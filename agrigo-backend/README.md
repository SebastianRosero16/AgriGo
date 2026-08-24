# Agrigo Backend - Microservices Architecture

Sistema completo de microservicios para plataforma agrícola con Spring Boot, MySQL y Docker.

## Arquitectura

- **Auth Service** (8081): Autenticación JWT
- **Farmer Service** (8082): Gestión de agricultores y productos
- **Store Service** (8083): Gestión de agrotiendas e insumos
- **Payment Service** (8084): Pagos con Bancolombia y Nequi (Adapter Pattern)
- **AI Recommendation Service** (8085): Recomendaciones IA (Strategy Pattern)
- **Product Marketplace Service** (8086): Marketplace de productos
- **Inventory Service** (8087): Control de inventario (Singleton Pattern)
- **Notification Service** (8088): Sistema de notificaciones

## Patrones de Diseño

- **Singleton**: Gestión de inventario
- **Factory**: Creación de estrategias de recomendación y gateways de pago
- **Adapter**: Integración con Bancolombia y Nequi
- **Strategy**: Recomendaciones por tipo de cultivo

## Requisitos

- Java 17
- Maven 3.8+
- Docker & Docker Compose
- MySQL 8.0

## Configuración

1. Copiar `.env.example` a `.env` y configurar variables:
```bash
cp .env.example .env
```

2. Editar `.env` con tus credenciales reales de APIs

## Build

### Windows:
```bash
build-all.bat
```

### Linux/Mac:
```bash
chmod +x build-all.sh
./build-all.sh
```

## Ejecución con Docker

```bash
docker-compose up -d
```

## Ejecución Local (sin Docker)

1. Iniciar MySQL y crear bases de datos
2. Ejecutar cada servicio:
```bash
cd auth-service && mvn spring-boot:run
cd farmer-service && mvn spring-boot:run
# ... etc
```

## Endpoints Principales

### Auth Service (8081)
- POST `/api/auth/register` - Registro
- POST `/api/auth/login` - Login
- GET `/api/auth/validate` - Validar token

### Farmer Service (8082)
- POST `/api/farmers` - Crear agricultor
- GET `/api/farmers/{id}` - Obtener agricultor
- POST `/api/farmers/{id}/products` - Publicar producto

### Store Service (8083)
- POST `/api/stores` - Crear tienda
- GET `/api/stores/products/cheapest?productName=X` - Producto más barato
- POST `/api/stores/{id}/products` - Agregar producto

### Payment Service (8084)
- POST `/api/payments/process` - Procesar pago (Bancolombia/Nequi)
- GET `/api/payments/user/{userId}` - Historial de pagos

### AI Recommendation Service (8085)
- POST `/api/recommendations` - Obtener recomendaciones IA

### Product Marketplace Service (8086)
- GET `/api/marketplace/products` - Listar productos
- POST `/api/marketplace/orders` - Crear orden

### Inventory Service (8087)
- GET `/api/inventory/low-stock` - Items con bajo stock
- POST `/api/inventory/reduce` - Reducir cantidad

### Notification Service (8088)
- GET `/api/notifications/user/{userId}` - Notificaciones de usuario
- POST `/api/notifications` - Crear notificación

## Deployment en Senode

1. Crear proyecto en Senode
2. Configurar variables de entorno
3. Subir docker-compose.yml
4. Deploy automático

## Usuarios de Prueba

- **Agricultor**: farmer1@agrigo.com / password
- **Tienda**: store1@agrigo.com / password
- **Admin**: admin@agrigo.com / password

## Tecnologías

- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security + JWT
- MySQL 8.0
- Docker
- OpenFeign
- Lombok
- Hugging Face API (IA gratuita)
