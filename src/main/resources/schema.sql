-- =========================================
--  DATA.SQL LISTO PARA SPRING BOOT
-- =========================================

-- Eliminar tablas si existen
DROP TABLE IF EXISTS compra_entrada;
DROP TABLE IF EXISTS evento;

-- Crear tabla evento
CREATE TABLE evento (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NULL,
                        descripcion VARCHAR(255) NULL,
                        fecha_hora DATETIME NULL,
                        lugar VARCHAR(100) NULL,
                        precio_base DECIMAL(10,2) NULL,
                        recargo_grada DECIMAL(10,2) NULL,
                        recargo_vip DECIMAL(10,2) NULL
);

-- Crear tabla compra_entrada
CREATE TABLE compra_entrada (
                                id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                evento_id INT NOT NULL,
                                nombre_comprador VARCHAR(100) NULL,
                                email_comprador VARCHAR(100) NULL,
                                zona VARCHAR(20) NOT NULL, -- GENERAL, GRADA, VIP
                                numero_entradas INT NULL,
                                precio_unitario DECIMAL(10,2) NULL,
                                precio_total DECIMAL(10,2) NULL,
                                fecha_compra DATETIME NULL,
                                CONSTRAINT compra_entrada_evento_id_fk FOREIGN KEY (evento_id) REFERENCES evento (id)
);

-- =========================================
-- INSERTAR EVENTOS DE EJEMPLO
-- =========================================
INSERT INTO evento (nombre, descripcion, fecha_hora, lugar, precio_base, recargo_grada, recargo_vip)
VALUES
    ('Concierto de Rock - Los Relámpagos', 'Gira nacional de la banda Los Relámpagos', '2025-05-12 21:00:00', 'Auditorio Nacional', 35.00, 10.00, 25.00),
    ('Orquesta Sinfónica de Madrid', 'Concierto especial de primavera', '2025-06-20 19:00:00', 'Teatro Real', 50.00, 12.00, 30.00),
    ('Festival de Electrónica', 'Evento con DJs internacionales', '2025-07-05 22:30:00', 'IFEMA Madrid', 45.00, 8.00, 20.00),
    ('Noche de Flamenco', 'Espectáculo con artistas invitados', '2025-05-30 20:00:00', 'Plaza de Toros de Málaga', 30.00, 7.00, 18.00),
    ('Pop Summer Fest', 'Concierto de artistas pop nacionales', '2025-08-12 20:30:00', 'Estadio La Cartuja', 40.00, 10.00, 22.00);

-- =========================================
-- INSERTAR COMPRAS DE EJEMPLO
-- =========================================
INSERT INTO compra_entrada (evento_id, nombre_comprador, email_comprador, zona, numero_entradas, precio_unitario, precio_total, fecha_compra)
VALUES
    (1, 'Carlos Pérez', 'carlos.perez@email.com', 'GENERAL', 2, 35.00, 70.00, NOW()),
    (1, 'María López', 'maria.lopez@email.com', 'VIP', 1, 60.00, 60.00, NOW()),
    (2, 'Laura Sánchez', 'laura.sanchez@email.com', 'GENERAL', 3, 50.00, 150.00, NOW()),
    (3, 'Javier Torres', 'javier.torres@email.com', 'GRADA', 2, 53.00, 106.00, NOW()),
    (3, 'Sofía Morales', 'sofia.morales@email.com', 'GENERAL', 1, 45.00, 45.00, NOW()),
    (4, 'Antonio Gómez', 'antonio.gomez@email.com', 'GENERAL', 4, 30.00, 120.00, NOW()),
    (5, 'Isabel Ruiz', 'isabel.ruiz@email.com', 'VIP', 2, 62.00, 124.00, NOW());
