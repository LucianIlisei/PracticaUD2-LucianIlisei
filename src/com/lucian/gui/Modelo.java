package com.lucian.gui;

import com.lucian.base.entidades.Paciente;
import com.lucian.conexion.Conexion;
import com.lucian.utilidades.Utilidades;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Modelo {

    private Conexion conexion;
    public Modelo(Conexion conexion) {
        this.conexion = conexion;
    }
    void insertarPaciente(Paciente paciente) {
        String sentenciaSql = "INSERT INTO pacientes (nombre, primer_apellido, segundo_apellido, fecha_nacimiento, sexo, telefono, email, fumador, id_hospital) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, paciente.getNombre());
            sentencia.setString(2, paciente.getPrimerApellido());
            sentencia.setString(3, paciente.getSegundoApellido());
            sentencia.setDate(4, Date.valueOf(paciente.getFechaNacimiento()));
            sentencia.setString(5, paciente.getSexo());
            sentencia.setString(6, paciente.getTelefono());
            sentencia.setString(7, paciente.getEmail());
            sentencia.setBoolean(8, paciente.isFumador());
            sentencia.setInt(9, paciente.getIdHospital());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
