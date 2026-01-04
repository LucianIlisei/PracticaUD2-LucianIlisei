package com.lucian.gui;

import com.lucian.base.entidades.Paciente;
import com.lucian.conexion.Conexion;
import com.lucian.utilidades.Utilidades;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

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
        try {
            vista.pacientesTabla.setModel(
                    construirTableModel(modelo.consultarPaciente())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vista.tabbedPane1.addChangeListener(e -> {
            int index = vista.tabbedPane1.getSelectedIndex();
            refrescarTabla(index);
        });

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
        String command = e.getActionCommand();
        switch (command) {
            case "añadirPaciente":
                Paciente paciente = vista.getPacienteFormulario();
                modelo.insertarPaciente(paciente);
                break;
        }
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

    private void refrescarTabla(int indice) {
        try {
            switch (indice) {
                case 0: // Pacientes
                    vista.pacientesTabla.setModel(
                            construirTableModel(modelo.consultarPaciente())
                    );
                    break;

                case 1: // Doctores
                    vista.doctoresTabla.setModel(
                            construirTableModel(modelo.consultarDoctor())
                    );
                    break;

                case 2: // Hospitales
                    vista.hospitalesTabla.setModel(
                            construirTableModel(modelo.consultarHospital())
                    );
                    break;

                case 3: // Citas
                    vista.citasTabla.setModel(
                            construirTableModel(modelo.consultarCita())
                    );
                    break;

                case 4: // Medicamentos
                    vista.medicamentosTabla.setModel(
                            construirTableModel(modelo.consultarMedicamento())
                    );
                    break;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Nombres de columnas (usa alias AS)
        Vector<String> columnNames = new Vector<>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnLabel(column));
        }

        // Datos
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                row.add(rs.getObject(columnIndex));
            }
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames);
    }


    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }
}
