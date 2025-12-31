CREATE DATABASE IF NOT EXISTS hospital;
--
USE hospital;
--
CREATE TABLE IF NOT EXISTS hospitales (
    id_hospital INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    provincia VARCHAR(25) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    capacidad INT NOT NULL,
    tipo VARCHAR(10) NOT NULL
);
--
CREATE TABLE IF NOT EXISTS pacientes (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    primer_apellido VARCHAR(50) NOT NULL,
    segundo_apellido VARCHAR(50),
    fecha_nacimiento DATE NOT NULL,
    sexo VARCHAR(15) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    fumador BOOLEAN NOT NULL,
    id_hospital INT NOT NULL
);
--
CREATE TABLE IF NOT EXISTS doctores (
    id_doctor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    primer_apellido VARCHAR(50) NOT NULL,
    segundo_apellido VARCHAR(50),
    telefono VARCHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    especialidad VARCHAR(20) NOT NULL,
    fecha_contratacion DATE NOT NULL,
    id_hospital INT NOT NULL
);
--
CREATE TABLE IF NOT EXISTS medicamentos (
    id_medicamento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    dosis VARCHAR(50) NOT NULL,
    efectos_secundarios VARCHAR(200) NOT NULL
);
--
CREATE TABLE IF NOT EXISTS citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_doctor INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    diagnostico VARCHAR(200) NOT NULL,
    id_medicamento INT NOT NULL
);
--
ALTER TABLE pacientes
ADD FOREIGN KEY (id_hospital) REFERENCES hospitales(id_hospital);
--
ALTER TABLE doctores
ADD FOREIGN KEY (id_hospital) REFERENCES hospitales(id_hospital);
--
ALTER TABLE citas
ADD FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente),
ADD FOREIGN KEY (id_doctor) REFERENCES doctores(id_doctor),
ADD FOREIGN KEY (id_medicamento) REFERENCES medicamentos(id_medicamento);
--
CREATE FUNCTION existeEmailPaciente (f_email VARCHAR(100))
RETURNS BIT
BEGIN
    DECLARE i INT;
    SET i = 0;
    WHILE (i < (SELECT MAX(id_paciente) FROM pacientes)) DO
        IF ((SELECT email FROM pacientes
             WHERE id_paciente = (i + 1)) LIKE f_email)
        THEN RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;
    RETURN 0;
END;
--
CREATE FUNCTION existeEmailDoctor (f_email VARCHAR(100))
RETURNS BIT
BEGIN
    DECLARE i INT;
    SET i = 0;
    WHILE (i < (SELECT MAX(id_doctor) FROM doctores)) DO
        IF ((SELECT email FROM doctores
             WHERE id_doctor = (i + 1)) LIKE f_email)
        THEN RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;
    RETURN 0;
END;
--
CREATE FUNCTION existeNombreMedicamento (f_nombreMedicamento VARCHAR(50))
RETURNS BIT
BEGIN
    DECLARE i INT;
    SET i = 0;
    WHILE (i < (SELECT MAX(id_medicamento) FROM medicamentos)) DO
        IF ((SELECT nombre FROM medicamentos
             WHERE id_medicamento = (i + 1)) LIKE f_nombreMedicamento)
        THEN RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;
    RETURN 0;
END;
--
