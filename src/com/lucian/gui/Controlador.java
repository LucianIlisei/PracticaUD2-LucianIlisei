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

        vista.tabbedPane1.addChangeListener(e -> {
            int index = vista.tabbedPane1.getSelectedIndex();
            refrescarTabla(index);
        });

        refrescarTodo();
        cargarCombos();
        iniciar();
    }

    private void refrescarTodo() {
        int pestañasTotales = vista.tabbedPane1.getTabCount();
        for (int i = 0; i < pestañasTotales; i++) {
            refrescarTabla(i);
        }
        refrescar = false;
    }

    public void cargarCombos() {
        try {
            vista.cargarHospitalesCombo(modelo.consultarHospitalesCombo());
            vista.cargarPacientesCombo(modelo.consultarPacientesCombo());
            vista.cargarDoctoresCombo(modelo.consultarDoctoresCombo());
            vista.cargarMedicamentosCombo(modelo.consultarMedicamentosCombo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            vista.pacientesTabla.setModel(
                    construirTableModel(modelo.consultarPaciente())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        int confirmacion = 0;
        switch (command) {
            case "desconectar":
                conexion.desconectar();
                break;
            case "añadirPaciente":
                if(utilidades.hayFilaseleccionada(vista.pacientesTabla)) {
                    utilidades.hayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombrePaciente)) {
                    utilidades.campoVacioAlerta(vista.campoNombrePaciente);
                    break;
                } else if (utilidades.campoVacio(vista.campoPrimerApellidoPaciente)) {
                    utilidades.campoVacioAlerta(vista.campoPrimerApellidoPaciente);
                    break;
                } else if(utilidades.fechaVacia(vista.fechaNacimientoPacienteDatePicker)) {
                    utilidades.fechaVaciaAlerta();
                    break;
                } else if(!utilidades.fechaAnteriorAHoy(vista.fechaNacimientoPacienteDatePicker)) {
                    utilidades.fechaAnteriorHoy();
                    break;
                } else if (!utilidades.telefonoValido(vista.campoTelefonoPaciente)) {
                    utilidades.telefonoErrorAlerta();
                    break;
                } else if (!utilidades.emailValido(vista.campoEmailPaciente)) {
                    utilidades.emailErrorAlerta();
                    break;
                } else if (utilidades.errorSelecioneCombo(vista.comboBoxHospitalPaciente)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxHospitalPaciente);
                    break;
                } else {
                    Paciente paciente = vista.getPacienteFormulario();
                    modelo.insertarPaciente(paciente);
                    refrescarTabla(0);
                    borrarCamposPacientes();
                    cargarCombos();
                }
                break;
            case "modificarPaciente":
                if (utilidades.noHayFilaSeleccionada(vista.pacientesTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombrePaciente)) {
                    utilidades.campoVacioAlerta(vista.campoNombrePaciente);
                    break;
                } else if (utilidades.campoVacio(vista.campoPrimerApellidoPaciente)) {
                    utilidades.campoVacioAlerta(vista.campoPrimerApellidoPaciente);
                    break;
                } else if(utilidades.fechaVacia(vista.fechaNacimientoPacienteDatePicker)) {
                    utilidades.fechaVaciaAlerta();
                    break;
                } else if(!utilidades.fechaAnteriorAHoy(vista.fechaNacimientoPacienteDatePicker)) {
                    utilidades.fechaAnteriorHoy();
                    break;
                } else if (!utilidades.telefonoValido(vista.campoTelefonoPaciente)) {
                    utilidades.telefonoErrorAlerta();
                    break;
                } else if (!utilidades.emailValido(vista.campoEmailPaciente)) {
                    utilidades.emailErrorAlerta();
                    break;
                } else if (utilidades.errorSelecioneCombo(vista.comboBoxHospitalPaciente)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxHospitalPaciente);
                    break;
                } else {
                    int fila = vista.pacientesTabla.getSelectedRow();
                    int idPaciente = Integer.parseInt(vista.pacientesTabla.getValueAt(fila, 0).toString());

                    Paciente paciente = vista.getPacienteFormulario(idPaciente);
                    modelo.modificarPaciente(paciente);

                    refrescarTabla(0);
                    borrarCamposPacientes();
                    cargarCombos();
                }
                break;
            case "eliminarPaciente":
                if (utilidades.noHayFilaSeleccionada(vista.pacientesTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                confirmacion = utilidades.eliminarConfirmacion();
                if (confirmacion != JOptionPane.YES_OPTION) {
                    break;
                }
                int fila = vista.pacientesTabla.getSelectedRow();
                int idPaciente = Integer.parseInt(vista.pacientesTabla.getValueAt(fila, 0).toString());

                modelo.eliminarPaciente(idPaciente);

                refrescarTabla(0);
                borrarCamposPacientes();
                cargarCombos();

                break;
            case "añadirDoctor":
                if(utilidades.hayFilaseleccionada(vista.doctoresTabla)) {
                    utilidades.hayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombreDoctor)) {
                    utilidades.campoVacioAlerta(vista.campoNombreDoctor);
                    break;
                } else if (utilidades.campoVacio(vista.campoPrimerApellidoDoctor)) {
                    utilidades.campoVacioAlerta(vista.campoPrimerApellidoDoctor);
                    break;
                } else if (!utilidades.telefonoValido(vista.campoTelefonoDoctor)) {
                    utilidades.telefonoErrorAlerta();
                    break;
                } else if (!utilidades.emailValido(vista.campoEmailDoctor)) {
                    utilidades.emailErrorAlerta();
                    break;
                } else if (utilidades.errorSelecioneCombo(vista.comboBoxEspecialidadDoctor)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxEspecialidadDoctor);
                    break;
                } else if(utilidades.fechaVacia(vista.fechaContratacionDatePickerDoctor)) {
                    utilidades.fechaVaciaAlerta();
                    break;
                } else if(!utilidades.fechaAnteriorAHoy(vista.fechaContratacionDatePickerDoctor)) {
                    utilidades.fechaAnteriorHoy();
                    break;
                }  else {
                    Doctor doctor = vista.getDoctorFormulario();
                    modelo.insertarDoctor(doctor);
                    refrescarTabla(1);
                    borrarCamposDoctores();
                    cargarCombos();
                }
                break;
            case "modificarDoctor":
                if (utilidades.noHayFilaSeleccionada(vista.doctoresTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombreDoctor)) {
                    utilidades.campoVacioAlerta(vista.campoNombreDoctor);
                    break;
                } else if (utilidades.campoVacio(vista.campoPrimerApellidoDoctor)) {
                    utilidades.campoVacioAlerta(vista.campoPrimerApellidoDoctor);
                    break;
                } else if (!utilidades.telefonoValido(vista.campoTelefonoDoctor)) {
                    utilidades.telefonoErrorAlerta();
                    break;
                } else if (!utilidades.emailValido(vista.campoEmailDoctor)) {
                    utilidades.emailErrorAlerta();
                    break;
                } else if (utilidades.errorSelecioneCombo(vista.comboBoxEspecialidadDoctor)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxEspecialidadDoctor);
                    break;
                } else if(utilidades.fechaVacia(vista.fechaContratacionDatePickerDoctor)) {
                    utilidades.fechaVaciaAlerta();
                    break;
                } else if(!utilidades.fechaAnteriorAHoy(vista.fechaContratacionDatePickerDoctor)) {
                    utilidades.fechaAnteriorHoy();
                    break;
                } else {
                    int filaDoctor = vista.doctoresTabla.getSelectedRow();
                    int idDoctor = Integer.parseInt(vista.doctoresTabla.getValueAt(filaDoctor, 0).toString());
                    Doctor doctor = vista.getDoctorFormulario(idDoctor);
                    modelo.modificarDoctor(doctor);

                    refrescarTabla(1);
                    borrarCamposDoctores();
                    cargarCombos();
                }
                break;
            case "eliminarDoctor":

                if (utilidades.noHayFilaSeleccionada(vista.doctoresTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                confirmacion = utilidades.eliminarConfirmacion();
                if (confirmacion != JOptionPane.YES_OPTION) {
                    break;
                }
                int filaDoctorEliminar = vista.doctoresTabla.getSelectedRow();
                int idDoctorEliminar = Integer.parseInt(vista.doctoresTabla.getValueAt(filaDoctorEliminar, 0).toString());
                modelo.eliminarDoctor(idDoctorEliminar);

                refrescarTabla(1);
                borrarCamposDoctores();
                cargarCombos();

                break;
            case "añadirHospital":
                if(utilidades.hayFilaseleccionada(vista.hospitalesTabla)) {
                    utilidades.hayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombreHospital)) {
                    utilidades.campoVacioAlerta(vista.campoNombreHospital);
                    break;
                } else if(utilidades.errorSelecioneCombo(vista.comboBoxProvinciaHospital)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxProvinciaHospital);
                    break;
                } else if (!utilidades.telefonoValido(vista.campoTelefonoHospital)) {
                    utilidades.telefonoErrorAlerta();
                    break;
                } else {
                    Hospital hospital = vista.getHospitalFormulario();
                    modelo.insertarHospital(hospital);

                    refrescarTabla(2);
                    borrarCamposHospitales();
                    cargarCombos();
                }
                break;
            case "modificarHospital":
                if (utilidades.noHayFilaSeleccionada(vista.hospitalesTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombreHospital)) {
                    utilidades.campoVacioAlerta(vista.campoNombreHospital);
                    break;
                } else if(utilidades.errorSelecioneCombo(vista.comboBoxProvinciaHospital)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxProvinciaHospital);
                    break;
                } else if (!utilidades.telefonoValido(vista.campoTelefonoHospital)) {
                    utilidades.telefonoErrorAlerta();
                    break;
                } else {
                    int filaHospital = vista.hospitalesTabla.getSelectedRow();
                    int idHospital = Integer.parseInt(vista.hospitalesTabla.getValueAt(filaHospital, 0).toString());
                    Hospital hospital = vista.getHospitalFormulario(idHospital);
                    modelo.modificarHospital(hospital);

                    refrescarTabla(2);
                    borrarCamposHospitales();
                    cargarCombos();
                }
                break;
            case "eliminarHospital":
                if (utilidades.noHayFilaSeleccionada(vista.hospitalesTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                confirmacion = utilidades.eliminarConfirmacion();
                if (confirmacion != JOptionPane.YES_OPTION) {
                    break;
                }
                int filaHospitalEliminar = vista.hospitalesTabla.getSelectedRow();
                int idHospitalEliminar = Integer.parseInt(vista.hospitalesTabla.getValueAt(filaHospitalEliminar, 0).toString());

                modelo.eliminarHospital(idHospitalEliminar);

                refrescarTabla(2);
                borrarCamposHospitales();
                cargarCombos();

                break;
            case "añadirCita":
                if(utilidades.hayFilaseleccionada(vista.citasTabla)) {
                    utilidades.hayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoMotivoCita)) {
                    utilidades.campoVacioAlerta(vista.campoMotivoCita);
                    break;
                } else if (utilidades.campoVacio(vista.campoDiagnosticoCita)) {
                    utilidades.campoVacioAlerta(vista.campoDiagnosticoCita);
                    break;
                } else {
                    modelo.insertarCita(vista.getCitaFormulario());
                    refrescarTabla(3);
                    borrarCamposCitas();
                }
                break;
            case "modificarCita":
                if (utilidades.noHayFilaSeleccionada(vista.citasTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }

                if (utilidades.campoVacio(vista.campoMotivoCita)) {
                    utilidades.campoVacioAlerta(vista.campoMotivoCita);
                    break;
                } else if (utilidades.campoVacio(vista.campoDiagnosticoCita)) {
                    utilidades.campoVacioAlerta(vista.campoDiagnosticoCita);
                    break;
                } else {
                    int filaCita = vista.citasTabla.getSelectedRow();
                    int idCita = Integer.parseInt(vista.citasTabla.getValueAt(filaCita, 0).toString());
                    modelo.modificarCita(vista.getCitaFormulario(idCita));

                    refrescarTabla(3);
                    borrarCamposCitas();
                }
                break;
            case "eliminarCita":
                if (utilidades.noHayFilaSeleccionada(vista.citasTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                confirmacion = utilidades.eliminarConfirmacion();
                if (confirmacion != JOptionPane.YES_OPTION) {
                    break;
                }
                int filaCitaEliminar = vista.citasTabla.getSelectedRow();
                int idCitaEliminar = Integer.parseInt(vista.citasTabla.getValueAt(filaCitaEliminar, 0).toString());
                modelo.eliminarCita(idCitaEliminar);

                refrescarTabla(3);
                borrarCamposCitas();
                break;
            case "añadirMedicamento":
                if(utilidades.hayFilaseleccionada(vista.medicamentosTabla)) {
                    utilidades.hayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombreMedicamento)) {
                    utilidades.campoVacioAlerta(vista.campoNombreMedicamento);
                    break;
                } else if(utilidades.campoVacio(vista.campoDescripción)) {
                    utilidades.campoVacioAlerta(vista.campoDescripción);
                    break;
                } else if (utilidades.errorSelecioneCombo(vista.comboBoxTipoMedicamento)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxTipoMedicamento);
                    break;
                } else if (utilidades.campoVacio(vista.campoEfectosSecundarios)) {
                    utilidades.campoVacioAlerta(vista.campoEfectosSecundarios);
                    break;
                } else {
                    modelo.insertarMedicamento(vista.getMedicamentoFormulario());

                    refrescarTabla(4);
                    borrarCamposMedicamentos();
                }
                break;
            case "modificarMedicamento":
                if (utilidades.noHayFilaSeleccionada(vista.medicamentosTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                if (utilidades.campoVacio(vista.campoNombreMedicamento)) {
                    utilidades.campoVacioAlerta(vista.campoNombreMedicamento);
                    break;
                } else if(utilidades.campoVacio(vista.campoDescripción)) {
                    utilidades.campoVacioAlerta(vista.campoDescripción);
                    break;
                } else if (utilidades.errorSelecioneCombo(vista.comboBoxTipoMedicamento)) {
                    utilidades.errorSeleccioneAlertaCombo(vista.comboBoxTipoMedicamento);
                    break;
                } else if (utilidades.campoVacio(vista.campoEfectosSecundarios)) {
                    utilidades.campoVacioAlerta(vista.campoEfectosSecundarios);
                    break;
                } else {
                    int filaMed = vista.medicamentosTabla.getSelectedRow();
                    int idMed = Integer.parseInt(vista.medicamentosTabla.getValueAt(filaMed, 0).toString());
                    modelo.modificarMedicamento(vista.getMedicamentoFormulario(idMed));

                    refrescarTabla(4);
                    borrarCamposMedicamentos();
                }
                break;
            case "eliminarMedicamento":
                if (utilidades.noHayFilaSeleccionada(vista.medicamentosTabla)) {
                    utilidades.noHayFilaSeleccionadaAlerta();
                    break;
                }
                confirmacion = utilidades.eliminarConfirmacion();
                if (confirmacion != JOptionPane.YES_OPTION) {
                    break;
                }
                int filaMedEliminar = vista.medicamentosTabla.getSelectedRow();
                int idMedEliminar = Integer.parseInt(
                        vista.medicamentosTabla.getValueAt(filaMedEliminar, 0).toString()
                );
                modelo.eliminarMedicamento(idMedEliminar);

                refrescarTabla(4);
                borrarCamposMedicamentos();
                break;
        }
    }

    private void refrescarTabla(int indice) {
        try {
            switch (indice) {
                case 0:
                    vista.pacientesTabla.setModel(construirTableModel(modelo.consultarPaciente()));
                    break;
                case 1:
                    vista.doctoresTabla.setModel(construirTableModel(modelo.consultarDoctor()));
                    break;
                case 2:
                    vista.hospitalesTabla.setModel(construirTableModel(modelo.consultarHospital()));
                    break;
                case 3:
                    vista.fechaHoraCita.setDateTimeStrict(LocalDateTime.now());
                    vista.citasTabla.setModel(construirTableModel(modelo.consultarCita()));
                    break;
                case 4:
                    vista.medicamentosTabla.setModel(construirTableModel(modelo.consultarMedicamento()));
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
        vista.comboBoxHospitalPaciente.setSelectedIndex(0);
    }

    private void borrarCamposDoctores() {
        vista.campoNombreDoctor.setText("");
        vista.campoPrimerApellidoDoctor.setText("");
        vista.campoSegundoApellidoDoctor.setText("");
        vista.campoTelefonoDoctor.setText("");
        vista.campoEmailDoctor.setText("");
        vista.comboBoxEspecialidadDoctor.setSelectedIndex(0);
        vista.fechaContratacionDatePickerDoctor.clear();
        vista.comboBoxHospitalDoctor.setSelectedIndex(-1);
    }

    private void borrarCamposHospitales() {
        vista.campoNombreHospital.setText("");
        vista.comboBoxProvinciaHospital.setSelectedIndex(0);
        vista.campoTelefonoHospital.setText("");
        vista.spinnerCapacidadHospital.setValue(0);
        vista.publicoRadioButton.setSelected(false);
        vista.privadoRadioButton.setSelected(false);
    }

    private void borrarCamposCitas() {
        vista.comboBoxPacienteCita.setSelectedIndex(-1);
        vista.comboBoxDoctorCita.setSelectedIndex(-1);
        vista.fechaHoraCita.setDateTimeStrict(LocalDateTime.now());
        vista.campoMotivoCita.setText("");
        vista.campoDiagnosticoCita.setText("");
        vista.comboBoxMedicamentoCita.setSelectedIndex(-1);
    }

    private void borrarCamposMedicamentos() {
        vista.campoNombreMedicamento.setText("");
        vista.campoDescripción.setText("");
        vista.comboBoxTipoMedicamento.setSelectedIndex(0);
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
