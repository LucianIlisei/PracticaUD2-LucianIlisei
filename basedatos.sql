CREATE DATABASE if NOT EXISTS hospital;
--
USE hospital;
--
CREATE TABLE if NOT EXISTS hospitales(
id_hospital int auto_increment primary key,
nombre varchar(50) not null,
ciudad varchar(25) not null,
telefono varchar(9) not null,
capacidad int not null,
tipo varchar(10) not null);
--
CREATE TABLE if NOT EXISTS pacientes (
id_paciente int auto_increment primary key,
nombre varchar(50) not null,
primer_apellido varchar(50) not null,
segundo_apellido varchar(50),
fecha_nacimiento date not null,
sexo varchar(15) not null,
telefono varchar(9) not null,
email varchar(100) not null UNIQUE,
fumador BOOLEAN not null,
id_hospital int not null);
--
CREATE TABLE if NOT EXISTS doctores (
id_doctor int auto_increment primary key,
nombre varchar(50) not null,
primer_apellido varchar(50) not null,
segundo_apellido varchar(50),
telefono varchar(9) not null,
email varchar(100) not null UNIQUE,
especialidad varchar(20) not null,
fecha_contratacion date not null,
id_hospital int not null);
--
CREATE TABLE if NOT EXISTS medicamentos(
id_medicamento int auto_increment primary key,
nombre varchar(50) not null UNIQUE,
descripcion varchar(200) not null,
tipo varchar(20) not null,
dosis varchar (50) not null,
efectos_secundarios varchar(200) not null);
CREATE TABLE if NOT EXISTS citas(
id_cita int auto_increment primary key,
id_paciente int not null,
id_doctor int not null,
fecha_hora DATETIME not null,
motivo varchar(200) not null,
diagnostico varchar(200) not null,
id_medicamento int not null);
--
alter table pacientes
add foreign key (id_hospital) references hospitales(id_hospital);
--
alter table doctores
add foreign key (id_hospital) references hospitales(id_hospital);
--
alter table citas
add foreign key (id_paciente) references pacientes(id_paciente),
add foreign key (id_doctor) references doctores(id_doctor),
add foreign key (id_medicamento) references medicamentos(id_medicamento);
--
delimiter ||
create function existeEmailPaciente (f_email varchar(100))
returns bit
begin
    declare i int;
    set i=0;
    while (i <(select max(id_paciente) from pacientes)) do
    if ((select email from pacientes where id_paciente=(i+1)) like f_email)
    then return 1;
    end if;
    set i = i + 1;
    end while;
    return 0;
end;
|| delimiter ;
--
delimiter ||
create function existeEmailDoctor (f_email varchar(100))
returns bit
begin
    declare i int;
    set i=0;
    while (i <(select max(id_doctor) from doctores)) do
    if ((select email from doctores where id_doctor=(i+1)) like f_email)
    then return 1;
    end if;
    set i = i + 1;
    end while;
    return 0;
end;
|| delimiter ;
--
delimiter ||
create function existeNombreMedicamento (f_nombreMedicamento varchar(50))
returns bit
begin
    declare i int;
    set i=0;
    while (i <(select max(id_medicamento) from medicamentos)) do
    if ((select nombre from medicamentos where id_medicamento=(i+1)) like f_nombreMedicamento)
    then return 1;
    end if;
    set i = i + 1;
    end while;
    return 0;
end;
|| delimiter ;