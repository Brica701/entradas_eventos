CREATE DATABASE IF NOT EXISTS entradas_eventos;
USE entradas_eventos;

CREATE TABLE IF NOT EXISTS evento (
                                      id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      nombre        varchar(100)   null,
                                      descripcion   varchar(255)   null,
                                      fecha_hora    datetime       null,
                                      lugar         varchar(100)   null,
                                      precio_base   decimal(10, 2) null,
                                      recargo_grada decimal(10, 2) null,
                                      recargo_vip   decimal(10, 2) null
);

CREATE TABLE IF NOT EXISTS compra_entrada (
                                              id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                              evento_id        int            not null,
                                              nombre_comprador varchar(100)   null,
                                              email_comprador  varchar(100)   null,
                                              numero_entradas  int            null,
                                              precio_unitario  decimal(10, 2) null,
                                              precio_total     decimal(10, 2) null,
                                              fecha_compra     datetime       null,
                                              constraint compra_entrada_evento_id_fk
                                                  foreign key (evento_id) references evento (id)
);
