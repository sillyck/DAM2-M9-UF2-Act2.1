package com.example.gira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mapa, listado;
    private ImageView muteOn, muteOff;
    private int[] canciones = new int[]{R.raw.nosotros,R.raw.fuegofuego,R.raw.cuestiondefe,R.raw.sudoresfrios,R.raw.masalchol,R.raw.alatumba,R.raw.dimequesi};
    private MediaPlayer mp;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mp = MediaPlayer.create(this, canciones[0]);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (i < canciones.length) {
                    mp.start();
                    i = 0;
                }
            }
        });
        mp.start();



        muteOn = findViewById(R.id.muteOn);
        muteOff = findViewById(R.id.muteOff);
        muteOff.setEnabled(false);
        muteOn.setOnClickListener(v ->{
            mp.pause();
            muteOn.setEnabled(false);
            muteOn.setVisibility(View.INVISIBLE);
            muteOff.setVisibility(View.VISIBLE);
            muteOff.setEnabled(true);
        });

        muteOff.setOnClickListener(v ->{
            mp.start();
            muteOn.setEnabled(true);
            muteOn.setVisibility(View.VISIBLE);
            muteOff.setVisibility(View.INVISIBLE);
            muteOff.setEnabled(false);
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
        if (mp != null) {
            int pos = mp.getCurrentPosition();
            estadoGuardado.putInt("posicion", pos);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle estadoGuardado){
        super.onRestoreInstanceState(estadoGuardado);
        if (estadoGuardado != null && mp != null) {
            int pos = estadoGuardado.getInt("posicion");
            mp.seekTo(pos);
        }
    }

    private void openPantalla(Class clase) {
        Intent intent = new Intent(this, clase);
        startActivity(intent);
    }
}