
USE entradas_eventos;
create table evento
(
    id            int auto_increment
        primary key,
    nombre        varchar(100)   not null,
    descripcion   varchar(255)   null,
    fecha_hora         datetime       null,
    lugar         varchar(100)   null,
    precio_base   decimal(10, 2) null,
    recargo_grada decimal(10, 2) null,
    recargo_vip   decimal(10, 2) null
);

create table compra_entrada
(
    id               int auto_increment
        primary key,
    evento_id        int            not null,
    nombre_comprador varchar(100)   null,
    email_comprador  varchar(100)   null,
    zona             VARCHAR(20)    NULL,  -- NUEVA COLUMNA
    numero_entradas   int            null,
    precio_unitario  decimal(10, 2) null,
    precio_total     decimal(10, 2) null,
    fecha_compra     datetime       null,
    constraint compra_entrada_evento_id_fk
        foreign key (evento_id) references evento (id)
);



