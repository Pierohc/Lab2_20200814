package com.example.telegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EstadisticasActivity extends AppCompatActivity {

    private TextView jugador;
    private ListView listView;
    private ArrayList<Integer> tiempos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estadisticas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String nombre_usuario = intent.getStringExtra("username");
        System.out.println(nombre_usuario);

        jugador = findViewById(R.id.jugador);
        jugador.setText("Jugador: " + nombre_usuario);

        listView = findViewById(R.id.listView);

        tiempos = (ArrayList<Integer>) intent.getSerializableExtra("tiempos");

        ArrayList<String> tiemposStrings = new ArrayList<>();

        for (Integer tiempo : tiempos) {
            tiemposStrings.add("Juego " + (tiempos.indexOf(tiempo)+ 1) + ": Terminó en " + tiempo + "s");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tiemposStrings);
        listView.setAdapter(adapter);

        ImageView toolbarIcon = findViewById(R.id.toolbar_icon);
        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity2(view);
            }
        });


    }

    public void abrirActivity2(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }





}