package com.lucian.utilidades;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utilidades {

    public boolean campoVacio(JTextField campo) {
        return campo.getText().isBlank();
    }
    public void campoVacioAlerta(JTextField campo) {
        JOptionPane.showMessageDialog(null, "El campo " + campo.getName() + " no puede estar vacio.");
    }

    public boolean campoVacio(JTextArea campo) { return campo.getText().isBlank(); }
    public void campoVacioAlerta(JTextArea campo) {
        JOptionPane.showMessageDialog(null, "El campo " + campo.getName() + " no puede estar vacio.");
    }

    public boolean telefonoValido(JTextField campo) {
        return campo.getText().matches("^[0-9]{9}$");
    }
    public void telefonoErrorAlerta() {
        JOptionPane.showMessageDialog(null, "Error. El teléfono debe tener 9 dígitos");
    }

    public boolean emailValido(JTextField campo) {
        return campo.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    public void emailErrorAlerta() {
        JOptionPane.showMessageDialog(null, "Formato del email incorrecto. Por favor, intentelo de nuevo.");
    }

    public boolean errorSelecioneCombo(JComboBox combo) {
        return combo.getSelectedItem().equals("Seleccione");
    }

    public void errorSeleccioneAlertaCombo(JComboBox combo) {
        JOptionPane.showMessageDialog(null, combo.getName() + " no puede estar vacio.");
    }

    public boolean noHayFilaSeleccionada(JTable tabla) {
        return tabla.getSelectedRow() == -1;
    }

    public void noHayFilaSeleccionadaAlerta() {
        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
    }

    public int eliminarConfirmacion() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar el paciente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        return confirmacion;
    }

    public boolean fechaVacia(DatePicker datePicker) {
        return datePicker.getDate() == null;
    }

    public boolean fechaAnteriorAHoy(DatePicker datePicker) {
        LocalDate fecha = datePicker.getDate();
        return fecha.isBefore(LocalDate.now());
    }

    public void fechaVaciaAlerta() {
        JOptionPane.showMessageDialog(null, "La fecha no puede estar vacia");
    }

    public void fechaAnteriorHoy() {
        JOptionPane.showMessageDialog(null, "La fecha debe ser anterior a la fecha actual");
    }

    public boolean hayFilaseleccionada(JTable tabla) {
        return tabla.getSelectedRow() != -1;
    }

    public void hayFilaSeleccionadaAlerta() {
        JOptionPane.showMessageDialog(null, "No se puede añadir si hay una fila seleccionada, por favor deseleccione la tabla modificando, eliminando o cambiando de pestaña");
    }


}
