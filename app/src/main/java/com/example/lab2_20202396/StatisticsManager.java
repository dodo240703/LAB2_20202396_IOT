package com.example.lab2_20202396;

import java.util.ArrayList;
import java.util.List;

public class StatisticsManager {

    private static StatisticsManager instance;
    private List<Resultado> listaResultados;

    private StatisticsManager() {
        listaResultados = new ArrayList<>();
    }

    public static StatisticsManager getInstance() {
        if (instance == null) {
            instance = new StatisticsManager();
        }
        return instance;
    }

    public void agregarResultado(Resultado resultado) {
        listaResultados.add(resultado);
    }

    public List<String> getResultadosFormateados() {
        List<String> resultadosFormateados = new ArrayList<>();
        for (Resultado res : listaResultados) {
            resultadosFormateados.add(res.toString());
        }
        return resultadosFormateados;
    }
}
