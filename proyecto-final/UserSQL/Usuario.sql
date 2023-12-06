USE Usuarios;

create table if not exists rol
(
	id bigint not null
		primary key,
	nombre varchar(255) null
);

create table if not exists usuario
(
	id bigint not null
		primary key,
	apellido varchar(255) null,
	email varchar(255) null,
	nombre varchar(255) null,
	password varchar(255) null,
	username varchar(255) null
);

create table if not exists usuario_roles
(
	usuario_id bigint not null,
	roles_id bigint not null,
	constraint FK861wr18rjyh6pmadvor1u36vb
		foreign key (roles_id) references rol (id),
	constraint FKqblnumndby0ftm4c7sg6uso6p
		foreign key (usuario_id) references usuario (id)
);

INSERT INTO rol(id, nombre) VALUES (1,'ROL_USUARIO'),(2,'ROL_CLIENTE'),(3,'ROL_EMPLEADO'), (4,'ROL_ADMIN'),(5,'ROL_SUPER_ADMIN'),(6,'ROL_ROBOT');
INSERT INTO usuario (id, apellido, email, nombre, password, username) VALUES (7, 'admin', 'admin', 'admin', '$2a$10$rhvunyP27lCJOSM5bZMR0uFDeOMLRFg.7qJwlCyw0CD3HrusJ4Noe', 'admin');
INSERT INTO usuario (id, apellido, email, nombre, password, username) VALUES (8, 'robot', 'robot', 'robot', '$2a$10$tAIWvJs6xV6OWXaYDHLBH.RvQjBYrlLtSoDfcOAfeP8IndROBB6p6', 'robot');

INSERT INTO Usuarios.usuario_roles (usuario_id, roles_id) VALUES (7, 4);
INSERT INTO Usuarios.usuario_roles (usuario_id, roles_id) VALUES (7, 5);
INSERT INTO Usuarios.usuario_roles (usuario_id, roles_id) VALUES (8, 6);
