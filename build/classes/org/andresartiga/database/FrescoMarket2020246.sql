drop database if exists DBFrescoMarket;
create database DBFrescoMarket;
use DBFrescoMarket;
set global time_zone = '-6:00';

create table Clientes(
	codigoCliente int primary key not null,
    nitCliente varchar(10),
    nombreCliente varchar(50),
    apellidoCliente varchar(50),
    direccionCliente varchar(150),
    telefonoCliente varchar(8),
    correoCliente varchar(45)
);

create table TipoProducto(
	idTipoProducto int primary key not null,
    descripcion varchar(45)
);

create table Compras(
	numeroDocumento int primary key not null,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2)
);



create table CargoEmpleado(
	idCargoEmpleado int primary key not null,
    nombreCargo varchar(45),
    descripcionCargo varchar(45)
);

create table Proveedores(
	codigoProveedor int primary key not null,
    nitProveedor varchar(10),
    nombreProveedor varchar(50),
    apellidoProveedor varchar(50),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50)
);

delimiter $$
create procedure sp_agregarTipoProducto (in idTipo int, in descrip varchar(45))
begin
	insert into TipoProducto (TipoProducto.idTipoProducto, TipoProducto.descripcion)
    values (idTipo, descrip);
end$$
delimiter ;

delimiter $$
	create procedure sp_listarTipoProducto ()
    begin
		select idTipoProducto, descripcion from TipoProducto;
    end$$
delimiter ;

delimiter $$
	create procedure sp_buscarTipoProducto(in idTipo int)
    begin
	select idTipoProducto, descripcion from TipoProducto where TipoProducto.idTipoProducto = idTipo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarTipoProducto(in idTipo int, in descrip varchar(50))
    begin
		update TipoProducto
		set
        TipoProducto.descripcion = descrip
        where 
        TipoProducto.idTipoProducto = idTipo;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarTipoProducto (in idTipo int)
    begin
		delete from TipoProducto where TipoProducto.idTipoProducto = idTipo;
    end$$
delimiter ;

delimiter $$
create procedure sp_agregarCompras (in numDoc int, in fechaDoc date, descrip varchar(60), totalDoc decimal(10,2))
begin
	insert into TipoProducto (Compras.numeroDocumento, Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento)
    values (numDoc, fechaDoc, descrip, totalDoc);
end$$
delimiter ;

delimiter $$
	create procedure sp_listarCompras ()
    begin
		select numeroDocumento, fehcaDocumento, descripcion, totalDocumento from Compras;
    end$$
delimiter ;

delimiter $$
	create procedure sp_buscarCompras(in numDoc int)
    begin
	select numeroDocumento, fechaDocumento , descripcion, totalDocumento from Compras where Compras.numeroDocumento = numDoc;
	end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarCompras(in numDoc int, in fechaDoc date,  in descrip varchar(50), totalDoc decimal(10,2))
    begin
		update Compras
		set
        TipoProducto.descripcion = descrip
        where 
        TipoProducto.idTipoProducto = idTipo;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarCompras (in numDoc int)
    begin
		delete from Compras where Compras.numeroDocumento = numDoc;
    end$$
delimiter ;

delimiter $$
create procedure sp_agregarClientes (in codCli int, in nitCli varchar(10), in nomCli varchar(50),
in apeCli varchar(50), in dire varchar(150), in tel varchar(8), in correoCli varchar(45))
begin
	insert into Clientes (Clientes.codigoCliente, Clientes.nitCliente, Clientes.nombreCliente,
    Clientes.apellidoCliente, Clientes.direccionCliente, Clientes.telefonoCliente, Clientes.correoCliente)
    values (codCli, nitCli, nomCli, apeCli, dire, tel, correoCli);
end$$
delimiter ;

call sp_agregarClientes (1, 1234567891, 'javier', 'hernandez', 'mi casa', '12345678', 'javierlapolla123');

delimiter $$
	create procedure sp_listarClientes ()
    begin
		select Clientes.codigoCliente, Clientes.nitCliente, Clientes.nombreCliente,
		Clientes.apellidoCliente, Clientes.direccionCliente, Clientes.telefonoCliente, Clientes.correoCliente  from Clientes;
    end$$
