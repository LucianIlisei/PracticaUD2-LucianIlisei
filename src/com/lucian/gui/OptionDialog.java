package com.lucian.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OptionDialog extends JDialog {
    public JPanel contentPane;
    public JTextField campoIp;
    public JTextField campoUsuario;
    public JPasswordField campoContraseña;
    public JPasswordField campoContraseñaAdministrador;
    public JButton btnGuardar;
    public JButton buttonOK;
    public JButton buttonCancel;
    public JButton btnOpcionesGuardar;
    public Frame owner;

    public OptionDialog(Frame owner) {
        super(owner, "Opciones", true);
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
        }
}
