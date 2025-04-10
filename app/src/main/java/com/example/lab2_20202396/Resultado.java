package com.example.lab2_20202396;

public class Resultado {
    private final String tema;
    private final boolean completado; // false si fue cancelado
    private final boolean gano;       // true si ganó
    private final long tiempo;        // en segundos
    private final int intentos;

    public Resultado(String tema, boolean completado, boolean gano, long tiempo, int intentos) {
        this.tema = tema;
        this.completado = completado;
        this.gano = gano;
        this.tiempo = tiempo;
        this.intentos = intentos;
    }

    public String getResumen() {
        if (!completado) {
            return tema + ": Canceló";
        } else if (gano) {
            return tema + ": Ganó en " + tiempo + "s, intentos usados: " + intentos;
        } else {
            return tema + ": Perdió en " + tiempo + "s";
        }
    }

    @Override
    public String toString() {
        return getResumen();
    }
}
