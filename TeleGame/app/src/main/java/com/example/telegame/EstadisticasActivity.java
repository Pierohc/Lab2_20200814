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
        String nombre_usuario2 = intent.getStringExtra("username2");
        String nombre_usuario3 = intent.getStringExtra("username3");




        jugador = findViewById(R.id.jugador);
        if(nombre_usuario !=null){
            jugador.setText("Jugador: " + nombre_usuario);
        }else if(nombre_usuario2 != null){
            jugador.setText("Jugador: " + nombre_usuario2);
        }else{
            jugador.setText("Jugador: " + nombre_usuario3);
        }

        listView = findViewById(R.id.listView);

        tiempos = (ArrayList<Integer>) intent.getSerializableExtra("tiempos");

        ArrayList<String> tiemposStrings = new ArrayList<>();

        for (Integer tiempo : tiempos) {
            tiemposStrings.add("Juego " + (tiempos.indexOf(tiempo)+ 1) + ": Terminó en " + tiempo + "s");
        }

        //Se usó ChatGPT para poder mostrar el arraylist relacionado a un ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tiemposStrings);
        listView.setAdapter(adapter);

        ImageView toolbarIcon = findViewById(R.id.toolbar_icon);
        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity2(view, nombre_usuario, nombre_usuario2);
            }
        });


    }

    public void abrirActivity2(View view, String username, String username2){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        intent.putExtra("username2", username2);
        startActivity(intent);
    }





}