package com.example.telegame;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
    private int numCorrecto;
    private int numChars;
    private ImageView[]persona;
    private int sizeParts = 6;
    private int parteActual;

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
        numCorrecto=0;
        numChars = palabraActual.length();
        parteActual = 0;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Ganaste");
                builder.setMessage("Felicidades \n \n ");
                builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity2.this.iniciarJuego();
                    }
                });
                builder.show();

            }
        }else if(parteActual<sizeParts){
            persona[parteActual].setVisibility(View.VISIBLE);
            parteActual++;
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Perdiste");
            builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity2.this.iniciarJuego();
                }
            });
            builder.show();
        }

    }


}