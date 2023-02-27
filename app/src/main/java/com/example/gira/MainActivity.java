package com.example.gira;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Button mapa, listado, preferencias;
    private ImageView mute;
    private Musica musica;
//    RelativeLayout relativeLayout;
    ConstraintLayout constraintLayout;
    SwipeListener swipeListener;
    private Constants constants;
    private int musicaPosition;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.constraint_layout);

        swipeListener = new SwipeListener(constraintLayout);

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

    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;

        SwipeListener(View view){
            int threshold = 100;
            int velocity = 100;

            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(@NonNull MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                            float xDiff = e2.getX() - e1.getX();
                            float yDiff = e2.getY() - e1.getY();

                            try {
                                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                                    if (Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity){
                                        if (xDiff > 0) {
                                            //Derecha
                                            openPantalla(ListadoConciertoActivity.class);
                                        }else{
                                            //Izquierda
                                            constants.setMapaGeneral(true);
                                            openPantalla(MapsActivity.class);
                                        }
                                        return true;
                                    }
                                }
                            }catch (Exception e ){
                                e.printStackTrace();
                            }
                            return true;
                        }
                    };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }
}