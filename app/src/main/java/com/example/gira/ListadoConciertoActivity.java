package com.example.gira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ListadoConciertoActivity extends AppCompatActivity {
    private Button tornar;
    private ImageView mute;
    private boolean isPlaying = false;
    private Musica musica;
    private MainActivity main;
    private ArrayList<String> fecha = new ArrayList<>();
    private ArrayList<String> lugar = new ArrayList<>();
    private ArrayList<String> lat = new ArrayList<>();
    private ArrayList<String> lng = new ArrayList<>();
    private ArrayList<String> entrada = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_concierto);
        musica = new Musica();
        main = new MainActivity();
        mute = findViewById(R.id.listamute);

        if(!musica.isMuted()){
            mute.setImageResource(R.drawable.volumenon);
        }else {
            mute.setImageResource(R.drawable.volumenoff);
        }

        try {
            InputStream input = getAssets().open("conciertos.xml");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(input);
            NodeList nList = document.getElementsByTagName("concierto");

            for(int i = 0; i<nList.getLength(); i++){
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element elm = (Element) nList.item(i);
                    fecha.add(elm.getElementsByTagName("fecha").item(0).getTextContent());
                    lugar.add(elm.getElementsByTagName("ciudad").item(0).getTextContent() + " - " + elm.getElementsByTagName("escenarios").item(0).getTextContent());
                    entrada.add(elm.getElementsByTagName("entrada").item(0).getTextContent());
                    lat.add(elm.getElementsByTagName("lat").item(0).getTextContent());
                    lng.add(elm.getElementsByTagName("long").item(0).getTextContent());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        mute.setOnClickListener(v ->{
            musica.musicaBotones(ListadoConciertoActivity.this);
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





        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter adapter = new CustomAdapter(this, fecha, lugar, entrada, lat, lng);
        recyclerView.setAdapter(adapter);

        tornar = findViewById(R.id.tornaListado);
        tornar.setOnClickListener(v -> {
            musica.musicaBotones(ListadoConciertoActivity.this);
            openPantalla();
        });

    }

    protected void onPause() {
        super.onPause();
        musica.pausaAudio();
        isPlaying = false;
    }

    protected void onResume() {
        super.onResume();
        if(!musica.isMuted()) {
            musica.resumeAudio();
            isPlaying = true;
        }
    }

    private void openPantalla() {
        finish();
    }
}
