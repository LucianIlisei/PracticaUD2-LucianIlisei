package com.lucian.main;

import com.lucian.conexion.Conexion;
import com.lucian.gui.Controlador;
import com.lucian.gui.Modelo;
import com.lucian.gui.Vista;
import com.lucian.utilidades.Utilidades;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Conexion conexion = new Conexion();
        Utilidades utilidades = new Utilidades();
        Controlador controlador = new Controlador(modelo, conexion, vista, utilidades);
    }
}
