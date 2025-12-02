-- Datos de prueba para tablas evento y compra_entrada

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE compra_entrada;
TRUNCATE TABLE evento;
SET FOREIGN_KEY_CHECKS = 1;

-- Eventos
INSERT INTO evento (id, nombre, descripcion, fecha, lugar, precio_base, recargo_grada, recargo_vip) VALUES
                                                                                                        (1, 'Concierto de Rock: Los Errantes', 'Banda de rock alternativo en gira nacional', '2025-06-15 20:00:00', 'Auditorio Nacional', 30.00, 5.00, 20.00),
                                                                                                        (2, 'Teatro: La Vida Breve', 'Drama contemporáneo, versión renovada', '2025-05-10 19:30:00', 'Teatro Central', 20.00, 3.00, 12.00),
                                                                                                        (3, 'Festival Gastronómico Ciudad', 'Productos locales y showcookings', '2025-08-02 11:00:00', 'Parque del Río', 15.00, 0.00, 0.00);

-- Compras de entradas
INSERT INTO compra_entrada
(id, evento_id, nombre_comprador, email_comprador, zona, numero_entrada, precio_unitario, precio_total, fecha_compra) VALUES
                                                                                                                          (1, 1, 'María López', 'maria.lopez@example.com', 'GENERAL', 2, 30.00, 60.00, '2025-05-02 14:23:00'),
                                                                                                                          (2, 1, 'Javier Ruiz', 'j.ruiz@gmail.com', 'VIP', 4, 50.00, 200.00, '2025-06-01 09:10:00'),
                                                                                                                          (3, 2, 'Ana García', 'ana.garcia@yahoo.com', 'GRADA', 1, 23.00, 23.00, '2025-04-29 11:45:00'),
                                                                                                                          (4, 2, 'Teatro Lovers SL', 'ventas@teatrolovers.es', 'VIP', 10, 32.00, 320.00, '2025-05-01 10:00:00'),
                                                                                                                          (5, 3, 'Mercado Team', 'info@mercadoteam.es', 'GENERAL', 5, 15.00, 75.00, '2025-07-20 08:00:00'),
                                                                                                                          (6, 3, 'Carlos Fernández', 'carlosf@mail.com', 'GENERAL', 2, 15.00, 30.00, '2025-07-01 12:00:00'),
                                                                                                                          (7, 2, 'Laura Martínez', 'laura.m@example.com', 'VIP', 1, 150.00, 150.00, '2025-08-15 16:30:00'),
                                                                                                                          (8, 2, 'API Tickets SL', 'tickets@apitickets.com', 'GENERAL', 3, 100.00, 300.00, '2025-09-01 09:00:00'),
                                                                                                                          (9, 3, 'Pablo Gómez', 'p.gomez@gmail.com', 'GENERAL', 4, 5.00, 20.00, '2025-06-25 18:20:00'),
                                                                                                                          (10, 1, 'Lucía Torres', 'lucia.torres@hotmail.com', 'VIP', 2, 48.00, 96.00, '2025-06-10 13:05:00');
