package com.lucian.gui;

import javax.swing.*;
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
    }

    public void cargarComboBoxBuscar() {
        String[] comboBox = {"Pacientes", "Doctores", "Hospitales", "Medicamentos"};
        for (String dato : comboBox) {
            comboBoxBuscar.addItem(dato);
        }
    }
}