delimiter ;

call sp_listarClientes();

delimiter $$
	create procedure sp_buscarClientes(in codCli int)
    begin
	select Clientes.nitCliente, Clientes.nombreCliente, Clientes.apellidoCliente, Clientes.direccionCliente, 
    Clientes.telefonoCliente, Clientes.correoCliente from Clientes where Clientes.codigoCliente = codCli;
	end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarClientes(in codCli int, in nitCli varchar(10), in nomCli varchar(50), in apeCli varchar(50),
    dirCli varchar(150), telCli varchar(8), correoCli varchar(45))
    begin
		update Clientes 
		set
        Clientes.nitCliente = nitCli,
        Clientes.nombreCliente = nomCli,
        Clientes.apellidoCliente = apeCli,
        Clientes.direccionCliente = dirCli, 
        Clientes.correoCliente = correoCli
        where 
        Clientes.codigoCliente = codCli;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarCliente (in codCli int)
    begin
		delete from Clientes where Clientes.codigoCliente = codCli;
    end$$
delimiter ;

delimiter $$
create procedure sp_agregarCargoEmpleado (in idCargo int, in nomCargo varchar(45), in descripCargo varchar(45))
begin
	insert into CargoEmpleado (CargoEmpleado.idCargoEmpleado, CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo)
    values (idCargo, nomCargo, descripCargo);
end$$
delimiter ;

delimiter $$
	create procedure sp_listarCargoEmpleado ()
    begin
		select CargoEmpleado.idCargoEmpleado, CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo from CargoEmpleado;
    end$$
delimiter ;

delimiter $$
	create procedure sp_buscarCargoEmpleado(in idCargo int)
    begin
	select CargoEmpleado.idCargoEmpleado, CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo from CargoEmpleado where CargoEmpleado.idCargoEmpleado = idCargo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarCargoEmpleado(in idCargo int, in nomCar varchar(50), in descrip varchar(50))
    begin
		update CargoEmpleado
		set
        CargoEmpleado.nombreCargo = nomCar,
        CargoEmpleado.descripcionCargo = descrip
        where 
        CargoEmpleado.idCargoEmpleado = idCargo;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarCargoEmpleado (in idCargo int)
    begin
		delete from CargoEmpleado where CargoEmpleado.idCargoEmpleado = idCargo;
    end$$
delimiter ;

delimiter $$
create procedure sp_agregarProveedor (in codProve int, in nitProve varchar(10), in nombreProve varchar(45), in apellidoProve varchar(45), in direccionProve varchar(150)
, in razon varchar(60), in contactoPri varchar(100), in pagina varchar(50))
begin
	insert into Proveedores (Proveedores.codigoProveedor, Proveedores.nitProveedor, Proveedores.nombreProveedor, Proveedores.apellidoProveedor, Proveedores.direccionProveedor,
    Proveedores.razonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb)
    values (codProve, nitProve, nombreProve, apellidoProve, direccionProve, razon, contactoPri, pagina);
end$$
delimiter ;

delimiter $$
	create procedure sp_listarProveedores ()
    begin
		select Proveedores.codigoProveedor, Proveedores.nitProveedor, Proveedores.nombreProveedor, Proveedores.apellidoProveedor, Proveedores.direccionProveedor,
    Proveedores.razonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb from Proveedores;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarProveedor (in codP int)
    begin
		delete from Proveedores where codigoProveedor = codP;
    end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarProveedor (in codProve int, in nitProve varchar(10), in nombreProve varchar(45), in apellidoProve varchar(45), in direccionProve varchar(150)
	, in razon varchar(60), in contactoPri varchar(100), in pagina varchar(50))
	begin
		update Proveedores 
        set
        nitProveedor = nitProve,
        nombreProveedor = nombreProve,
        apellidoProveedor = apellidoProve,
        direccionProveedor = direccionProve,
        razonSocial = razon,
        contactoPrincipal = contactoPri,
        paginaWeb = pagina
        where
        codigoProveedor = codProve;
	end$$
delimiter ;