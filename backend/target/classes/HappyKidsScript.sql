

--Creación de la Base de datos--

Drop database if exists happykids;


CREATE DATABASE happykids WITH ENCODING='UTF8';

\c happykids

--Definicion de las tablas--

--Status-- 

CREATE TABLE TMSTATUS(
    pkcod_status integer not null primary key,
    dstatus varchar(10) not null
);

INSERT INTO TMSTATUS(pkcod_status,dstatus)VALUES(0,'ELIMINADO');
INSERT INTO TMSTATUS(pkcod_status,dstatus)VALUES(1,'ACTIVO');

--Mostrar--

SELECT*FROM TMSTATUS;

--Rol--

CREATE TABLE TMROL(
    pkcodrol_usuario integer not null primary key,
    Drol varchar(12) not null
);

INSERT INTO TMROL(pkcodrol_usuario,Drol) VALUES(0,'ADMIN');
INSERT INTO TMROL(pkcodrol_usuario,Drol) VALUES(1,'CLIENTE');

--Mostrar--

SELECT*FROM TMROL;

CREATE TABLE TMUSUARIOS(
    pkcodusuario integer not null primary key,
    correo_usuario varchar(50) not null,
    contrasena_usuario varchar(255) not null,
    fecha_registro DATE not null default CURRENT_DATE,
    fkcods integer not null,
    fkrol_usuario integer not null,

    foreign key(fkcods) REFERENCES TMSTATUS(pkcod_status) 
    on update cascade on delete restrict,
    foreign key(fkrol_usuario) REFERENCES TMROL(pkcodrol_usuario)
    on update cascade on delete restrict
); 

---Agregar Usuario Base---

INSERT INTO TMUSUARIOS(pkcodusuario,correo_usuario,contrasena_usuario,fecha_registro,fkcods,fkrol_usuario)VALUES(0,'san.cvelandia@gmail.com','3118913Niko*',CURRENT_DATE,1,0);

INSERT INTO TMUSUARIOS(pkcodusuario,correo_usuario,contrasena_usuario,fecha_registro,fkcods,fkrol_usuario)VALUES(1, 'proyectm7@gmail.com','12345Prueba*',CURRENT_DATE,1,1);

--Mostrar--

SELECT*FROM TMUSUARIOS;

--CLIENTE--

CREATE TABLE TMCLIENTES(
    pkcodcliente integer not null primary key,
    nombre_cliente varchar(60) not null,
    telefono_cliente BIGINT not null,
    fkcods integer not null,
    fkusuario_asociado integer,
    
    foreign key(fkcods) REFERENCES TMSTATUS(pkcod_status)
    on update cascade on delete restrict,
    foreign key(fkusuario_asociado) REFERENCES TMUSUARIOS(pkcodusuario)
    on update cascade on delete restrict
);

--AGG--

INSERT INTO TMCLIENTES(pkcodcliente,nombre_cliente,telefono_cliente,fkcods,fkusuario_asociado)VALUES(0,'Paula Mendoza',3244897329,1,1);

--Mostrar--

--METODO--

CREATE TABLE TMMETODO(
    pkcodmetodo integer not null primary key,
    Dmetodo varchar(50) not null
);

INSERT INTO TMMETODO(pkcodmetodo,Dmetodo)VALUES(0,'EFECTIVO');
INSERT INTO TMMETODO(pkcodmetodo,Dmetodo)VALUES(1,'TARJETA');
INSERT INTO TMMETODO(pkcodmetodo,Dmetodo)VALUES(2,'TRANSFERENCIA');
INSERT INTO TMMETODO(pkcodmetodo,Dmetodo)VALUES(3,'NEQUI');

--mostrar
SELECT*FROM TMMETODO;

CREATE TABLE TMVENTAS(
    pkcodventa integer not null primary key,
    fecha_venta DATE not null,
    total_venta decimal (12,2) not null,
    direccion_envio varchar(150) not null,
    fkmetodo_pago integer not null,
    fkcodcliente integer not null,
    fkcods integer not null,

    foreign key(fkmetodo_pago) REFERENCES TMMETODO(pkcodmetodo)
    on update cascade on delete restrict,

    foreign key(fkcodcliente) REFERENCES TMCLIENTES(pkcodcliente)
    on update cascade on delete restrict,

    foreign key(fkcods) REFERENCES TMSTATUS(pkcod_status)
    on update cascade on delete restrict

);

