package com.example.gira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button mapa;
    private ImageView mute;
    private MediaPlayer mp;
    private int[] canciones = new int[]{R.raw.nwhijosdelaruina_alatumba};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mute = findViewById(R.id.muteOn);
        for(int i=0;i<canciones.length;i++) {
            mp = MediaPlayer.create(this, canciones[i]);
            if(i>canciones.length){
                i=0;
            }
        }
        mp.start();
        mapa = findViewById(R.id.mapa);
        mapa.setOnClickListener(v -> {
            openPantalla(MapsActivity.class);
        });
    }

    private void openPantalla(Class clase) {
        Intent intent = new Intent(this, clase);
        startActivity(intent);
    }
}