package com.lucian.gui;

import com.lucian.conexion.Conexion;
import com.lucian.utilidades.Utilidades;

public class Controlador {
    private Modelo modelo;
    private Conexion conexion;
    private Vista vista;
    private Utilidades utilidades;

    public Controlador(Modelo modelo, Conexion conexion, Vista vista, Utilidades utilidades){
        this.modelo = modelo;
        this.conexion = conexion;
        this.vista = vista;
        this.utilidades = utilidades;
    }
}
