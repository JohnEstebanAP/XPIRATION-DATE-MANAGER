/*CREATE DATABASE DB_XPIRATION_DATA_MANAGER;

USE DB_XPIRATION_DATA_MANAGER;*/

CREATE TABLE TipoUsuario(
  cod_tipo_usuario Integer primary key  not null,
  tipoUsuario TEXT not null
);

CREATE TABLE Permiso(
  cod_permiso Integer primary key not null,
  nom_permiso TEXT not null
);

CREATE TABLE Usuario(
  ced_usuario Integer PRIMARY KEY  not null,              
  nom_usuario  TEXT not null,
  apellido_usuario  TEXT not null,
  correo_usuario TEXT not null,
  contrasenia_usuario TEXT not null,
  telefono_usuario Integer null,
  cod_tipo_usuario Integer not null,
  CONSTRAINT TipoUsuario_fk FOREIGN KEY (cod_tipo_usuario) REFERENCES TipoUsuario(cod_tipo_usuario)
);

CREATE TABLE UsuarioPermiso(
  ced_usuario Integer  not null,     
  cod_permiso Integer  not null,
  Primary Key (ced_usuario,cod_permiso),
  constraint Usuario_fk foreign key  (ced_usuario) REFERENCES Usuario(ced_usuario),
  constraint Permiso_fk foreign key  (cod_permiso) REFERENCES Permiso (cod_permiso)
);

CREATE TABLE Proveedor(
  cod_proveedor Integer PRIMARY KEY not null, 
  nom_proveedor TEXT not null,
  telefono_proveedor INTEGER not null,
  correo_proveedor TEXT not null
 );

CREATE TABLE Pasillo(
  cod_pasillo TEXT primary KEY not null,
  descripcion TEXT null
);

CREATE TABLE Estanteria(
  cod_estanteria Integer PRIMARY KEY not null,
  cod_pasillo TEXT not null,
  CONSTRAINT Pasillo_fk FOREIGN KEY (cod_pasillo) REFERENCES Pasillo(cod_pasillo)
);

CREATE TABLE EstadoProducto(
  cod_estadoPro Integer PRIMARY KEY not null,
  tipo_estado TEXT not null
);

CREATE TABLE Caducidad(
  cod_caducidad Integer PRIMARY KEY not null,
  tipo_caducidad TEXT not null
);

CREATE TABLE Producto(
  cod_Producto Integer primary key not null,
  nom_Producto TEXT NOT null,
  nombre_marca TEXT null,
  cantidad Integer not null,
  fecha_Producto date not null,
  fecha_caducidad date not null,
  fecha_ingreso_Producto datetime not null,
  cod_estadoPro Integer not null,
  cod_estanteria Integer not null,
  cod_caducidad Integer not null,
CONSTRAINT EstadoProducto_fk foreign key (cod_estadoPro) references EstadoProducto(cod_estadoPro),
CONSTRAINT Estanteria_fk foreign key (cod_estanteria) references Estanteria(cod_estanteria),
CONSTRAINT Caducidad_fk foreign key (cod_caducidad) references Caducidad(cod_caducidad)
);


Create table ProductoProveedor(
	cod_ProductoProveedor INTEGER PRIMARY KEY AUTOINCREMENT,
  	cod_Producto Integer not null,
	cod_proveedor Integer not null,
    CONSTRAINT Producto_fk foreign key (cod_Producto) references Producto(cod_Producto),
	CONSTRAINT Proveedor_fk foreign key (cod_proveedor) references Proveedor(cod_proveedor)
);

/*
CREATE TABLE EncargadoRotacionProducto(
  cod_rotacion INTEGER PRIMARY KEY AUTOINCREMENT,
  fecha_rotacion time not null,
  ced_usuario bigint not null,
  CONSTRAINT Usuario_fk FOREIGN KEY (ced_usuario) REFERENCES Usuario (ced_usuario)
);*/


CREATE TABLE TipoCaducidadAlertas(
  cod_caducidad_alertas Integer PRIMARY KEY not null,
  tipo_caducidad_alertas TEXT not null,
  descripcion TEXT null
 );


