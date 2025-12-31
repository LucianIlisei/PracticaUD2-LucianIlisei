package com.lucian.base.enums;

public enum TipoMedicamento {
    ANALGESICO("Analgésico"),
    ANTIBIOTICO("Antibiótico"),
    ANTIINFLAMATORIO("Antiinflamatorio"),
    ANTIPIRETICO("Antipirético"),
    ANTIALERGICO("Antialérgico"),
    ANTIVIRAL("Antiviral"),
    ANTIFUNGICO("Antifúngico"),
    ANSIOLITICO("Ansiolítico"),
    ANTIDEPRESIVO("Antidepresivo"),
    ANTICONCEPTIVO("Anticonceptivo"),
    VACUNA("Vacuna"),
    VITAMINA("Vitamina"),
    HORMONA("Hormona"),
    BRONCODILATADOR("Broncodilatador"),
    DIURETICO("Diurético");

    private final String valor;

    TipoMedicamento(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
