package com.lucian.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;

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
    JButton bntAñadirPaciente;
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
    JComboBox comboBoxCiudadHospital;
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
    }
}
