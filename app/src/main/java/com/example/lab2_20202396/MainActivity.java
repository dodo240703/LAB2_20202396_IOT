package com.example.lab2_20202396;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnSoftware, btnCiberseguridad, btnOpticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSoftware = findViewById(R.id.btnSoftware);
        btnCiberseguridad = findViewById(R.id.btnCiberseguridad);
        btnOpticas = findViewById(R.id.btnOpticas);

        btnSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarJuego("Software");
            }
        });
        btnCiberseguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarJuego("Ciberseguridad");
            }
        });
        btnOpticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarJuego("Opticas");
            }
        });

    }

    private void iniciarJuego(String tema) {
        Intent intent = new Intent(MainActivity.this, TeleMemoActivity.class);
        intent.putExtra("tema", tema);
        startActivity(intent);
    }
}
