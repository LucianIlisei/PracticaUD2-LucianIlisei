package com.lucian.base.enums;

public enum CiudadesHospital {
    A_CORUÑA("A Coruña"),
    ALBACETE("Albacete"),
    ALICANTE("Alicante"),
    ALMERIA("Almería"),
    AVILA("Ávila"),
    BADAJOZ("Badajoz"),
    BARCELONA("Barcelona"),
    BILBAO("Bilbao"),
    BURGOS("Burgos"),
    CACERES("Cáceres"),
    CADIZ("Cádiz"),
    CASTELLON("Castellón"),
    CIUDAD_REAL("Ciudad Real"),
    CORDOBA("Córdoba"),
    CUENCA("Cuenca"),
    DONOSTIA("Donostia"),
    GIRONA("Girona"),
    GRANADA("Granada"),
    GUADALAJARA("Guadalajara"),
    HUELVA("Huelva"),
    HUESCA("Huesca"),
    JAEN("Jaén"),
    LAS_PALMAS("Las Palmas"),
    LEON("León"),
    LLEIDA("Lleida"),
    LOGROÑO("Logroño"),
    LUGO("Lugo"),
    MADRID("Madrid"),
    MALAGA("Málaga"),
    MURCIA("Murcia"),
    OURENSE("Ourense"),
    OVIEDO("Oviedo"),
    PALENCIA("Palencia"),
    PALMA("Palma"),
    PAMPLONA("Pamplona"),
    PONTEVEDRA("Pontevedra"),
    SALAMANCA("Salamanca"),
    SANTA_CRUZ_TENERIFE("Santa Cruz de Tenerife"),
    SANTANDER("Santander"),
    SEGOVIA("Segovia"),
    SEVILLA("Sevilla"),
    SORIA("Soria"),
    TARRAGONA("Tarragona"),
    TERUEL("Teruel"),
    TOLEDO("Toledo"),
    VALENCIA("Valencia"),
    VALLADOLID("Valladolid"),
    VITORIA("Vitoria"),
    ZAMORA("Zamora"),
    ZARAGOZA("Zaragoza");

    private final String valor;

    CiudadesHospital(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
