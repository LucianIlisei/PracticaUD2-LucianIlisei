package com.lucian.base.enums;

public enum EspecialidadDoctor {
    MEDICINA_GENERAL("Medicina General"),
    PEDIATRIA("Pediatría"),
    CARDIOLOGIA("Cardiología"),
    DERMATOLOGIA("Dermatología"),
    GINECOLOGIA("Ginecología"),
    TRAUMATOLOGIA("Traumatología"),
    NEUROLOGIA("Neurología"),
    PSIQUIATRIA("Psiquiatría"),
    OFTALMOLOGIA("Oftalmología"),
    OTORRINOLARINGOLOGIA("Otorrinolaringología");

    private final String valor;

    EspecialidadDoctor(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