CREATE TABLE TipoEstadoAlerta(
  cod_estado_alertas Integer PRIMARY KEY not null,
  tipo_estado_alertas TEXT not null,
  descripcion TEXT null
);

CREATE TABLE HistorialAlertas(
  cod_historial  INTEGER PRIMARY KEY AUTOINCREMENT,
  fecha_historial date not null,
  cod_Producto Integer not null,
  cod_caducidad_alertas Integer not null,
  cod_estado_alertas Integer not null,
  CONSTRAINT TipoCaducidadAlertas_fk foreign key (cod_caducidad_alertas) references TipoCaducidadAlertas(cod_caducidad_alertas),
  CONSTRAINT TipoEstadoAlerta_fk foreign key (cod_estado_alertas) references TipoEstadoAlerta(cod_estado_alertas), 
  CONSTRAINT Producto_fk foreign key (cod_Producto) references Producto(cod_Producto)
  );
 
 

CREATE TABLE UsuarioHistorialAlertas(
 cod_UsuarioHistorialAlertas Integer primary key AUTOINCREMENT,
 fecha_atencionNotificacion date not null,
 cod_historial Integer unique not null,
 ced_usuario Integer not null,
 cambioEstado_producto Integer not null,
 constraint Usuario_fk foreign key (ced_usuario) REFERENCES Usuario(ced_usuario),
 constraint HistorialAlertas_fk foreign key (cod_historial) REFERENCES HistorialAlertas (cod_historial),
 constraint EstadoProducto_fk foreign key (cambioEstado_producto) REFERENCES EstadoProducto (cod_estadoPro)
);


create table BancoAlimentos(
cod_banco_alimentos Integer  not null,
nombre_banco_alimentos TEXT not null,
dirr_banco_alimentos TEXT not null,
telefono Integer not null,
correo TEXT not null
);

create table BancoAlimentosProducto(
cod_donacion INTEGER PRIMARY KEY AUTOINCREMENT,
fecha_donacion datetime not null,
cod_banco_alimentos Integer not null,
cod_Producto Integer not null,
CONSTRAINT BancoAlimentos_fk FOREIGN KEY (cod_banco_alimentos) REFERENCES BancoAlimentos(cod_banco_alimentos),
CONSTRAINT Producto_fk FOREIGN KEY (cod_Producto) REFERENCES Producto(cod_Producto)
);



/*///////////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/
/*//////////////////////////////////////////////////////      INSERCIÃ“N    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/
/*//////////////////////////////////////////////////////         DE        \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/
/*//////////////////////////////////////////////////////      VALORES      \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/
/*///////////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/


/*---------------------------------------------------- Insercion de Valores -------------------------------------------------*/

/*---------------------------------------------------- Tipo usuario ---------------------------------------------------------*/

INSERT INTO TipoUsuario(cod_tipo_usuario,tipoUsuario) VALUES (1,'Desarrollador');
INSERT INTO TipoUsuario(cod_tipo_usuario,tipoUsuario) VALUES (2,'Jefe de ventas');
INSERT INTO TipoUsuario(cod_tipo_usuario,tipoUsuario) VALUES (3,'Jefe de zona');
INSERT INTO TipoUsuario(cod_tipo_usuario,tipoUsuario) VALUES (4,'Supervisor');
INSERT INTO TipoUsuario(cod_tipo_usuario,tipoUsuario) VALUES (5,'Asistentes de venta');

/*---------------------------------------------------- Permiso ---------------------------------------------------------*/

INSERT INTO Permiso(cod_permiso, nom_permiso) VALUES (1,'Agregar productos');
INSERT INTO Permiso(cod_permiso, nom_permiso) VALUES (2,'Actualizar productos');
INSERT INTO Permiso(cod_permiso, nom_permiso) VALUES (3,'Eliminar productos');
INSERT INTO Permiso(cod_permiso, nom_permiso) VALUES (4,'Ver base de datos');
INSERT INTO Permiso(cod_permiso, nom_permiso) VALUES (5,'Ver estadisticas');


/*--------------------------------------------Usuario--------------------------------------------*/

INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES (1057210001,'JUAN', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',1, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210002,'PEDRO', 'ALVAREZ ESCOBAR', 'juan@gmail.com','123',2, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210003,'LAURA', 'JARAMILLO PALCIO', 'juan@gmail.com','123',3, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210004,'ERNESTO', 'PIEDRHAITA CORONA', 'juan@gmail.com','123',4, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210005,'EMILIA', 'GONZALES ', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210006,'LUPITA', 'CARDONADO PALASE', 'juan@gmail.com','123',1, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210007,'MARIA LYSHET', 'STMILER DANSGUIN', 'juan@gmail.com','123',2, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210008,'FELIPE', 'ALVAREZ ', 'juan@gmail.com','123',3, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210009,'JOHN SHMIT', 'GARZON CORONA', 'juan@gmail.com','123',4, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210010,'CAMILO', 'CASTRO BUENO ', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210011,'JUAN CAMILO', '', 'juan@gmail.com','123',4, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210012,'JENNIFER', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',4, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210013,'DAVID', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210014,'ENRIQUE QUINTO', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210015,'LEONISA', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210016,'BLANCA', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210017,'ELISABET', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210018,'EMILIO','JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210019,'VANESSA', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210020,'LEONARDO', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210021,'SARA', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210022,'PAULO', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210023,'ROSA', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210024,'ROUS', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);
INSERT INTO Usuario(ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario, cod_tipo_usuario, telefono_usuario ) VALUES  (1057210025,'ANDREA', 'JARAMILLO ESCOBAR', 'juan@gmail.com','123',5, 3118111526);


/*---------------------------------------------------- UsuaurioPermiso ---------------------------------------------------------*/

INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210025,1);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210024,1);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210023,1);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210022,1);

INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210025,2);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210024,2);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210023,2);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210022,2);

INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210025,3);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210024,3);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210023,3);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210022,3);

INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210025,4);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210024,4);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210023,4);
INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210022,4);

INSERT INTO UsuarioPermiso(ced_usuario, cod_permiso) VALUES (1057210025,5);


