package com.lucian.utilidades;

import javax.swing.*;

public class Utilidades {

    public boolean campoVacio(JTextField campo) {
        return campo.getText().isBlank();
    }

    public void campoVacioAlerta(JTextField campo) {
        JOptionPane.showMessageDialog(null, "El campo " + campo.getName() + " no puede estar vacio");
    }

    public boolean campoVacio(JTextArea campo) {
        return campo.getText().isBlank();
    }

    public boolean telefonoValido(JTextField campo) {
        return campo.getText().matches("^[0-9]{9}$");
    }

    public boolean emailValido(JTextField campo) {
        return campo.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }



}
