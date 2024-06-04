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

create table Productos(
	codigoProducto int primary key,
    descripcionProducto varchar(45),
	precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    existencia int not null,
    idTipoProducto int not null,
    codigoProveedor int not null,
    constraint FK_idTipoProducto foreign key (idTipoProducto) references TipoProducto(idTipoProducto) on delete cascade,
    constraint FK_codigoProveedor foreign key (codigoProveedor) references Proveedores(codigoProveedor) on delete cascade
);



create table Empleados(
	idEmpleado int primary key,
	nombresEmpleado varchar(50),
	apellidosEmpleado varchar(50),
	sueldo decimal(10,2),
	direccion varchar(150),
	turno varchar(15),
	idCargoEmpleado int,
	foreign key (idCargoEmpleado) references CargoEmpleado(idCargoEmpleado) on delete cascade
);

create table Factura(
	numeroFactura int not null primary key,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int,
    idEmpleado int,
	foreign key (codigoCliente) references Clientes(codigoCliente) on delete cascade,
	foreign key (idEmpleado) references Empleados(idEmpleado) on delete cascade
);

create table DetalleFactura(
	idDetalleFa int primary key not null,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFac int,
    codigoProducto int,
    foreign key (numeroFac) references Factura(numeroFactura),
    foreign key (codigoProducto) references Productos(codigoProducto)
);

