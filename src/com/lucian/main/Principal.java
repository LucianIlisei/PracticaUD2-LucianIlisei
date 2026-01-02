package com.lucian.main;

import com.lucian.conexion.Conexion;
import com.lucian.gui.Controlador;
import com.lucian.gui.Modelo;
import com.lucian.gui.Vista;
import com.lucian.utilidades.Utilidades;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Modelo modelo = new Modelo(conexion);
        Vista vista = new Vista();
        Utilidades utilidades = new Utilidades();
        Controlador controlador = new Controlador(modelo, conexion, vista, utilidades);
    }
}
