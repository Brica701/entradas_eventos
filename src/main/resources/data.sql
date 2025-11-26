USE entradas_eventos;

-- =========================================
-- LIMPIEZA DE DATOS (sin avisos ni errores)
-- =========================================
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE compra_entrada;
TRUNCATE TABLE evento;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================================
--              EVENTOS
-- =========================================
INSERT INTO evento (nombre, descripcion, fecha_hora, lugar, precio_base, recargo_grada, recargo_vip)
VALUES
    ('Concierto de Rock - Los Relámpagos', 'Gira nacional de la banda Los Relámpagos', '2025-05-12 21:00:00', 'Auditorio Nacional', 35.00, 10.00, 25.00),
    ('Orquesta Sinfónica de Madrid', 'Concierto especial de primavera', '2025-06-20 19:00:00', 'Teatro Real', 50.00, 12.00, 30.00),
    ('Festival de Electrónica', 'Evento con DJs internacionales', '2025-07-05 22:30:00', 'IFEMA Madrid', 45.00, 8.00, 20.00),
    ('Noche de Flamenco', 'Espectáculo con artistas invitados', '2025-05-30 20:00:00', 'Plaza de Toros de Málaga', 30.00, 7.00, 18.00),
    ('Pop Summer Fest', 'Concierto de artistas pop nacionales', '2025-08-12 20:30:00', 'Estadio La Cartuja', 40.00, 10.00, 22.00);

-- =========================================
--          COMPRAS DE ENTRADAS
-- =========================================
INSERT INTO compra_entrada (evento_id, nombre_comprador, email_comprador, numero_entradas, precio_unitario, precio_total, fecha_compra)
VALUES
    (1, 'Carlos Pérez', 'carlos.perez@email.com', 2, 35.00, 70.00, NOW()),
    (1, 'María López', 'maria.lopez@email.com', 1, 60.00, 60.00, NOW()),   -- VIP
    (2, 'Laura Sánchez', 'laura.sanchez@email.com', 3, 50.00, 150.00, NOW()),
    (3, 'Javier Torres', 'javier.torres@email.com', 2, 65.00, 130.00, NOW()),
    (3, 'Sofía Morales', 'sofia.morales@email.com', 1, 45.00, 45.00, NOW()),
    (4, 'Antonio Gómez', 'antonio.gomez@email.com', 4, 30.00, 120.00, NOW()),
    (5, 'Isabel Ruiz', 'isabel.ruiz@email.com', 2, 62.00, 124.00, NOW());
