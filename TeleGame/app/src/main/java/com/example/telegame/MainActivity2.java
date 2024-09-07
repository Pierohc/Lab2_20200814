package com.example.telegame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView toolbarIcon = findViewById(R.id.toolbar_icon);
        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity1(view); // Llama a tu método para abrir la otra actividad
            }
        });


        layoutPalabras = findViewById(R.id.palabras);




    }


    public void abrirActivity1(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    private String palabraActual;
    private Random rand;
    private String[] palabras = {"FIBRA","REDES","ANTENA","PROPA","CLOUD","TELECO"};
    private TextView[] charViews;
    private LinearLayout layoutPalabras;
    public void iniciarJuego(){
        String palabraAdivinar = palabras[rand.nextInt(palabras.length)];

        while(palabraAdivinar.equals(palabraActual))palabraAdivinar=palabras[rand.nextInt(palabras.length)];

        palabraActual=palabraAdivinar;

        charViews = new TextView[palabraActual.length()];

        for(int i=0; i<palabraActual.length();i++){
            charViews[i]=new TextView(this);
            charViews[i].setText(palabraActual.charAt(i));
            charViews[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            charViews[i].setGravity(Gravity.CENTER);
            charViews[i].setTextColor(Color.BLUE);
            charViews[i].setBackgroundResource(R.drawable.underline);
            layoutPalabras.addView(charViews[i]);
        }


    }




}