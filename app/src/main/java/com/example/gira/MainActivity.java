package com.example.gira;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mapa, listado;
    private ImageView mute;
    private Musica musica;
    private Constants constants;
    private int musicaPosition;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musica = new Musica();
        mute = findViewById(R.id.muteMain);

        musica.playAudio(MainActivity.this);


        mute.setOnClickListener(v ->{
            if(!musica.isMuted()){
                musica.pausaAudio();
                musica.setMuted(true);
                mute.setImageResource(R.drawable.volumenoff);
            }else {
                musica.resumeAudio();
                musica.setMuted(false);
                mute.setImageResource(R.drawable.volumenon);
            }
        });


        mapa = findViewById(R.id.mapa);
        mapa.setOnClickListener(v -> {
            constants.setMapaGeneral(true);
            openPantalla(MapsActivity.class);
        });

        listado = findViewById(R.id.listado);
        listado.setOnClickListener(v -> {
            openPantalla((ListadoConciertoActivity.class));
        });
    }

    protected void onPause() {
        super.onPause();
        musica.pausaAudio();
    }

    protected void onResume() {
        super.onResume();
        if(!musica.isMuted()) {
            musica.resumeAudio();
            mute.setImageResource(R.drawable.volumenon);
        } else {
            mute.setImageResource(R.drawable.volumenoff);
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle estadoGuardado){
//        super.onSaveInstanceState(estadoGuardado);
////        if (musica.getMp() != null) {
//        musica.pausaAudio();
//        musicaPosition = musica.getMp().getCurrentPosition();
//        isPlaying = musica.getMp().isPlaying();
//        estadoGuardado.putInt("musciaPosicion", musicaPosition);
//        estadoGuardado.putBoolean("isPlaying", isPlaying);
////            int pos = musica.getMp().getCurrentPosition();
////            estadoGuardado.putInt("posicion", pos);
////        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle estadoGuardado){
//        super.onRestoreInstanceState(estadoGuardado);
////        if (estadoGuardado != null && musica.getMp() != null) {
//        musica.resumeAudio();
//        int pos = estadoGuardado.getInt("musciaPosicion");
//        musica.getMp().seekTo(pos);
////        }
//    }

    private void openPantalla(Class clase) {
        Intent intent = new Intent(this, clase);
        startActivity(intent);
    }

}