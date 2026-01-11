package com.lucian.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class OptionDialogBuscar extends JDialog {
    private JPanel contentPane;
    public JComboBox comboBoxBuscar;
    public JButton btnBuscar;
    public JTextField campoBuscar;
    public JTable table1;
    public JButton buttonOK;
    public JButton buttonCancel;
    public Frame owner;
    DefaultTableModel dtmBusqueda;

    public OptionDialogBuscar(Frame owner) {
        super(owner, "Buscar", true);
        this.owner = owner;
        initDialog();
    }

    private void initDialog() {
        this.setContentPane(contentPane);
        this.contentPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth()+200,this.getHeight()));
        this.setLocationRelativeTo(owner);

        cargarComboBoxBuscar();
        setTableModels();
        aplicarEstilo();
    }

    private void aplicarEstilo() {

        contentPane.setBackground(new Color(244, 246, 250));
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        estilizarCampo(campoBuscar);
        estilizarBoton(btnBuscar, new Color(162, 114, 185));

        table1.setRowHeight(28);
        table1.setShowVerticalLines(false);
        table1.setShowHorizontalLines(false);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
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

    public void cargarComboBoxBuscar() {
        String[] comboBox = {"Pacientes", "Doctores", "Hospitales", "Medicamentos"};
        for (String dato : comboBox) {
            comboBoxBuscar.addItem(dato);
        }
    }

    private void setTableModels() {
        this.dtmBusqueda = new DefaultTableModel();
        this.table1.setModel(dtmBusqueda);
    }

}
