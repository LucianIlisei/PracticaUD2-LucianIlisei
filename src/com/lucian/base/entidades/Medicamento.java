package com.lucian.base.entidades;

public class Medicamento {
    private int idMedicamento;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String dosis;
    private String efectosSecundarios;

    public Medicamento(int idMedicamento, String nombre, String descripcion, String tipo, String dosis, String efectosSecundarios) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.dosis = dosis;
        this.efectosSecundarios = efectosSecundarios;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getEfectosSecundarios() {
        return efectosSecundarios;
    }

    public void setEfectosSecundarios(String efectosSecundarios) {
        this.efectosSecundarios = efectosSecundarios;
    }
}
