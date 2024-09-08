package com.example.telegame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private String palabraActual;
    private Random rand = new Random();
    private TextView[] charViews;
    private LinearLayout layoutPalabras;
    private Letras adaptador;
    private GridView gridView;
    private String palabras[];
    private int numCorrecto;
    private int numChars;
    private ImageView[]persona;
    private int sizeParts = 6;
    private int parteActual;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int tiempo = 0;
    private TextView textViewGanaste;
    private Button buttonNewGame;
    private static ArrayList<Integer> tiempos = new ArrayList<>(); // Lista estática para compartir entre actividades



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


        Intent intent = getIntent();
        String nombreUsuario = intent.getStringExtra("nombre_usuario");
        String nombreUsuario2 = intent.getStringExtra("username");
        String nombreUsuario3 = intent.getStringExtra("username2");





        ImageView toolbarIcon = findViewById(R.id.toolbar_icon);
        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity1(view);
            }
        });

        ImageView toolbarIcon2 = findViewById(R.id.toolbar_icon2);
        toolbarIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity3(view, nombreUsuario, tiempos, nombreUsuario2, nombreUsuario3);
            }
        });


        textViewGanaste = findViewById(R.id.textView4);
        textViewGanaste.setVisibility(View.GONE);
        buttonNewGame = findViewById(R.id.button2);
        buttonNewGame.setVisibility(View.GONE);

        buttonNewGame.setOnClickListener(view -> iniciarJuego());


        palabras = getResources().getStringArray(R.array.words);
        layoutPalabras = findViewById(R.id.palabras);
        gridView=findViewById(R.id.grid);
        persona = new ImageView[sizeParts];
        persona[0] = findViewById(R.id.head);
        persona[1] = findViewById(R.id.torso);
        persona[2] = findViewById(R.id.left_arm);
        persona[3] = findViewById(R.id.right_arm);
        persona[4] = findViewById(R.id.left_leght);
        persona[5] = findViewById(R.id.right_leg);



        iniciarJuego();

    }


    public void abrirActivity1(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void abrirActivity3(View view, String nombreUsuario, ArrayList<Integer> tiempos, String nombreUsuario2, String nombreUsuario3){
        Intent intent = new Intent(this, EstadisticasActivity.class);
        intent.putExtra("username", nombreUsuario);
        intent.putExtra("username2", nombreUsuario2);
        intent.putExtra("username3", nombreUsuario3);
        intent.putExtra("tiempos", tiempos);
        startActivity(intent);
    }




    public void iniciarJuego(){

        textViewGanaste = findViewById(R.id.textView4);
        textViewGanaste.setVisibility(View.GONE);
        buttonNewGame = findViewById(R.id.button2);
        buttonNewGame.setVisibility(View.GONE);

        String palabraAdivinar = palabras[rand.nextInt(palabras.length)];
        System.out.println(palabraAdivinar);

        while(palabraAdivinar.equals(palabraActual))palabraAdivinar=palabras[rand.nextInt(palabras.length)];

        palabraActual=palabraAdivinar;

        charViews = new TextView[palabraActual.length()];

        layoutPalabras.removeAllViews();
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
        numCorrecto=0;
        numChars = palabraActual.length();
        parteActual = 0;
        tiempo = 0;

        for(int i=0; i<sizeParts; i++){
            persona[i].setVisibility(View.INVISIBLE);
        }


        iniciarContador();

    }


    private void iniciarContador() {
        runnable = new Runnable() {
            @Override
            public void run() {
                tiempo++;
                System.out.println("Segundos: " + tiempo);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    private void detenerContador() {
        handler.removeCallbacks(runnable);
    }


    public void letraPresionada(View view){
        String letra = ((TextView)view).getText().toString();
        char letraChar = letra.charAt(0);

        view.setEnabled(false);
        boolean palabraCorrecta = false;

        for(int i=0; i<palabraActual.length(); i++){
            if(palabraActual.charAt(i)==letraChar){
                palabraCorrecta=true;
                numCorrecto++;
                charViews[i].setTextColor(Color.BLACK);

            }
        }

        if(palabraCorrecta){
            if(numCorrecto==numChars){
                detenerContador();
                tiempos.add(tiempo);

                String mensaje = "Ganó / Terminó en " + tiempo + " segundos";
                textViewGanaste.setText(mensaje);
                textViewGanaste.setVisibility(View.VISIBLE);

                buttonNewGame.setVisibility(View.VISIBLE);


            }
        }else if(parteActual<sizeParts){
            persona[parteActual].setVisibility(View.VISIBLE);
            parteActual++;
        }else{
            detenerContador();
            String mensaje = "Perdiste";
            textViewGanaste.setText(mensaje);
            textViewGanaste.setVisibility(View.VISIBLE);

            buttonNewGame.setVisibility(View.VISIBLE);
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        detenerContador();
    }

}

