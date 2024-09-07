package com.example.telegame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
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

    private String palabraActual;
    private Random rand = new Random(); ;
    private TextView[] charViews;
    private LinearLayout layoutPalabras;
    private Letras adaptador;
    private GridView gridView;
    private String palabras[];

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

        palabras = getResources().getStringArray(R.array.words);
        layoutPalabras = findViewById(R.id.palabras);
        gridView=findViewById(R.id.grid);

        iniciarJuego();




    }


    public void abrirActivity1(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




    public void iniciarJuego(){
        String palabraAdivinar = palabras[rand.nextInt(palabras.length)];
        System.out.println(palabraAdivinar);

        while(palabraAdivinar.equals(palabraActual))palabraAdivinar=palabras[rand.nextInt(palabras.length)];

        palabraActual=palabraAdivinar;

        charViews = new TextView[palabraActual.length()];

        for(int i=0; i<palabraActual.length();i++){
            charViews[i]=new TextView(this);
            charViews[i].setText(String.valueOf(palabraActual.charAt(i)));
            charViews[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            charViews[i].setGravity(Gravity.CENTER);
            charViews[i].setTextColor(Color.WHITE);
            charViews[i].setBackgroundResource(R.drawable.underline);
            layoutPalabras.addView(charViews[i]);
        }

        adaptador=new Letras(this);
        gridView.setAdapter(adaptador);



    }




}