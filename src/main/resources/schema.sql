
CREATE DATABASE entradas_eventos;

USE entradas_eventos;

-- Crear tabla evento
CREATE TABLE IF NOT EXISTS evento (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      nombre VARCHAR(255) NOT NULL,
                                      descripcion TEXT,
                                      fecha_hora DATETIME NOT NULL,
                                      lugar VARCHAR(255) NOT NULL,
                                      precio_base DECIMAL(10, 2) NOT NULL,
                                      recargo_grada DECIMAL(10, 2),
                                      recargo_vip DECIMAL(10, 2)
);

-- Crear tabla compra_entrada
CREATE TABLE IF NOT EXISTS compra_entrada (
                                              id INT AUTO_INCREMENT PRIMARY KEY,
                                              evento_id INT NOT NULL,
                                              nombre_comprador VARCHAR(255) NOT NULL,
                                              email_comprador VARCHAR(255) NOT NULL,
                                              zona VARCHAR(50) NOT NULL,
                                              numero_entradas INT NOT NULL,
                                              precio_unitario DECIMAL(10, 2) NOT NULL,
                                              precio_total DECIMAL(10, 2) NOT NULL,
                                              fecha_compra DATETIME NOT NULL,
                                              FOREIGN KEY (evento_id) REFERENCES evento(id)
);

-- INSERT INTO evento
INSERT INTO evento (nombre, descripcion, fecha_hora, lugar, precio_base, recargo_grada, recargo_vip) VALUES
                                                                                                         ('Concierto Rock 2024', 'Gran concierto de rock con bandas nacionales', '2024-06-15 20:00:00', 'Estadio Central', 50.00, 15.00, 30.00),
                                                                                                         ('Festival Jazz', 'Festival de jazz internacional', '2024-07-20 19:00:00', 'Teatro Principal', 45.00, 12.00, 25.00),
                                                                                                         ('Conferencia Tech', 'Conferencia de tecnología e innovación', '2024-08-10 09:00:00', 'Centro de Convenciones', 30.00, 8.00, 15.00),
                                                                                                         ('Obra de Teatro', 'Comedia musical en vivo', '2024-09-05 18:30:00', 'Teatro Municipal', 40.00, 10.00, 20.00);

-- INSERT INTO compra_entrada
INSERT INTO compra_entrada (evento_id, nombre_comprador, email_comprador, zona, numero_entradas, precio_unitario, precio_total, fecha_compra) VALUES
                                                                                                                                                 (1, 'Juan García', 'juan@email.com', 'Grada', 2, 65.00, 130.00, '2024-06-01 10:30:00'),
                                                                                                                                                 (1, 'María López', 'maria@email.com', 'VIP', 1, 80.00, 80.00, '2024-06-02 14:15:00'),
                                                                                                                                                 (2, 'Carlos Ruiz', 'carlos@email.com', 'Grada', 3, 57.00, 171.00, '2024-07-10 11:00:00'),
                                                                                                                                                 (3, 'Ana Martínez', 'ana@email.com', 'Normal', 4, 30.00, 120.00, '2024-08-01 09:45:00'),
                                                                                                                                                 (4, 'Pedro Sánchez', 'pedro@email.com', 'VIP', 2, 60.00, 120.00, '2024-09-01 16:20:00');