package com.lucian.gui;

import com.lucian.conexion.Conexion;
import com.lucian.utilidades.Utilidades;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {
    private Modelo modelo;
    private Conexion conexion;
    private Vista vista;
    private Utilidades utilidades;

    public Controlador(Modelo modelo, Conexion conexion, Vista vista, Utilidades utilidades){
        this.modelo = modelo;
        this.conexion = conexion;
        this.vista = vista;
        this.utilidades = utilidades;
        conexion.conectar();
        addActionListeners(this);
    }

    private void addActionListeners(ActionListener listener) {

        vista.btnAñadirPaciente.addActionListener(listener);
        vista.btnAñadirPaciente.setActionCommand("añadirPaciente");
        vista.btnModificarPaciente.addActionListener(listener);
        vista.btnModificarPaciente.setActionCommand("modificarPaciente");
        vista.btnEliminarPaciente.addActionListener(listener);
        vista.btnEliminarPaciente.setActionCommand("eliminarPaciente");

        vista.btnAñadirDoctor.addActionListener(listener);
        vista.btnAñadirDoctor.setActionCommand("añadirDoctor");
        vista.btnModificarDoctor.addActionListener(listener);
        vista.btnModificarDoctor.setActionCommand("modificarDoctor");
        vista.btnEliminarDoctor.addActionListener(listener);
        vista.btnEliminarDoctor.setActionCommand("eliminarDoctor");

        vista.btnAñadirHospital.addActionListener(listener);
        vista.btnAñadirHospital.setActionCommand("añadirHospital");
        vista.btnModificarHospital.addActionListener(listener);
        vista.btnModificarHospital.setActionCommand("modificarHospital");
        vista.btnEliminarHospital.addActionListener(listener);
        vista.btnEliminarHospital.setActionCommand("eliminarHospital");

        vista.btnAñadirCita.addActionListener(listener);
        vista.btnAñadirCita.setActionCommand("añadirCita");
        vista.btnModificarCita.addActionListener(listener);
        vista.btnModificarCita.setActionCommand("modificarCita");
        vista.btnEliminarCita.addActionListener(listener);
        vista.btnEliminarCita.setActionCommand("eliminarCita");

        vista.btnAñadirMedicamento.addActionListener(listener);
        vista.btnAñadirMedicamento.setActionCommand("añadirMedicamento");
        vista.btnModificarMedicamento.addActionListener(listener);
        vista.btnModificarMedicamento.setActionCommand("modificarMedicamento");
        vista.btnElimiarMedicamento.addActionListener(listener);
        vista.btnElimiarMedicamento.setActionCommand("eliminarMedicamento");
    }

    private void addWindowListeners(WindowListener listener) { vista.addWindowListener(listener); }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
