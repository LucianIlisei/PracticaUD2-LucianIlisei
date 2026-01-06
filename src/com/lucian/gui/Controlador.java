package com.lucian.gui;

import com.lucian.base.entidades.Doctor;
import com.lucian.base.entidades.Hospital;
import com.lucian.base.entidades.Medicamento;
import com.lucian.base.entidades.Paciente;
import com.lucian.conexion.Conexion;
import com.lucian.utilidades.Utilidades;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {
    private Modelo modelo;
    private Conexion conexion;
    private Vista vista;
    private Utilidades utilidades;
    private boolean refrescar;

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

        refrescarTodo();
        iniciar();
    }

    private void refrescarTodo() {
        int pestañasTotales = vista.tabbedPane1.getTabCount();
        for (int i = 0; i < pestañasTotales; i++) {
            refrescarTabla(i);
        }
        refrescar = false;
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

    void iniciar() {
        vista.pacientesTabla.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = vista.pacientesTabla.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if(e.getSource().equals(vista.pacientesTabla.getSelectionModel())) {
                        int row = vista.pacientesTabla.getSelectedRow();
                        vista.campoNombrePaciente.setText(String.valueOf(vista.pacientesTabla.getValueAt(row, 1)));
                        vista.campoPrimerApellidoPaciente.setText(String.valueOf(vista.pacientesTabla.getValueAt(row, 2)));
                        vista.campoSegundoApellidoPaciente.setText(String.valueOf(vista.pacientesTabla.getValueAt(row, 3)));

                        LocalDate fecha = Date.valueOf(String.valueOf(vista.pacientesTabla.getValueAt(row, 4))).toLocalDate();
                        vista.fechaNacimientoPacienteDatePicker.setDate(fecha);

                        String sexo = String.valueOf(vista.pacientesTabla.getValueAt(row, 5));
                        if (sexo.equals("Masculino")) {
                            vista.masculinoRadioButtonPaciente.setSelected(true);
                        } else if (sexo.equals("Femenino")) {
                            vista.femeninoRadioButtonPaciente.setSelected(true);
                        }

                        vista.campoTelefonoPaciente.setText(String.valueOf(vista.pacientesTabla.getValueAt(row, 6)));
                        vista.campoEmailPaciente.setText(String.valueOf(vista.pacientesTabla.getValueAt(row, 7)));


                        boolean fumador = Boolean.parseBoolean(String.valueOf(vista.pacientesTabla.getValueAt(row, 8)));
                        vista.siRadioButtonFumadorPaciente.setSelected(fumador);
                        vista.noRadioButtonFumadorPaciente.setSelected(!fumador);

                        int idHospitalTabla = Integer.parseInt(String.valueOf(vista.pacientesTabla.getValueAt(row, 9)));
                        for (int i = 0; i < vista.comboBoxHospitalPaciente.getItemCount(); i++) {
                            Hospital h = (Hospital) vista.comboBoxHospitalPaciente.getItemAt(i);
                            if (h.getIdHospital() == idHospitalTabla) {
                                vista.comboBoxHospitalPaciente.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                } else if (e.getValueIsAdjusting()
                        && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        borrarCamposPacientes();

                }
            }
        });

        vista.doctoresTabla.setCellSelectionEnabled(true);
        ListSelectionModel doctorSelectionModel = vista.doctoresTabla.getSelectionModel();
        doctorSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        doctorSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {

                    if (e.getSource().equals(vista.doctoresTabla.getSelectionModel())) {
                        int row = vista.doctoresTabla.getSelectedRow();

                        vista.campoNombreDoctor.setText(String.valueOf(vista.doctoresTabla.getValueAt(row, 1)));
                        vista.campoPrimerApellidoDoctor.setText(String.valueOf(vista.doctoresTabla.getValueAt(row, 2)));
                        vista.campoSegundoApellidoDoctor.setText(String.valueOf(vista.doctoresTabla.getValueAt(row, 3)));
                        vista.campoTelefonoDoctor.setText(String.valueOf(vista.doctoresTabla.getValueAt(row, 4)));
                        vista.campoEmailDoctor.setText(String.valueOf(vista.doctoresTabla.getValueAt(row, 5)));
                        vista.comboBoxEspecialidadDoctor.setSelectedItem(String.valueOf(vista.doctoresTabla.getValueAt(row, 6)));

                        LocalDate fecha = Date.valueOf(String.valueOf(vista.doctoresTabla.getValueAt(row, 7))).toLocalDate();
                        vista.fechaContratacionDatePickerDoctor.setDate(fecha);

                        int idHospitalTabla = Integer.parseInt(String.valueOf(vista.doctoresTabla.getValueAt(row, 8)));

                        for (int i = 0; i < vista.comboBoxHospitalDoctor.getItemCount(); i++) {
                            Hospital h = (Hospital) vista.comboBoxHospitalDoctor.getItemAt(i);
                            if (h.getIdHospital() == idHospitalTabla) {
                                vista.comboBoxHospitalDoctor.setSelectedIndex(i);
                                break;
                            }
                        }
                    }

                } else if (!e.getValueIsAdjusting()
                        && ((ListSelectionModel) e.getSource()).isSelectionEmpty()
                        && !refrescar) {
                    borrarCamposDoctores();
                }
            }
        });

        vista.hospitalesTabla.setCellSelectionEnabled(true);
        ListSelectionModel hospitalSelectionModel = vista.hospitalesTabla.getSelectionModel();
        hospitalSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        hospitalSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {

                    if (e.getSource().equals(vista.hospitalesTabla.getSelectionModel())) {
                        int row = vista.hospitalesTabla.getSelectedRow();

                        vista.campoNombreHospital.setText(String.valueOf(vista.hospitalesTabla.getValueAt(row, 1)));
                        vista.comboBoxProvinciaHospital.setSelectedItem(String.valueOf(vista.hospitalesTabla.getValueAt(row, 2)));
                        vista.campoTelefonoHospital.setText(String.valueOf(vista.hospitalesTabla.getValueAt(row, 3)));
                        vista.spinnerCapacidadHospital.setValue(Integer.parseInt(String.valueOf(vista.hospitalesTabla.getValueAt(row, 4))));

                        String tipo = String.valueOf(vista.hospitalesTabla.getValueAt(row, 5));
                        vista.publicoRadioButton.setSelected("Publico".equalsIgnoreCase(tipo));
                        vista.privadoRadioButton.setSelected("Privado".equalsIgnoreCase(tipo));
                    }

                } else if (!e.getValueIsAdjusting()
                        && ((ListSelectionModel) e.getSource()).isSelectionEmpty()
                        && !refrescar) {
                    borrarCamposHospitales();
                }
            }
        });

        vista.citasTabla.setCellSelectionEnabled(true);
        ListSelectionModel citaSelectionModel = vista.citasTabla.getSelectionModel();
        citaSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        citaSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {

                    if (e.getSource().equals(vista.citasTabla.getSelectionModel())) {
                        int row = vista.citasTabla.getSelectedRow();

                        int idPaciente = Integer.parseInt(String.valueOf(vista.citasTabla.getValueAt(row, 1)));
                        int idDoctor = Integer.parseInt(String.valueOf(vista.citasTabla.getValueAt(row, 2)));

                        for (int i = 0; i < vista.comboBoxPacienteCita.getItemCount(); i++) {
                            Paciente p = (Paciente) vista.comboBoxPacienteCita.getItemAt(i);
                            if (p.getIdPaciente() == idPaciente) {
                                vista.comboBoxPacienteCita.setSelectedIndex(i);
                                break;
                            }
                        }

                        for (int i = 0; i < vista.comboBoxDoctorCita.getItemCount(); i++) {
                            Doctor d = (Doctor) vista.comboBoxDoctorCita.getItemAt(i);
                            if (d.getIdDoctor() == idDoctor) {
                                vista.comboBoxDoctorCita.setSelectedIndex(i);
                                break;
                            }
                        }

                        LocalDateTime fechaHora = ((Timestamp) vista.citasTabla.getValueAt(row, 3)).toLocalDateTime();
                        vista.fechaHoraCita.setDateTimeStrict(fechaHora);
                        vista.campoMotivoCita.setText(String.valueOf(vista.citasTabla.getValueAt(row, 4)));
                        vista.campoDiagnosticoCita.setText(String.valueOf(vista.citasTabla.getValueAt(row, 5)));

                        int idMedicamento = Integer.parseInt(String.valueOf(vista.citasTabla.getValueAt(row, 6)));

                        for (int i = 0; i < vista.comboBoxMedicamentoCita.getItemCount(); i++) {
                            Medicamento m = (Medicamento) vista.comboBoxMedicamentoCita.getItemAt(i);
                            if (m.getIdMedicamento() == idMedicamento) {
                                vista.comboBoxMedicamentoCita.setSelectedIndex(i);
                                break;
                            }
                        }
                    }

                } else if (!e.getValueIsAdjusting()
                        && ((ListSelectionModel) e.getSource()).isSelectionEmpty()
                        && !refrescar) {
                    borrarCamposCitas();
                }
            }
        });

        vista.medicamentosTabla.setCellSelectionEnabled(true);
        ListSelectionModel medicamentoSelectionModel = vista.medicamentosTabla.getSelectionModel();
        medicamentoSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        medicamentoSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {

                    if (e.getSource().equals(vista.medicamentosTabla.getSelectionModel())) {
                        int row = vista.medicamentosTabla.getSelectedRow();

                        vista.campoNombreMedicamento.setText(String.valueOf(vista.medicamentosTabla.getValueAt(row, 1)));
                        vista.campoDescripción.setText(String.valueOf(vista.medicamentosTabla.getValueAt(row, 2)));
                        vista.comboBoxTipoMedicamento.setSelectedItem(String.valueOf(vista.medicamentosTabla.getValueAt(row, 3)));
                        vista.campoDosisMedicamento.setText(String.valueOf(vista.medicamentosTabla.getValueAt(row, 4)));
                        vista.campoEfectosSecundarios.setText(String.valueOf(vista.medicamentosTabla.getValueAt(row, 5)));
                    }

                } else if (!e.getValueIsAdjusting()
                        && ((ListSelectionModel) e.getSource()).isSelectionEmpty()
                        && !refrescar) {
                    borrarCamposMedicamentos();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "desconectar":
                conexion.desconectar();
                break;

            case "añadirPaciente":
                if(utilidades.campoVacio(vista.campoNombrePaciente)) {
                    utilidades.campoVacio(vista.campoNombrePaciente);
                }
        }
    }

    private void refrescarTabla(int indice) {
        try {
            switch (indice) {
                case 0:
                    vista.pacientesTabla.setModel(
                            construirTableModel(modelo.consultarPaciente())
                    );
                    break;

                case 1:
                    vista.doctoresTabla.setModel(
                            construirTableModel(modelo.consultarDoctor())
                    );
                    break;

                case 2:
                    vista.hospitalesTabla.setModel(
                            construirTableModel(modelo.consultarHospital())
                    );
                    break;

                case 3:
                    vista.citasTabla.setModel(
                            construirTableModel(modelo.consultarCita())
                    );
                    break;

                case 4:
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

    private void borrarCamposPacientes() {
        vista.campoNombrePaciente.setText("");
        vista.campoPrimerApellidoPaciente.setText("");
        vista.campoSegundoApellidoPaciente.setText("");
        vista.fechaNacimientoPacienteDatePicker.clear();
        vista.masculinoRadioButtonPaciente.setSelected(false);
        vista.femeninoRadioButtonPaciente.setSelected(false);
        vista.campoTelefonoPaciente.setText("");
        vista.campoEmailPaciente.setText("");
        vista.siRadioButtonFumadorPaciente.setSelected(false);
        vista.noRadioButtonFumadorPaciente.setSelected(false);
        vista.comboBoxHospitalPaciente.setSelectedIndex(-1);
    }

    private void borrarCamposDoctores() {
        vista.campoNombreDoctor.setText("");
        vista.campoPrimerApellidoDoctor.setText("");
        vista.campoSegundoApellidoDoctor.setText("");
        vista.campoTelefonoDoctor.setText("");
        vista.campoEmailDoctor.setText("");
        vista.comboBoxEspecialidadDoctor.setSelectedIndex(-1);
        vista.fechaContratacionDatePickerDoctor.clear();
        vista.comboBoxHospitalDoctor.setSelectedIndex(-1);
    }

    private void borrarCamposHospitales() {
        vista.campoNombreHospital.setText("");
        vista.comboBoxProvinciaHospital.setSelectedIndex(-1);
        vista.campoTelefonoHospital.setText("");
        vista.spinnerCapacidadHospital.setValue(0);
        vista.publicoRadioButton.setSelected(false);
        vista.privadoRadioButton.setSelected(false);
    }

    private void borrarCamposCitas() {
        vista.comboBoxPacienteCita.setSelectedIndex(-1);
        vista.comboBoxDoctorCita.setSelectedIndex(-1);
        vista.fechaHoraCita.clear();
        vista.campoMotivoCita.setText("");
        vista.campoDiagnosticoCita.setText("");
        vista.comboBoxMedicamentoCita.setSelectedIndex(-1);
    }

    private void borrarCamposMedicamentos() {
        vista.campoNombreMedicamento.setText("");
        vista.campoDescripción.setText("");
        vista.comboBoxTipoMedicamento.setSelectedIndex(-1);
        vista.campoDosisMedicamento.setText("");
        vista.campoEfectosSecundarios.setText("");
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
