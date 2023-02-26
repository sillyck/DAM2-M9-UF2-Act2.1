package com.example.gira;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button mapa, listado;
    private ImageView mute;
    private boolean isPlaying = false;
    private Musica musica;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musica = new Musica();


        musica.playAudio(MainActivity.this);

        mute = findViewById(R.id.muteMain);
        mute.setOnClickListener(v ->{
            if(isPlaying){
                musica.pausaAudio();
                isPlaying = false;
                mute.setImageResource(R.drawable.volumenoff);
            }else {
                musica.resumeAudio();
                isPlaying = true;
                mute.setImageResource(R.drawable.volumenon);
            }
        });


        mapa = findViewById(R.id.mapa);
        mapa.setOnClickListener(v -> {
            openPantalla(MapsActivity.class);
        });

        listado = findViewById(R.id.listado);
        listado.setOnClickListener(v -> {
            openPantalla((ListadoConciertoActivity.class));
        });
    }



    @Override
    protected void onSaveInstanceState(Bundle estadoGuardado){
        super.onSaveInstanceState(estadoGuardado);
        if (musica.getMp() != null) {
            int pos = musica.getMp().getCurrentPosition();
            estadoGuardado.putInt("posicion", pos);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle estadoGuardado){
        super.onRestoreInstanceState(estadoGuardado);
        if (estadoGuardado != null && musica.getMp() != null) {
            int pos = estadoGuardado.getInt("posicion");
            musica.getMp().seekTo(pos);
        }
    }

    private void openPantalla(Class clase) {
        Intent intent = new Intent(this, clase);
        startActivity(intent);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}