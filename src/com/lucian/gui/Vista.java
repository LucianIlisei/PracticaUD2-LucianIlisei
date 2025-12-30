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
    private DatePicker fechaNacimientoPacienteDatePicker;
    private JTable pacientesTabla;
    private JTextField campoNombreDoctor;
    private JTextField campoPrimerApellidoDoctor;
    private JTextField campoSegundoApellidoDoctor;
    private JTextField campoTelefonoDoctor;
    private JTextField campoEmailDoctor;
    private JComboBox comboBoxEspecialidadDoctor;
    private JComboBox comboBoxHospitalDoctor;
    private JButton btnAñadirDoctor;
    private JButton btnModificarDoctor;
    private JButton btnEliminarDoctor;
    private DatePicker fechaContratacionDatePickerDoctor;
    private JTable doctoresTabla;
    private JTextField campoNombreHospital;
    private JComboBox comboBoxCiudadHospital;
    private JTextField campoTelefonoHospital;
    private JSpinner spinnerCapacidadHospital;
    private JRadioButton públicoRadioButton;
    private JRadioButton privadoRadioButton;
    private JButton btnAñadirHospital;
    private JButton btnModificarHospital;
    private JButton btnEliminarHospital;
    private JTable hospitalesTabla;
    private JComboBox comboBoxPacienteCita;
    private JComboBox comboBoxDoctorCita;
    private JTextField campoMotivoCita;
    private JComboBox comboBoxMedicamentoCita;
    private JButton btnAñadirCita;
    private JButton btnModificarCita;
    private JButton btnEliminarCita;
    private JTable table1;
    private JTextField campoNombreMedicamento;
    private DateTimePicker fechaHoraCita;
    private JTextArea campoDiagnosticoCita;
    private JScrollPane citasTabla;
    private JTextField textField1;
    private JComboBox comboBoxTipoMedicamento;
    private JTextField campoDosisMedicamento;
    private JTextArea campoEfectosSecundarios;
    private JButton btnAñadirMedicamento;
    private JButton btnModificarMedicamento;
    private JButton btnElimiarMedicamento;
    private JTable table2;
    private JScrollPane medicamentosTabla;

    public Vista() {
        super(TITULO_FRAME);
        initFrame();
    }

    public void initFrame() {
        this.setContentPane(panel1);
        //al clickar en cerrar no hace nada
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
