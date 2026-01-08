package com.lucian.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.lucian.base.entidades.*;
import com.lucian.base.enums.CiudadesHospital;
import com.lucian.base.enums.EspecialidadDoctor;
import com.lucian.base.enums.TipoMedicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vista extends JFrame{
    private JPanel panel1;
    public JTabbedPane tabbedPane1;
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
    JRadioButton publicoRadioButton;
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
    JTextField campoDescripción;
    JComboBox comboBoxTipoMedicamento;
    JTextField campoDosisMedicamento;
    JTextArea campoEfectosSecundarios;
    JButton btnAñadirMedicamento;
    JButton btnModificarMedicamento;
    JButton btnEliminarMedicamento;
    JTable medicamentosTabla;

    // Tablas
    DefaultTableModel dtmPacientes;
    DefaultTableModel dtmDoctores;
    DefaultTableModel dtmHospitales;
    DefaultTableModel dtmCitas;
    DefaultTableModel dtmMedicamentos;

    JLabel etiquetaEstado;
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;
    OptionDialog optionDialog;

    //cuadro dialogo
    JDialog adminPasswordDialog;
    JButton btnValidate;
    JPasswordField adminPassword;

    public Vista() {
        super(TITULO_FRAME);
        initFrame();
        initComponents();
    }

    public void initFrame() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth()+100,this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        setMenu();
        setAdminDialog();
        setEnumComboBox();
        setTableModels();
        aplicarEstiloPacientes();
        aplicarEstiloDoctores();
        aplicarEstiloHospitales();
        aplicarEstiloCitas();
        aplicarEstiloMedicamentos();
    }

    public void initComponents() {
        masculinoRadioButtonPaciente.setSelected(true);
        siRadioButtonFumadorPaciente.setSelected(true);
        publicoRadioButton.setSelected(true);
        SpinnerNumberModel capacidadHospital = new SpinnerNumberModel(100, 1, 10000, 50);
        spinnerCapacidadHospital.setModel(capacidadHospital);
        fechaHoraCita.setEnabled(false);
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        itemDesconectar=new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemSalir= new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }

    private void setAdminDialog() {
        btnValidate= new JButton("Validar");
        btnValidate.setActionCommand("abrirOpciones");
        adminPassword=new JPasswordField();
        adminPassword.setPreferredSize(new Dimension(100,26));
        Object[] options = new Object[] {adminPassword,btnValidate};
        JOptionPane jop= new JOptionPane("Introduce la contraseña",
                JOptionPane.WARNING_MESSAGE,JOptionPane.YES_NO_OPTION,null,options);
        adminPasswordDialog=new JDialog(this,"Opciones",true);
        adminPasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPasswordDialog.setContentPane(jop);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);
    }

    private void aplicarEstiloPacientes() {
        getContentPane().setBackground(new Color(244, 246, 250));

        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        estilizarBoton(btnAñadirPaciente, new Color(16, 185, 129));
        estilizarBoton(btnModificarPaciente, new Color(249, 115, 22));
        estilizarBoton(btnEliminarPaciente, new Color(220, 38, 38));

        estilizarCampo(campoNombrePaciente);
        estilizarCampo(campoPrimerApellidoPaciente);
        estilizarCampo(campoSegundoApellidoPaciente);
        estilizarCampo(campoTelefonoPaciente);
        estilizarCampo(campoEmailPaciente);

        pacientesTabla.setRowHeight(28);
        pacientesTabla.setShowVerticalLines(false);
        pacientesTabla.setShowHorizontalLines(false);
        pacientesTabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pacientesTabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void aplicarEstiloDoctores() {
        getContentPane().setBackground(new Color(244, 246, 250));

        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        estilizarBoton(btnAñadirDoctor, new Color(16, 185, 129));
        estilizarBoton(btnModificarDoctor, new Color(249, 115, 22));
        estilizarBoton(btnEliminarDoctor, new Color(220, 38, 38));

        estilizarCampo(campoNombrePaciente);
        estilizarCampo(campoPrimerApellidoPaciente);
        estilizarCampo(campoSegundoApellidoPaciente);
        estilizarCampo(campoTelefonoDoctor);
        estilizarCampo(campoEmailDoctor);

        doctoresTabla.setRowHeight(28);
        doctoresTabla.setShowVerticalLines(false);
        doctoresTabla.setShowHorizontalLines(false);
        doctoresTabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        doctoresTabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void aplicarEstiloHospitales() {
        getContentPane().setBackground(new Color(244, 246, 250));

        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        estilizarBoton(btnAñadirHospital, new Color(16, 185, 129));
        estilizarBoton(btnModificarHospital, new Color(249, 115, 22));
        estilizarBoton(btnEliminarHospital, new Color(220, 38, 38));

        estilizarCampo(campoNombreHospital);
        estilizarCampo(campoTelefonoHospital);

        hospitalesTabla.setRowHeight(28);
        hospitalesTabla.setShowVerticalLines(false);
        hospitalesTabla.setShowHorizontalLines(false);
        hospitalesTabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hospitalesTabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void aplicarEstiloCitas() {
        getContentPane().setBackground(new Color(244, 246, 250));

        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        estilizarBoton(btnAñadirCita, new Color(16, 185, 129));
        estilizarBoton(btnModificarCita, new Color(249, 115, 22));
        estilizarBoton(btnEliminarCita, new Color(220, 38, 38));

        estilizarCampo(campoMotivoCita);

        citasTabla.setRowHeight(28);
        citasTabla.setShowVerticalLines(false);
        citasTabla.setShowHorizontalLines(false);
        citasTabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        citasTabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void aplicarEstiloMedicamentos() {
        getContentPane().setBackground(new Color(244, 246, 250));

        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        estilizarBoton(btnAñadirMedicamento, new Color(16, 185, 129));
        estilizarBoton(btnModificarMedicamento, new Color(249, 115, 22));
        estilizarBoton(btnEliminarMedicamento, new Color(220, 38, 38));

        estilizarCampo(campoNombreMedicamento);
        estilizarCampo(campoDescripción);
        estilizarCampo(campoDosisMedicamento);

        medicamentosTabla.setRowHeight(28);
        medicamentosTabla.setShowVerticalLines(false);
        medicamentosTabla.setShowHorizontalLines(false);
        medicamentosTabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        medicamentosTabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void estilizarBoton(JButton b, Color color) {
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.putClientProperty("JButton.buttonType", "roundRect");
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.putClientProperty("JComponent.roundRect", true);
    }


    private void setEnumComboBox() {
        comboBoxEspecialidadDoctor.addItem("Seleccione");
        comboBoxProvinciaHospital.addItem("Seleccione");
        comboBoxTipoMedicamento.addItem("Seleccione");
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
        Hospital h = (Hospital) comboBoxHospitalDoctor.getSelectedItem();
        int id = h.getIdHospital();
        doctor.setIdHospital(id);

        return doctor;
    }
    public Doctor getDoctorFormulario(int idDoctor) {
        Doctor doctor = new Doctor();

        doctor.setIdDoctor(idDoctor);
        doctor.setNombre(campoNombreDoctor.getText());
        doctor.setPrimerApellido(campoPrimerApellidoDoctor.getText());
        doctor.setSegundoApellido(campoSegundoApellidoDoctor.getText());
        doctor.setTelefono(campoTelefonoDoctor.getText());
        doctor.setEmail(campoEmailDoctor.getText());
        doctor.setEspecialidad(comboBoxEspecialidadDoctor.getSelectedItem().toString());
        doctor.setFechaContratacion(fechaContratacionDatePickerDoctor.getDate());
        Hospital h = (Hospital) comboBoxHospitalDoctor.getSelectedItem();
        int id = h.getIdHospital();
        doctor.setIdHospital(id);

        return doctor;
    }

    public Hospital getHospitalFormulario() {
        Hospital hospital = new Hospital();

        hospital.setNombre(campoNombreHospital.getText());
        hospital.setProvincia(comboBoxProvinciaHospital.getSelectedItem().toString());
        hospital.setTelefono(campoTelefonoHospital.getText());
        hospital.setCapacidad(Integer.parseInt(spinnerCapacidadHospital.getValue().toString()));
        if (privadoRadioButton.isSelected()) {
            hospital.setTipo("Privado");
        } else if (publicoRadioButton.isSelected()) {
            hospital.setTipo("Publico");
        }
        return hospital;
    }

    public Hospital getHospitalFormulario(int id) {
        Hospital hospital = new Hospital();

        hospital.setIdHospital(id);
        hospital.setNombre(campoNombreHospital.getText());
        hospital.setProvincia(comboBoxProvinciaHospital.getSelectedItem().toString());
        hospital.setTelefono(campoTelefonoHospital.getText());
        hospital.setCapacidad(Integer.parseInt(spinnerCapacidadHospital.getValue().toString()));
        if (privadoRadioButton.isSelected()) {
            hospital.setTipo("Privado");
        } else if (publicoRadioButton.isSelected()) {
            hospital.setTipo("Publico");
        }
        return hospital;
    }

    public Cita getCitaFormulario() {
        Cita cita = new Cita();

        Paciente p = (Paciente) comboBoxPacienteCita.getSelectedItem();
        cita.setIdPaciente(p.getIdPaciente());
        Doctor d = (Doctor) comboBoxDoctorCita.getSelectedItem();
        cita.setIdDoctor(d.getIdDoctor());
        cita.setFechaHora(fechaHoraCita.getDateTimeStrict());
        cita.setMotivo(campoMotivoCita.getText());
        cita.setDiagnostico(campoDiagnosticoCita.getText());
        Medicamento m = (Medicamento) comboBoxMedicamentoCita.getSelectedItem();
        cita.setIdMedicamento(m.getIdMedicamento());

        return cita;
    }

    public Cita getCitaFormulario(int id) {
        Cita cita = new Cita();

        cita.setIdCita(id);
        Paciente p = (Paciente) comboBoxPacienteCita.getSelectedItem();
        cita.setIdPaciente(p.getIdPaciente());
        Doctor d = (Doctor) comboBoxDoctorCita.getSelectedItem();
        cita.setIdDoctor(d.getIdDoctor());
        cita.setFechaHora(fechaHoraCita.getDateTimeStrict());
        cita.setMotivo(campoMotivoCita.getText());
        cita.setDiagnostico(campoDiagnosticoCita.getText());
        Medicamento m = (Medicamento) comboBoxMedicamentoCita.getSelectedItem();
        cita.setIdMedicamento(m.getIdMedicamento());

        return cita;
    }

    public Medicamento getMedicamentoFormulario() {
        Medicamento medicamento = new Medicamento();

        medicamento.setNombre(campoNombreMedicamento.getText());
        medicamento.setDescripcion(campoDescripción.getText());
        medicamento.setTipo(comboBoxTipoMedicamento.getSelectedItem().toString());
        medicamento.setDosis(campoDosisMedicamento.getText());
        medicamento.setEfectosSecundarios(campoEfectosSecundarios.getText());

        return medicamento;
    }

    public Medicamento getMedicamentoFormulario(int id) {
        Medicamento medicamento = new Medicamento();

        medicamento.setIdMedicamento(id);
        medicamento.setNombre(campoNombreMedicamento.getText());
        medicamento.setDescripcion(campoDescripción.getText());
        medicamento.setTipo(comboBoxTipoMedicamento.getSelectedItem().toString());
        medicamento.setDosis(campoDosisMedicamento.getText());
        medicamento.setEfectosSecundarios(campoEfectosSecundarios.getText());

        return medicamento;
    }

    public void cargarHospitalesCombo(ResultSet rs) {
        try {
            comboBoxHospitalPaciente.removeAllItems();
            comboBoxHospitalDoctor.removeAllItems();

            while (rs.next()) {
                Hospital h = new Hospital();
                h.setIdHospital(rs.getInt("id_hospital"));
                h.setNombre(rs.getString("nombre"));

                comboBoxHospitalPaciente.addItem(h);
                comboBoxHospitalDoctor.addItem(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarPacientesCombo(ResultSet rs) {
        try {
            comboBoxPacienteCita.removeAllItems();

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("id_paciente"));
                p.setNombre(rs.getString("nombre"));
                p.setPrimerApellido(rs.getString("primer_apellido"));

                comboBoxPacienteCita.addItem(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarDoctoresCombo(ResultSet rs) {
        try {
            comboBoxDoctorCita.removeAllItems();

            while (rs.next()) {
                Doctor d = new Doctor();
                d.setIdDoctor(rs.getInt("id_doctor"));
                d.setNombre(rs.getString("nombre"));
                d.setPrimerApellido(rs.getString("primer_apellido"));

                comboBoxDoctorCita.addItem(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarMedicamentosCombo(ResultSet rs) {
        try {
            comboBoxMedicamentoCita.removeAllItems();

            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setIdMedicamento(rs.getInt("id_medicamento"));
                m.setNombre(rs.getString("nombre"));

                comboBoxMedicamentoCita.addItem(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
