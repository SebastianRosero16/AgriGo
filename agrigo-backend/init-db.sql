-- ============================================
-- SCRIPT DE INICIALIZACIÓN - AGRIGO BACKEND
-- Crea todas las tablas para los microservicios
-- ============================================

-- ============================================
-- AUTH SERVICE - Tabla de usuarios
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- FARMER SERVICE - Tablas de agricultores
-- ============================================
CREATE TABLE IF NOT EXISTS farmers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    farm_size VARCHAR(100),
    crop_types VARCHAR(255),
    soil_type VARCHAR(100),
    climate_zone VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS farmer_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    farmer_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    unit VARCHAR(50),
    category VARCHAR(100),
    image_url VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- STORE SERVICE - Tablas de tiendas
-- ============================================
CREATE TABLE IF NOT EXISTS stores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    store_name VARCHAR(255) NOT NULL,
    description TEXT,
    location VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    address VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS store_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    category VARCHAR(100) NOT NULL,
    brand VARCHAR(100),
    image_url VARCHAR(500),
    product_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- PAYMENT SERVICE - Tabla de pagos
-- ============================================
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    gateway VARCHAR(50) NOT NULL,
    reference VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    transaction_id VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- MARKETPLACE SERVICE - Tablas de marketplace
-- ============================================
CREATE TABLE IF NOT EXISTS marketplace_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    seller_type VARCHAR(50) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    unit VARCHAR(50),
    category VARCHAR(100),
    image_url VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    delivery_address VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- INVENTORY SERVICE - Tabla de inventario
-- ============================================
CREATE TABLE IF NOT EXISTS inventory_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    product_type VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    min_threshold INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- NOTIFICATION SERVICE - Tabla de notificaciones
-- ============================================
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- DATOS DE PRUEBA
-- ============================================

-- Usuarios de prueba (password: "password" para todos)
INSERT INTO users (email, password, name, role, phone, created_at) VALUES
('farmer1@agrigo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Juan Pérez', 'FARMER', '3001234567', NOW()),
('store1@agrigo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Agro Store', 'STORE', '3007654321', NOW()),
('admin@agrigo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Admin', 'ADMIN', '3009876543', NOW());

-- Agricultor de prueba
INSERT INTO farmers (user_id, name, location, farm_size, crop_types, soil_type, climate_zone, created_at) VALUES
(1, 'Juan Pérez', 'Valle del Cauca', '5 hectares', 'Rice, Corn', 'Clay', 'Tropical', NOW());

-- Productos del agricultor
INSERT INTO farmer_products (farmer_id, product_name, description, price, quantity, unit, category, status, created_at) VALUES
(1, 'Arroz Premium', 'Arroz de alta calidad', 2500.00, 1000, 'kg', 'Grains', 'AVAILABLE', NOW()),
(1, 'Maíz Amarillo', 'Maíz fresco', 1800.00, 500, 'kg', 'Grains', 'AVAILABLE', NOW());

-- Tienda de prueba
INSERT INTO stores (user_id, store_name, description, location, phone, email, address, created_at) VALUES
(2, 'Agro Store Central', 'Insumos agrícolas de calidad', 'Bogotá', '3007654321', 'store1@agrigo.com', 'Calle 100 #15-20', NOW());

-- Productos de la tienda
INSERT INTO store_products (store_id, product_name, description, price, stock, category, brand, product_type, created_at) VALUES
(1, 'Fertilizante NPK 15-15-15', 'Fertilizante completo', 45000.00, 200, 'Fertilizers', 'AgroMax', 'FERTILIZER', NOW()),
(1, 'Pesticida Orgánico', 'Control de plagas natural', 35000.00, 150, 'Pesticides', 'EcoAgro', 'PESTICIDE', NOW()),
(1, 'Herbicida Selectivo', 'Control de malezas', 28000.00, 100, 'Herbicides', 'AgroMax', 'HERBICIDE', NOW());
