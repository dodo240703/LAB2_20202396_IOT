package com.example.lab2_20202396;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private ListView listViewEstadisticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        listViewEstadisticas = findViewById(R.id.listViewEstadisticas);

        List<String> resultados = StatisticsManager.getInstance().getResultadosFormateados();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultados);
        listViewEstadisticas.setAdapter(adapter);
    }
}
