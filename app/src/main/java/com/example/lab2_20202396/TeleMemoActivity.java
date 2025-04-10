package com.example.lab2_20202396;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeleMemoActivity extends AppCompatActivity {

    private GridLayout gridPalabras;
    private TextView tvIntentos, tvTemporizador;
    private String tema;
    private String oracionSeleccionada;
    private List<String> palabrasOracion;
    private List<Button> botonesPalabras;
    private int indicePalabraActual = 0;
    private int intentos = 0;
    private final int MAX_INTENTOS = 3;
    private long tiempoInicio;
    private boolean juegoTerminado = false;

    // Oraciones predefinidas para cada temática
    private final String[] oracionesOpticas = {
            "La fibra óptica envía datos a gran velocidad evitando cualquier interferencia eléctrica",
            "Los amplificadores EDFA mejoran la señal óptica en redes de larga distancia"
    };
    private final String[] oracionesCiberseguridad = {
            "Una VPN encripta tu conexión para navegar de forma anónima y segura",
            "El ataque DDoS satura servidores con tráfico falso y causa caídas masivas"
    };
    private final String[] oracionesSoftware = {
            "Los fragments reutilizan partes de pantalla en distintas actividades de la app",
            "Los intents permiten acceder a apps como la cámara o WhatsApp directamente"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telememo);

        gridPalabras = findViewById(R.id.gridPalabras);
        tvIntentos = findViewById(R.id.tvIntentos);
        tvTemporizador = findViewById(R.id.tvTemporizador);

        tema = getIntent().getStringExtra("tema");

        oracionSeleccionada = seleccionarOracion(tema);

        palabrasOracion = new ArrayList<>();
        Collections.addAll(palabrasOracion, oracionSeleccionada.split(" "));

        List<String> palabrasMezcladas = new ArrayList<>(palabrasOracion);
        Collections.shuffle(palabrasMezcladas);

        // Agregar botones al GridLayout para cada palabra
        botonesPalabras = new ArrayList<>();
        gridPalabras.removeAllViews();
        for (String palabra : palabrasMezcladas) {
            final Button btnPalabra = new Button(this);
            btnPalabra.setText(palabra);
            btnPalabra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    procesarSeleccion(btnPalabra);
                }
            });
            botonesPalabras.add(btnPalabra);
            gridPalabras.addView(btnPalabra);
        }

        // Inicializar temporizador
        tiempoInicio = SystemClock.elapsedRealtime();
        actualizarIntentos();

        Button btnNuevoJuego = findViewById(R.id.btnNuevoJuego);
        btnNuevoJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarEstadistica(false, (SystemClock.elapsedRealtime() - tiempoInicio)/1000, intentos);
                Intent intent = new Intent(TeleMemoActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });



    }

    private String seleccionarOracion(String tema) {
        switch (tema) {
            case "Opticas":
            case "Ópticas":
                return oracionesOpticas[(int)(Math.random() * oracionesOpticas.length)];
            case "Ciberseguridad":
                return oracionesCiberseguridad[(int)(Math.random() * oracionesCiberseguridad.length)];
            case "Software":
                return oracionesSoftware[(int)(Math.random() * oracionesSoftware.length)];
            default:
                return "";
        }
    }

    private void procesarSeleccion(Button btnPalabra) {
        if (juegoTerminado) return;
        String palabraSeleccionada = btnPalabra.getText().toString();
        if (palabraSeleccionada.equals(palabrasOracion.get(indicePalabraActual))) {
            btnPalabra.setEnabled(false);
            btnPalabra.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            indicePalabraActual++;
            if (indicePalabraActual == palabrasOracion.size()) {
                juegoTerminado = true;
                long tiempoFinal = SystemClock.elapsedRealtime();
                long tiempoJuego = (tiempoFinal - tiempoInicio) / 1000; // en segundos
                mostrarResultado(true, tiempoJuego);
            }
        } else {
            Toast.makeText(this, "Secuencia incorrecta. Intentos restantes: " + (MAX_INTENTOS - intentos - 1), Toast.LENGTH_SHORT).show();
            reiniciarSelecciones();
            intentos++;
            actualizarIntentos();
            if (intentos >= MAX_INTENTOS) {
                juegoTerminado = true;
                long tiempoFinal = SystemClock.elapsedRealtime();
                long tiempoJuego = (tiempoFinal - tiempoInicio) / 1000;
                mostrarResultado(false, tiempoJuego);
            }
        }
    }

    private void reiniciarSelecciones() {
        for (Button btn : botonesPalabras) {
            btn.setEnabled(true);
            btn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }
        indicePalabraActual = 0;
    }

    private void actualizarIntentos() {
        tvIntentos.setText("Intentos: " + (MAX_INTENTOS - intentos));
    }

    private void mostrarResultado(boolean gano, long tiempoJuego) {
        String mensaje;
        if (gano) {
            mensaje = "¡Ganaste!\nTiempo: " + tiempoJuego + " seg\nIntentos usados: " + intentos;
        } else {
            mensaje = "Perdiste.\nTiempo: " + tiempoJuego + " seg";
        }
        new AlertDialog.Builder(this)
                .setTitle("Resultado")
                .setMessage(mensaje)
                .setPositiveButton("Nuevo Juego", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Registrar resultado en estadísticas y volver a la pantalla de inicio
                        registrarEstadistica(gano, tiempoJuego, intentos);
                        Intent intent = new Intent(TeleMemoActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void registrarEstadistica(boolean gano, long tiempo, int intentos) {
        Resultado resultado = new Resultado(tema, true, gano, tiempo, intentos);
        StatisticsManager.getInstance().agregarResultado(resultado);
    }

    // Manejo del menú de la AppBar o Popup para estadísticas
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_telememo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuEstadisticas) {
            startActivity(new Intent(this, StatisticsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indicePalabraActual", indicePalabraActual);
        outState.putInt("intentos", intentos);
        outState.putLong("tiempoInicio", tiempoInicio);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        indicePalabraActual = savedInstanceState.getInt("indicePalabraActual");
        intentos = savedInstanceState.getInt("intentos");
        tiempoInicio = savedInstanceState.getLong("tiempoInicio");
        actualizarIntentos();
    }
}

