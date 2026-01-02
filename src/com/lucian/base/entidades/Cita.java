package com.lucian.base.entidades;

import java.time.LocalDateTime;

public class Cita {
    private int idCita;
    private int idPaciente;
    private int idDoctor;
    private LocalDateTime fechaHora;
    private String motivo;
    private String diagnostico;
    private int idMedicamento;

    public Cita(int idCita, int idPaciente, int idDoctor, LocalDateTime fechaHora, String motivo, String diagnostico, int idMedicamento) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.idMedicamento = idMedicamento;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
}
