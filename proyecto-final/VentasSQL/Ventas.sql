USE Ventas;

create table servicio
(
    id          bigint         not null auto_increment
        primary key,
    costo       decimal(19, 2) null,
    descripcion varchar(255)   null,
    nombre      varchar(32)    null
);

INSERT INTO servicio(nombre, costo) VALUES ('Pre-Boda', 1000), ('Boda', 5000), ('Cumpleaños', 3000), ('Vídeo de evento', 4000);