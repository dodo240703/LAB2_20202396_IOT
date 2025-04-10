package com.example.lab2_20202396;

public class Resultado {
    private String tema;
    private boolean completado;
    private long tiempo;
    private int intentos;

    public Resultado(String tema, boolean completado, long tiempo, int intentos) {
        this.tema = tema;
        this.completado = completado;
        this.tiempo = tiempo;
        this.intentos = intentos;
    }

    @Override
    public String toString() {
        if (!completado) {
            return tema + ": Canceló";
        } else {
            return tema + ": Tiempo " + tiempo + " seg" + (intentos > 0 ? ", Intentos usados: " + intentos : "");
        }
    }
}