CREATE TABLE TMPRODUCTOS(
    pkcodproducto integer not null primary key,
    serial_p varchar(20) UNIQUE,
    nombre_p varchar(50) not null, 
    imagen_url varchar(500),
    Dproducto varchar(50) not null,
    precio_compra decimal(12,2) not null,
    precio_venta decimal(12,2) not null,
    stock integer not null,
    fkcods integer not null,
    
    foreign key(fkcods) references TMSTATUS(pkcod_status)
    on update cascade on delete restrict
    );

    --GLOBOS--
    INSERT INTO TMPRODUCTOS(pkcodproducto,serial_p,nombre_p,Dproducto,precio_compra,precio_venta,stock,fkcods)VALUES(21,'7703340231563','Globo R-12 Fashion Azul Rey','Sempertex x50 unidades (28-30cms)',5000,10000,10,1);
    INSERT INTO TMPRODUCTOS(pkcodproducto,serial_p,nombre_p,Dproducto,precio_compra,precio_venta,stock,fkcods)VALUES(22,'7703340155722','Globo R-5 Pastel Mate Azul','Sempertex x50 unidades (13-15cms)',4000,8000,10,1);
    INSERT INTO TMPRODUCTOS(pkcodproducto,serial_p,nombre_p,Dproducto,precio_compra,precio_venta,stock,fkcods)VALUES(23,'7703340402079','Globo Tubito 260 Pastel Mate Azul','Sempertex x20 unidades',5000,10000,10,1);
    INSERT INTO TMPRODUCTOS(pkcodproducto,serial_p,nombre_p,Dproducto,precio_compra,precio_venta,stock,fkcods)VALUES(24,'7703340169644','Globo R-12 Reflex Dorado','Sempertex x50 unidades (28-30cms)',5000,10000,10,1);



CREATE TABLE TDVENTAS(
    pkcodetalle_v integer not null primary key,
    fkcodventa integer not null,
    fkcodproducto integer not null,

    foreign key(fkcodventa) REFERENCES TMVENTAS(pkcodventa)
    on update cascade on delete restrict,

    foreign key(fkcodproducto) REFERENCES TMPRODUCTOS(pkcodproducto)
    on update cascade on delete restrict
); 

CREATE TABLE TMCATEGORIAS(
    pkcod_ca integer not null primary key,
    nombre_ca varchar(40) not null,
    Dcategoria varchar(70) not null
);

INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(0,'PELUCHES','Muñecos de felpa de diferentes tamaños y motivos');
INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(1,'CONFITERIA','Dulces, gomas y snacks para fiestas');
INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(2,'INFLABLES','Globos latex, metalizados, números y accesorios');
INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(3,'DECORACIÓN','Articulos para ambientación de eventos y mesas');
INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(4,'DESECHABLES','Menaje de un solo uso para servir alimentos');
INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(5,'EMPAQUES','Bolsas de regalo, cajas decorativas y moños');
INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(6,'BISUTERIA','Joyeria, collares, pulseras y anill0s');
INSERT INTO TMCATEGORIAS(pkcod_ca,nombre_ca,Dcategoria)VALUES(7,'ACCESORIOS DE CABELLO','Diademas, ganchos, moños y articulos de peinado');

Select*from TMCATEGORIAS; 

CREATE TABLE TDCATEGORIAS(
    pkcod_cad integer not null primary key,
    fkcodca integer not null,
    fkcodproducto integer not null,

    foreign key(fkcodca) references TMCATEGORIAS(pkcod_ca)
    on update cascade on delete restrict,

    foreign key(fkcodproducto)references TMPRODUCTOS(pkcodproducto)
    on update cascade on delete restrict
); 
   
   --Categorias-(GLOBOS)
   INSERT INTO TDCATEGORIAS(pkcod_cad,fkcodca,fkcodproducto)VALUES(1,2,21);
   INSERT INTO TDCATEGORIAS(pkcod_cad,fkcodca,fkcodproducto)VALUES(2,2,22);
   INSERT INTO TDCATEGORIAS(pkcod_cad,fkcodca,fkcodproducto)VALUES(3,2,23);
   INSERT INTO TDCATEGORIAS(pkcod_cad,fkcodca,fkcodproducto)VALUES(4,2,24);

CREATE TABLE TMPROVEEDOR(
    pkcodproveedor integer not null primary key,
    pro_num BIGINT not null,
    pro_nom varchar(50) not null,
    pro_direc varchar(80),
    pro_correo varchar(50),
    fkcods integer not null,

    foreign key(fkcods) REFERENCES TMSTATUS(pkcod_status)
    on update cascade on delete restrict
);
    ---Proveedor Insert---
    INSERT INTO TMPROVEEDOR(pkcodproveedor,pro_num,pro_nom,pro_direc,pro_correo,fkcods)VALUES(0,3224108594,'La Gran Piñata','Av. 8 #9-46, Barrio Llano, Cúcuta, Norte de Santander, Colombia','lagranpinata@gmail.com',1);

CREATE TABLE TDPROVEEDOR(
    pkcodetalle_p integer not null primary key,
    fkcod_pro integer not null,
    fkcod_proveedor integer not null,

    foreign key(fkcod_pro) REFERENCES TMPRODUCTOS(pkcodproducto)
    on update cascade on delete restrict,

    foreign key(fkcod_proveedor) REFERENCES TMPROVEEDOR(pkcodproveedor)
    on update cascade on delete restrict
);
---Detaller proveedor--
    INSERT INTO TDPROVEEDOR(pkcodetalle_p,fkcod_pro,fkcod_proveedor)VALUES(1,21,0);
    INSERT INTO TDPROVEEDOR(pkcodetalle_p,fkcod_pro,fkcod_proveedor)VALUES(2,22,0);
    INSERT INTO TDPROVEEDOR(pkcodetalle_p,fkcod_pro,fkcod_proveedor)VALUES(3,23,0);
    INSERT INTO TDPROVEEDOR(pkcodetalle_p,fkcod_pro,fkcod_proveedor)VALUES(4,24,0);