/*--------------------------------------------Proveedor--------------------------------------------*/
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (1,'COMERCIAL NUTRESA',108854621,'NOEL@Nutresa.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (2,'Alpina',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (3,'Bavaria',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (4,'Águila roja',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (5,'POSTOBON S.A',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (6,'Alquería',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (7,'Colombina',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (8,'Grupo Manuelita',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (9,'Tecnoquimicas',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (10,'Leonisa',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (11,'Nabisco',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (12,'Bimbo',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (13,'Monoprix',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (14,'Levapan',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (15,'Ferrero',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (16,'Frutiño',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (17,'Fitness',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (18,'Servipan',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (19,'Eurosemillas',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (20,'Riovalle',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (21,'Robin-hood',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (22,'Marinela',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (23,'AJECOLOMBIA',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (24,'ALIMENTOS CARNICOS',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (25,'BIMBO DE COLOMBIA S.A',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (26,'ZONA 2 DISTRIBUCIONES S.A.S',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (27,'TROPICANA S.A.S',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (28,'QUALA',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (29,'PROVECOL',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (30,'Producto SEBA SEBA S.A.S',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (31,'MEAL DE COLOMBIA S.A.S',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (32,'LAVAPAN S.A',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (33,'LACTEOS BUENOS AIRES S.A.S',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (34,'COOPERATIVA COLANTA',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (35,'COMERCIALIZADORA DE HIELOS IGLU',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (36,'COMERCIALIZADORA ASOA',108854621,'NOEL@NOEL.COM');
INSERT INTO Proveedor(cod_proveedor,nom_proveedor,telefono_proveedor,correo_proveedor) VALUES (37,'Coca-cola',108854621,'NOEL@NOEL.COM');



/*--------------------------------------------Pasillo--------------------------------------------*/
INSERT INTO Pasillo(cod_pasillo) VALUES ('A1');
INSERT INTO Pasillo(cod_pasillo) VALUES ('B1');
INSERT INTO Pasillo(cod_pasillo) VALUES ('C1');
INSERT INTO Pasillo(cod_pasillo) VALUES ('D1');
INSERT INTO Pasillo(cod_pasillo) VALUES ('A2');
INSERT INTO Pasillo(cod_pasillo) VALUES ('B2');
INSERT INTO Pasillo(cod_pasillo) VALUES ('C2');
INSERT INTO Pasillo(cod_pasillo) VALUES ('D2');
INSERT INTO Pasillo(cod_pasillo) VALUES ('1');
INSERT INTO Pasillo(cod_pasillo) VALUES ('2');
INSERT INTO Pasillo(cod_pasillo) VALUES ('3');
INSERT INTO Pasillo(cod_pasillo) VALUES ('4');




/*--------------------------------------------Estanteria--------------------------------------------*/
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (1,'A1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (2,'A1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (3,'A1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (4,'A2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (5,'A2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (6,'A2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (7,'B1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (8,'B1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (9,'B1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (10,'B2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (11,'B2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (12,'B2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (13,'C1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (14,'C1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (15,'C1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (16,'C2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (17,'C2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (18,'C2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (19,'D1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (20,'D1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (21,'D1');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (22,'D2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (23,'D2');
INSERT  INTO Estanteria(cod_estanteria,cod_pasillo) VALUES  (24,'D2');



/*--------------------------------------------EstadoProducto--------------------------------------------*/

INSERT INTO EstadoProducto(cod_estadoPro, tipo_estado) VALUES  (1, 'Al Frente');
INSERT INTO EstadoProducto(cod_estadoPro, tipo_estado) VALUES  (2, 'En Promocion');
INSERT INTO EstadoProducto(cod_estadoPro, tipo_estado) VALUES  (3, 'En Descuento');
INSERT INTO EstadoProducto(cod_estadoPro, tipo_estado) VALUES  (4, 'Normal');





/*--------------------------------------------Caducidad--------------------------------------------*/

INSERT INTO Caducidad(cod_caducidad, tipo_caducidad) VALUES  (1, 'Producto desperdiciado');
INSERT INTO Caducidad(cod_caducidad, tipo_caducidad) VALUES  (2, 'Al Producto le faltan 3 dias para vencer');
INSERT INTO Caducidad(cod_caducidad, tipo_caducidad) VALUES  (3, 'Producto prÃ³ximo a vencer');
INSERT INTO Caducidad(cod_caducidad, tipo_caducidad) VALUES  (4, 'Producto en estado normal');





/*-------------------------------------------------------------Producto--------------------------------------------------------------*/
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES  (1,'LECHE', 'Alpina', 10, '2020-10-11', '2020-11-5','2020-10-20', 4, 1, 4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES  (2,'PANELA', 'Bavaria', 10,'2020-10-11','2020-12-1','2020-10-20', 4, 24, 4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES (3,'ARROZ', 'ROA', 10, '2020-5-4', '2020-11-5','2020-10-20', 4, 1, 4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES (4,'COCA-COLA', 'Coca-cola', 10,'2020-5-4', '2020-11-5','2020-10-20', 4, 1, 4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES (5,'Hit', 'Coca-cola', 10,'2020-5-4', '2020-11-5','2020-10-20', 4, 1, 4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad) 
VALUES (6,'Chocolate Corona','Corona', 10, '2020-5-4', '2020-11-5','2020-10-20', 4, 1, 4);

INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad) 
VALUES(7,'Leche pasteurisado de 1 Litro','Alpina', 10, '2020-10-20','2020-11-20','2020-10-20',1,2,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(8,'MARGARINA RAMA CULINARIA X 125 GRS','Margarita', 10, '2020-9-11','2020-11-20','2020-10-20',2,2,4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(9,'PANELA MOLDE 8UN X 113.5G CARIBE','CARIBE', 10, '2020-5-7','2020-12-6','2020-10-20',1,3,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(10,'NESCAFÉ FRASCO X 50GRS','NESCAFÉ', 10, '2020-9-4','2020-10-29','2020-10-20',1,9,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(11,'BLANQUILLO ABURRA X 500 GRS','ABURRA', 10,'2020-8-5','2020-12-5','2020-10-20',1,3,4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(12,'BLANQUILLO ABURRA X 500 GRS','ABURRA', 10,'2020-8-5','2020-12-5','2020-10-20',1,3,4);

INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(13,'MAIZENA FÉCULA X 720 GRS','MAIZENA', 10,'2020-8-5','2020-11-1','2020-10-20',1,3,2);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(14,'GELATINA ROYAL FRESA 40 GR INDIV','ROYAL', 10,'2020-8-5','2020-11-5','2020-10-20',1,3,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(15,'CHOCOLISTO CROCANTE X 500 GRS','CHOCOLISTO', 10,'2020-8-5','2020-12-5','2020-10-20',4,5,4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(16,'TOSTADA INTEGRAL JUANCHOPAN 10UN X 16G','JUANCHOPAN ', 10,'2020-10-5','2020-11-5','2020-10-20',1,3,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(17,'COSECHA PURA NARANJA 330ML','Hit', 10,'2020-10-5','2020-11-2','2020-10-20',1,5,2);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(18,'LECHE PASTEURISADO DE 1 LITRO','Alpina', 10,'2020-10-5','2020-10-20','2020-10-20',4,2,1);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(19,'ACEITE  GOURMET 1000 CC N.V','GOURMET', 10,'2020-9-5','2020-12-5','2020-10-20',4,3,4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(20,'CREMA MAGGI MARINERA INDIV C24','MAGGI', 10,'2020-8-5','2020-11-5','2020-10-20',1,3,2);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(21,'FRUCO SALSA TOM X 400 GRS VIDRIO','FRUCO', 10,'2020-8-5','2020-11-5','2020-10-20',1,3,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(22,'KLIM 1 MIEL FORTIPROTECT X 1 KL','KLIM', 10,'2020-8-5','2020-11-10','2020-10-20',1,3,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(23,'SALCHICHA VIENA ZENÚ PLL. X 150 GRS','ZENU', 10,'2020-8-5','2020-11-15','2020-10-20',1,3,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(24,'MARGARINA RAMA CULINARIA X 125 GRS','RAMA', 10,'2020-8-5','2020-11-20','2020-10-20',1,3,3);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(25,'PANELA MOLDE 8UN X 113.5G CARIBE','ABURRA', 10,'2020-8-5','2020-11-1','2020-10-20',1,6,2);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(26,'NESCAFÉ FRASCO X 50GRS','NESCAFÉ', 10,'2020-8-5','2020-11-2','2020-10-20',1,3,2);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(27,'BLANQUILLO ABURRA X 500 GRS','ABURRA', 10,'2020-8-5','2020-12-5','2020-10-20',1,3,4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(28,'MAIZENA FÉCULA X 720 GRS','MAIZENA', 10,'2020-8-5','2020-12-5','2020-10-20',1,3,4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(29,'GELATINA ROYAL FRESA 40 GR INDIV','ROYAL', 10,'2020-8-5','2020-12-5','2020-10-20',1,3,4);
INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)
VALUES(30,'CHOCOLISTO CROCANTE X 500 GRS','CHOCOLISTO', 10,'2020-8-5','2020-12-5','2020-10-20',1,3,4);

/*-------------------------------------------------------------ProductoProveedor--------------------------------------------------------------*/

Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(1,1);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(2,2);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(3,21);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(4,6);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(5,1);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(6,2);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(7,2);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(8,10);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(9,2);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(10,5);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(11,8);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(11,8);

Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(12,9);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(13,10);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(14,11);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(15,12);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(16,13);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(17,14);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(18,15);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(19,16);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(20,17);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(21,18);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(22,19);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(23,20);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(24,21);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(25,22);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(26,23);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(27,24);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(28,25);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(29,26);
Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(30,27);

/*-------------------------------------------------------------------------------------------------------------------------------------------*/






/*--------------------------------------------EncargadoRotacionProducto--------------------------------------------

insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210021);
insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210018);
insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210014);
insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210005);
insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210017);
insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210012);
insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210008);
insert into EncargadoRotacionProducto(fecha_rotacion,ced_usuario) values ('2020-11-5',1057210005);
--------------------------------------------------------------------------------------------------------------------*/


/*--------------------------------------------TipoCaducidadAlertas--------------------------------------------*/
insert into TipoCaducidadAlertas (cod_caducidad_alertas, tipo_caducidad_alertas ) values (1, 'Producto desperdiciado');
insert into TipoCaducidadAlertas (cod_caducidad_alertas, tipo_caducidad_alertas ) values (2, 'Al Producto le faltan 3 dias para vencer');
insert into TipoCaducidadAlertas (cod_caducidad_alertas, tipo_caducidad_alertas ) values (3, 'Producto prÃ³ximo a vencer');
insert into TipoCaducidadAlertas (cod_caducidad_alertas, tipo_caducidad_alertas ) values (4, 'Producto en estado normal');

/*------------------------------------------------------------------------------------------------------------------*/



/*--------------------------------------------TipoEstadoAlerta--------------------------------------------*/
insert into TipoEstadoAlerta (cod_estado_alertas, tipo_estado_alertas) values (1, 'atendida');
insert into TipoEstadoAlerta (cod_estado_alertas, tipo_estado_alertas) values (2, 'sin atender');
/*------------------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------HistorialAlertas--------------------------------------------------*/
INSERT INTO HistorialAlertas(fecha_historial,cod_producto,cod_caducidad_alertas,cod_estado_alertas ) VALUES ('2020-10-5',1,1,2);
INSERT INTO HistorialAlertas(fecha_historial,cod_producto,cod_caducidad_alertas,cod_estado_alertas ) VALUES ('202-10-5',8,2,2);
INSERT INTO HistorialAlertas(fecha_historial,cod_producto,cod_caducidad_alertas,cod_estado_alertas ) VALUES ('202-10-5',4,3,2);
INSERT INTO HistorialAlertas(fecha_historial,cod_producto,cod_caducidad_alertas,cod_estado_alertas ) VALUES ('2020-10-5',5,4,2);
INSERT INTO HistorialAlertas(fecha_historial,cod_producto,cod_caducidad_alertas,cod_estado_alertas ) VALUES ('2020-10-5',7,1,1);
INSERT INTO HistorialAlertas(fecha_historial,cod_producto,cod_caducidad_alertas,cod_estado_alertas ) VALUES ('2020-10-5',2,2,1);
INSERT INTO HistorialAlertas(cod_historial, fecha_historial,cod_producto,cod_caducidad_alertas,cod_estado_alertas ) VALUES (15,'2020-10-5',2,2,1);
/*----------------------------------------------------------------------------------------------------------------------------*/


/*-------------------------------------------------------UsuarioHistorialAlertas--------------------------------------------------------*/

insert into UsuarioHistorialAlertas(fecha_atencionNotificacion, cod_historial, ced_usuario, cambioEstado_producto) values
('2020-10-5',5,1057210016,4),
('2020-10-5',6,1057210017,4);
/*-----------------------------------------------------------------------------------------------------------------------------*/


/*-------------------------------------------------------BancoAlimentos--------------------------------------------------------*/

insert into BancoAlimentos(cod_banco_alimentos, nombre_banco_alimentos, dirr_banco_alimentos, telefono, correo) values (1, 'FundaciÃ³n Banco Arquidiocesano de Alimentos de MedellÃ­n', 'Carrera 52 #30A â€“ 97 MedellÃ­n â€“ Antioquia',  5744483888, 'donaciones@bancodealimentos.co');
/*-----------------------------------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------BancoAlimentos--------------------------------------------------------*/

insert into BancoAlimentosProducto(fecha_donacion, cod_banco_alimentos, cod_Producto) values ('2020-12-30',1,1);
insert into BancoAlimentosProducto(fecha_donacion, cod_banco_alimentos, cod_Producto) values ('2020-12-30',1,2);
insert into BancoAlimentosProducto(fecha_donacion, cod_banco_alimentos, cod_Producto) values ('2020-12-30',1,3);
/*-----------------------------------------------------------------------------------------------------------------------------*/



-------------------------------Consultas-------------------------------------
/*

SELECT * from Caducidad;
SELECT * from EstadoProducto;
SELECT * from Estanteria;
SELECT * from HistorialAlertas;
SELECT * from Notificacion;
SELECT * from Pasillo;
SELECT * from Producto;
SELECT * from Proveedor;
SELECT * from TipoCaducidadAlertas;
SELECT * from TipoEstadoAlerta;
select * from TipoUsuario;
SELECT * from Usuario;
SELECT * from Permiso;
SELECT * from UsuarioPermiso;
SELECT * from  ProductoProveedor;


SELECT nom_usuario, ced_usuario, correo_usuario, contrasenia_usuario from Usuario where ced_usuario = 1057210001 or correo_usuario = '';

SELECT count(*)  FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro
INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad
INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto
INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor
INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria


select p.cod_Producto, p.nom_Producto, p.nombre_marca, Proveedor.cod_proveedor,p.cod_estanteria, Estanteria.cod_pasillo, p.fecha_Producto, p.fecha_caducidad, p.fecha_ingreso_Producto, EstadoProducto.tipo_estado , Caducidad.tipo_caducidad
FROM Producto AS p
INNER JOIN EstadoProducto on p.cod_estadoPro = EstadoProducto.cod_estadoPro
INNER JOIN Caducidad on p.cod_caducidad  = Caducidad.cod_caducidad
INNER JOIN ProductoProveedor on p.cod_Producto = ProductoProveedor.cod_Producto
INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor
INNER JOIN Estanteria on p.cod_estanteria= Estanteria.cod_estanteria
where   fecha_caducidad BETWEEN '2020-11-0 ' and '2020-12-1' 


SELECT Producto.cod_Producto, Producto.nom_Producto, Producto.nombre_marca, Proveedor.cod_proveedor,Producto.cod_estanteria, Estanteria.cod_pasillo, Producto.fecha_Producto, Producto.fecha_caducidad, Producto.fecha_ingreso_Producto, EstadoProducto.tipo_estado , Caducidad.tipo_caducidad FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria
where   fecha_caducidad BETWEEN '2020-11-0' and '2020-12-1'         

SELECT Producto.cod_Producto, Producto.nom_Producto, Producto.nombre_marca, Proveedor.cod_proveedor,Producto.cod_estanteria, Estanteria.cod_pasillo, Producto.fecha_Producto, Producto.fecha_caducidad, Producto.fecha_ingreso_Producto, EstadoProducto.tipo_estado , Caducidad.tipo_caducidad FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria WHERE fecha_caducidad BETWEEN '2020-11-0' and '2020-12-1'

select count(*)
FROM Producto AS p
INNER JOIN EstadoProducto on p.cod_estadoPro = EstadoProducto.cod_estadoPro
INNER JOIN Caducidad on p.cod_caducidad  = Caducidad.cod_caducidad
INNER JOIN ProductoProveedor on p.cod_Producto = ProductoProveedor.cod_Producto
INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor
INNER JOIN Estanteria on p.cod_estanteria= Estanteria.cod_estanteria
where   fecha_caducidad BETWEEN '2020-11-0 ' and '2020-12-1' 



 select p.cod_Producto, p.nom_Producto, p.nombre_marca, Proveedor.cod_proveedor,p.cod_estanteria, Estanteria.cod_pasillo, p.fecha_Producto, p.fecha_caducidad, p.fecha_ingreso_Producto, EstadoProducto.tipo_estado , Caducidad.tipo_caducidad
FROM Producto AS p
INNER JOIN EstadoProducto on p.cod_estadoPro = EstadoProducto.cod_estadoPro
INNER JOIN Caducidad on p.cod_caducidad  = Caducidad.cod_caducidad
INNER JOIN ProductoProveedor on p.cod_Producto = ProductoProveedor.cod_Producto
INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor
INNER JOIN Estanteria on p.cod_estanteria= Estanteria.cod_estanteria
where   Producto.cod_caducidad = 2*/
 
