package com.lucian.base.entidades;

public class Hospital {
    private int idHospital;
    private String nombre;
    private String provincia;
    private String telefono;
    private int capacidad;
    private String tipo;

    public Hospital(int idHospital, String nombre, String provincia, String telefono, int capacidad, String tipo) {
        this.idHospital = idHospital;
        this.nombre = nombre;
        this.provincia = provincia;
        this.telefono = telefono;
        this.capacidad = capacidad;
        this.tipo = tipo;
    }

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return idHospital + " - " + nombre;
    }

}
