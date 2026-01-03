package com.lucian.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.lucian.base.entidades.Doctor;
import com.lucian.base.entidades.Hospital;
import com.lucian.base.entidades.Paciente;
import com.lucian.base.enums.CiudadesHospital;
import com.lucian.base.enums.EspecialidadDoctor;
import com.lucian.base.enums.TipoMedicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class Vista extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private final static String TITULO_FRAME="Aplicacion hospital";

    // Paciente
    JPanel PanelMedicamento;
    JPanel PanelDoctor;
    JPanel PanelHospital;
    JPanel PanelCita;
    JPanel PanelPaciente;
    JTextField campoNombrePaciente;
    JTextField campoPrimerApellidoPaciente;
    JTextField campoSegundoApellidoPaciente;
    JRadioButton masculinoRadioButtonPaciente;
    JRadioButton femeninoRadioButtonPaciente;
    JTextField campoTelefonoPaciente;
    JTextField campoEmailPaciente;
    JRadioButton siRadioButtonFumadorPaciente;
    JRadioButton noRadioButtonFumadorPaciente;
    JComboBox comboBoxHospitalPaciente;
    JButton btnAñadirPaciente;
    JButton btnModificarPaciente;
    JButton btnEliminarPaciente;
    DatePicker fechaNacimientoPacienteDatePicker;
    JTable pacientesTabla;

    // Doctor
    JTextField campoNombreDoctor;
    JTextField campoPrimerApellidoDoctor;
    JTextField campoSegundoApellidoDoctor;
    JTextField campoTelefonoDoctor;
    JTextField campoEmailDoctor;
    JComboBox comboBoxEspecialidadDoctor;
    JComboBox comboBoxHospitalDoctor;
    JButton btnAñadirDoctor;
    JButton btnModificarDoctor;
    JButton btnEliminarDoctor;
    DatePicker fechaContratacionDatePickerDoctor;
    JTable doctoresTabla;

    // Hospital
    JTextField campoNombreHospital;
    JComboBox comboBoxProvinciaHospital;
    JTextField campoTelefonoHospital;
    JSpinner spinnerCapacidadHospital;
    JRadioButton públicoRadioButton;
    JRadioButton privadoRadioButton;
    JButton btnAñadirHospital;
    JButton btnModificarHospital;
    JButton btnEliminarHospital;
    JTable hospitalesTabla;

    // Cita
    JComboBox comboBoxPacienteCita;
    JComboBox comboBoxDoctorCita;
    JTextField campoMotivoCita;
    JComboBox comboBoxMedicamentoCita;
    JButton btnAñadirCita;
    JButton btnModificarCita;
    JButton btnEliminarCita;
    JTable citasTabla;

    // Medicamento
    JTextField campoNombreMedicamento;
    DateTimePicker fechaHoraCita;
    JTextArea campoDiagnosticoCita;
    JTextField textField1;
    JComboBox comboBoxTipoMedicamento;
    JTextField campoDosisMedicamento;
    JTextArea campoEfectosSecundarios;
    JButton btnAñadirMedicamento;
    JButton btnModificarMedicamento;
    JButton btnElimiarMedicamento;
    JTable medicamentosTabla;

    // Tablas
    DefaultTableModel dtmPacientes;
    DefaultTableModel dtmDoctores;
    DefaultTableModel dtmHospitales;
    DefaultTableModel dtmCitas;
    DefaultTableModel dtmMedicamentos;

    public Vista() {
        super(TITULO_FRAME);
        initFrame();
    }

    public void initFrame() {
        this.setContentPane(panel1);
        //al clickar en cerrar no hace nada
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth()+100,this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        setEnumComboBox();
    }

    private void setEnumComboBox() {
        for (EspecialidadDoctor constant : EspecialidadDoctor.values()) {
            comboBoxEspecialidadDoctor.addItem(constant.getValor());
        }

        for (CiudadesHospital constant: CiudadesHospital.values()) {
            comboBoxProvinciaHospital.addItem(constant.getValor());
        }

        for (TipoMedicamento constant : TipoMedicamento.values()) {
            comboBoxTipoMedicamento.addItem(constant.getValor());
        }
    }

    private void setTableModels() {
        this.dtmPacientes = new DefaultTableModel();
        this.pacientesTabla.setModel(dtmPacientes);

        this.dtmDoctores = new DefaultTableModel();
        this.doctoresTabla.setModel(dtmDoctores);

        this.dtmHospitales = new DefaultTableModel();
        this.hospitalesTabla.setModel(dtmHospitales);

        this.dtmCitas = new DefaultTableModel();
        this.citasTabla.setModel(dtmCitas);

        this.dtmMedicamentos = new DefaultTableModel();
        this.medicamentosTabla.setModel(dtmMedicamentos);
    }

    public Paciente getPacienteFormulario() {
        Paciente paciente = new Paciente();

        paciente.setNombre(campoNombreDoctor.getText());
        paciente.setPrimerApellido(campoPrimerApellidoPaciente.getText());
        paciente.setSegundoApellido(campoSegundoApellidoPaciente.getText());
        paciente.setFechaNacimiento(fechaNacimientoPacienteDatePicker.getDate());
        if (masculinoRadioButtonPaciente.isSelected()) {
            paciente.setSexo("Masculino");
        } else if (femeninoRadioButtonPaciente.isSelected()) {
            paciente.setSexo("Femenino");
        }
        paciente.setTelefono(campoTelefonoPaciente.getText());
        paciente.setEmail(campoEmailPaciente.getText());
        paciente.setFumador(siRadioButtonFumadorPaciente.isSelected());
        Hospital h = (Hospital) comboBoxHospitalPaciente.getSelectedItem();
        int id = h.getIdHospital();
        paciente.setIdHospital(id);
        return paciente;
    }

    public Paciente getPacienteFormulario(int idPaciente) {
        Paciente paciente = new Paciente();

        paciente.setIdPaciente(idPaciente);
        paciente.setNombre(campoNombrePaciente.getText());
        paciente.setPrimerApellido(campoPrimerApellidoPaciente.getText());
        paciente.setSegundoApellido(campoSegundoApellidoPaciente.getText());
        paciente.setFechaNacimiento(fechaNacimientoPacienteDatePicker.getDate());
        if (masculinoRadioButtonPaciente.isSelected()) {
            paciente.setSexo("Masculino");
        } else if (femeninoRadioButtonPaciente.isSelected()) {
            paciente.setSexo("Femenino");
        }
        paciente.setTelefono(campoTelefonoPaciente.getText());
        paciente.setEmail(campoEmailPaciente.getText());
        paciente.setFumador(siRadioButtonFumadorPaciente.isSelected());
        Hospital h = (Hospital) comboBoxHospitalPaciente.getSelectedItem();
        paciente.setIdHospital(h.getIdHospital());

        return paciente;
    }

    public Doctor getDoctorFormulario() {
        Doctor doctor = new Doctor();

        doctor.setNombre(campoNombreDoctor.getText());
        doctor.setPrimerApellido(campoPrimerApellidoDoctor.getText());
        doctor.setSegundoApellido(campoSegundoApellidoDoctor.getText());
        doctor.setTelefono(campoTelefonoDoctor.getText());
        doctor.setEmail(campoEmailDoctor.getText());
        doctor.setEspecialidad(comboBoxEspecialidadDoctor.getSelectedItem().toString());
        doctor.setFechaContratacion(fechaContratacionDatePickerDoctor.getDate());
        Hospital h = (Hospital) comboBoxHospitalPaciente.getSelectedItem();
        int id = h.getIdHospital();
        doctor.setIdHospital(id);

        return doctor;
    }
}