create table DetalleCompra(
	idDetalleCo int primary key not null,
    costoUnitario decimal (10,2),
    cantidadCo int,
    idProducto int,
    numDoc int,
    foreign key (idProducto) references Productos(codigoProducto),
    foreign key (numDoc) references Compras(numeroDocumento)
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
	insert into Compras (Compras.numeroDocumento, Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento)
    values (numDoc, fechaDoc, descrip, totalDoc);
end$$
delimiter ;

delimiter $$
	create procedure sp_listarCompras ()
    begin
		select numeroDocumento, fechaDocumento, descripcion, totalDocumento from Compras;
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
        descripcion = descrip
        where 
        numeroDocumento = numDoc;
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
	select Clientes.codigoCliente, Clientes.nitCliente, Clientes.nombreCliente, Clientes.apellidoCliente, Clientes.direccionCliente, 
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

delimiter $$
	create procedure sp_buscarProveedor(in codPro int)
    begin
		select Proveedores.codigoProveedor, Proveedores.nitProveedor, Proveedores.nombreProveedor, Proveedores.apellidoProveedor, Proveedores.direccionProveedor,
    Proveedores.razonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb from Proveedores 
    where codigoProveedor = codPro;
    end$$
delimiter ;

delimiter $$
	create procedure sp_agregarProducto(in codPro int, in descripPro varchar(45), in precioU decimal(10,2), in precioD decimal(10,2), in precioM decimal(10,2),
	in existenciaPro int, in idTipo int, in codCar int )
    begin
		insert into Productos (codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor,
        existencia, idTipoProducto, codigoProveedor)
        values (codPro, descripPro, precioU, precioD, precioM, existenciaPro, idTipo, codCar);
    end$$
delimiter ;


delimiter $$
create procedure sp_listarProductos ()
	begin
    select codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor,
        existencia, idTipoProducto, codigoProveedor from Productos; 
    end$$
delimiter ;

call sp_listarProductos();

delimiter $$
	create procedure sp_buscarProducto(in idPro int)
    begin
		select codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor,
        existencia, idTipoProducto, codigoProveedor from Productos where codigoProducto = idPro;
    end$$
delimiter ;

delimiter $$
create procedure sp_eliminarProducto(in codPro varchar(45))
begin 
	delete from Productos where codigoProducto = codPro;
end $$
delimiter ;

delimiter $$
	create procedure sp_actualizarProducto(in codPro int, in descripPro varchar(45), in precioU decimal(10,2), in precioDo decimal(10,2), in precioM decimal(10,2),
	in existenciaPro int, in idTipo int, in codCar int)
    begin
	update productos
    set
		descripcionProducto = descripPro,
		precioUnitario = precioU,
        precioDocena = precioDo,
        precioMayor = precioMayor,
        existencia = existenciaPro,
        idTipoProducto = idTipo,
        codigoProveedor = codCar
	where
		codigoProducto = codCar;
end $$
delimiter ;

delimiter $$
	create procedure sp_agregarEmpleado(in idE int, in nom varchar(50), in ape varchar(50), in sue decimal (10.2),
    in dir varchar(100), in tur varchar(50), in idC int)
    begin
		insert into Empleados (idEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion,
        turno, idCargoEmpleado) values (idE, nom, ape, sue, dir, tur, idC);
    end$$ 
delimiter ;

delimiter $$
	create procedure sp_listarEmpleados()
    begin 
		select idEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion,
        turno, idCargoEmpleado from Empleados;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarEmpleado(in idE int)
    begin
		delete from Empleados where idEmpleado = idE;
    end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarEmpleado(in idE int, in nom varchar(50), in ape varchar(50), in sue decimal (10.2),
    in dir varchar(100), in tur varchar(50), in idC int)
    begin
			update Empleados
    set
		nombresEmpleado = nom,
		apellidosEmpleado = ape,
        sueldo = sue,
        direccion = dir,
        turno = turno,
        idCargoEmpleado = idC
	where
		idEmpleado = idE;
    end$$
delimiter ;

delimiter $$
	create procedure sp_buscarEmpleado(in idE int)
    begin
		select idEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion,
        turno, idCargoEmpleado from Empleados where idEmpleado = idE;
    end$$
delimiter ;

delimiter $$
	create procedure sp_agregarFactura(in numfa int,  in es varchar(45), in fec varchar(45),
    in codC int, in idE int)
    begin
		insert into Factura (numeroFactura, estado, fechaFactura, codigoCliente, idEmpleado)
        values (numfa, es, fec, codC, idE);
    end$$
delimiter ;

delimiter$$
	create procedure sp_buscarFactura(in numFa int)
    begin 
		select numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, idEmpleado from Factura
        where numeroFactura = numFa
    end$$
delimiter ;

delimiter $$
	create procedure sp_listarFactura()
    begin
		select numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, idEmpleado from Factura;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarFactura(in numF int)
    begin
		delete from Factura where numeroFactura = numF;
    end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarFactura(in numfa int, in es varchar(45), in tot decimal(10,2), in fec varchar(45),
    in codC int, in idE int)
    begin
		update Factura
        set
        estado = es,
        totalFactura = tot,
        fechaFactura = fec,
        codigoCliente = codC,
        idEmpleado = idE
        where
        numeroFactura = numfa;
    end$$
delimiter ;

delimiter $$
	create procedure sp_actualizarTotalFactura(in numF int, total decimal(10,2))
    begin 
		update Factura set totalFactura = total where numeroFactura = numF;
    end $$
delimiter ;

delimiter $$
	create procedure sp_agregarDetalleFactura(in idDetalleF int, in cant int, in numF int, in codP int)
    begin
        declare preU decimal(10,2) ;
        set preU = (select preciounitario from Productos where codigoProducto = codP); 
		insert into DetalleFactura(idDetalleFa, precioUnitario, cantidad, numeroFac, codigoProducto)
        values(idDetalleF, preU, cant, numF, codP);
    end$$
delimiter ;

delimiter $$
	create procedure sp_listarDetalleFactura()
    begin
		select idDetalleFa, precioUnitario, cantidad, numeroFac, codigoProducto from DetalleFactura;
    end$$
delimiter ;

delimiter $$
	create procedure sp_buscarDetalleFactura(in idDetalleF int)
    begin
		select idDetalleFa, precioUnitario, cantidad, numeroFac, codigoProducto from DetalleFactura
        where idDetalleFa = idDetalleF;
    end$$
delimiter ;

delimiter $$
	create procedure sp_eliminarDetalleFactura(in idDetalleF int)
    begin
		delete from DetalleFactura where idDetalleFa = idDetalleF;
    end$$
delimiter ;


delimiter $$
	create procedure sp_actualizarDetalleFactura(in idDetalleF int, in precioU decimal(10,2), in cant int, in numF int, in codP int)
		begin
			update DetalleFactura
            set
				precioUnitario = precioU,
                cantidad = cant,
                numeroFac = numF,
                codigoProducto = codP
                where idDetalleFactura = idDetalleF;
        end$$
delimiter ;

delimiter $$
	create procedure sp_agregarDetalleCompra(in idDetalleC int, in costU decimal (10,2), in cantidadC int, idPro int, in numD int)
		begin 
			insert into DetalleCompra(idDetalleCo, costoUnitario, cantidadCo, idProducto, numDoc)
            values(idDetalleC, costU,  cantidadC, idPro, numD);
        end$$
delimiter ;

Delimiter $$
	create trigger tr_DetalleFacturas_After_Insert
    before insert on DetalleFactura
    for each row
    begin
        declare idP int;
        declare preU int;
        set preU = (select precioUnitario from Productos where codigoProducto = idP);
        
    end $$
Delimiter ;

Delimiter $$
	create trigger tr_actualizaprecios_After_Insert
    after insert on DetalleCompra
    for each row
    begin
        declare CodA int;
        declare CostoA decimal (10,2);
        declare CantiA int;
        set CodA = new.idProducto;
        set CostoA = new.costoUnitario;
        set Cantia = new.cantidadCo;
        update productos set precioUnitario = CostoA * 1.40,
        precioDocena = CostoA * 1.35, PrecioMayor = CostoA * 1.25, existencia = existencia + Cantia
        where codigoProducto = CodA;
        
    end $$
Delimiter ;

Delimiter $$
	create trigger tr_actualizaCompra_After_Insert
    after insert on DetalleCompra
    for each row 
    begin 
        declare NumC int;
        declare CostoA decimal (10,2);
        declare TotalC decimal (10,2);
        declare Cantia int;
        set Numc = new.numDoc;
        set CostoA = new.costoUnitario;
        set Cantia = new.cantidadCo;
        set TotalC = CostoA * Cantia;
        update Compras set totalDocumento = totalDocumento + totalC where numeroDocumento = NumC;
    end $$
Delimiter ;

Delimiter $$
	create trigger tr_actualizarFactura_After_Insert
    after insert on DetalleFactura
    for each row 
    begin 
        declare idFac int;
        declare precio decimal (10,2);
        declare Can decimal(10,2);
        declare total decimal(10,2);
        set idFac = new.numeroFac;
        set precio = new.precioUnitario;
        set Can = new.cantidad;
        set total = precio * Can;
        update Factura set fechaFactura = "fecha1" where numeroFactura = 1;
        update Factura set totalFactura = precio  where numeroFactura = idFac;
    end $$
Delimiter ;

drop trigger tr_actualizarFactura_After_Insert;

call sp_agregarDetalleFactura(2,10, 1, 1);
select * from Productos;
select* from TipoProducto;
select * from Compras;
call sp_eliminarDetalleFactura(2);
select * from DetalleCompra;
select* from DetalleFactura;

select * from Factura;
select * from Clientes;