package com.example.gira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button mapa, listado, preferencias;
    private ImageView mute;
    private Musica musica;
    private Constants constants;
    private int musicaPosition;

    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyGestureListener gestureListener = new MyGestureListener(this);
        gestureDetector = new GestureDetector(new MyGestureDetector(this));
        gestureListener = new MyGestureListener(this)
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                return gestureDetector.onTouchEvent(event);
            }
        };

        musica = new Musica();
        mute = findViewById(R.id.muteMain);

        cargarPreferencies();

        musica.playAudio(MainActivity.this);


        mute.setOnClickListener(v -> {
            musica.musicaBotones(MainActivity.this);
            if (!musica.isMuted()) {
                musica.pausaAudio();
                musica.setMuted(true);
                mute.setImageResource(R.drawable.volumenoff);
            } else {
                musica.resumeAudio();
                musica.setMuted(false);
                mute.setImageResource(R.drawable.volumenon);
            }
        });


        mapa = findViewById(R.id.mapa);
        mapa.setOnClickListener(v -> {
            musica.musicaBotones(MainActivity.this);
            constants.setMapaGeneral(true);
            openPantalla(MapsActivity.class);
        });

        listado = findViewById(R.id.listado);
        listado.setOnClickListener(v -> {
            musica.musicaBotones(MainActivity.this);
            openPantalla((ListadoConciertoActivity.class));
        });

        preferencias = findViewById(R.id.pref);
        preferencias.setOnClickListener(v -> {
            musica.musicaBotones(MainActivity.this);
            openPantalla((Preferencias.class));
        });
    }

    protected void onPause() {
        super.onPause();
        musica.pausaAudio();
    }

    protected void onResume() {
        super.onResume();
        if (!musica.isMuted()) {
            musica.resumeAudio();
            mute.setImageResource(R.drawable.volumenon);
        } else {
            mute.setImageResource(R.drawable.volumenoff);
        }
    }

    private void openPantalla(Class clase) {
        Intent intent = new Intent(this, clase);
        startActivity(intent);
    }

    public void cargarPreferencies() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musica.PausePreferences(sharedPreferences.getBoolean("musica: ", false));
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

        Context context;
        public MyGestureDetector(Context mainActivity)
        {
            context = mainActivity;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if(Math.abs(e1.getY() - e2.getY()) > 250 && Math.abs(velocityY) > 200) {
                    System.out.println("Down");

                }
                else if(Math.abs(e2.getY()-e1.getY())>250 && Math.abs(velocityY)>200) {
                    System.out.println("Up");
                }

                else if(e1.getX()-e2.getX()>120 && Math.abs(velocityX)>200) {
                    System.out.println("Left");
                }

                else if(e2.getX()-e1.getX()>120 && Math.abs(velocityX)>200) {
                    System.out.println("Right");
                }
            }
            catch(Exception ignored) { }
            return false;
        }
    }
}