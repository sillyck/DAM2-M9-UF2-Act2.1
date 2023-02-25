package com.example.gira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
    private ImageView muteOn, muteOff;
    private ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    private ArrayList<String> fecha = new ArrayList<>();
    private ArrayList<String> lugar = new ArrayList<>();
    private ArrayList<String> mapa = new ArrayList<>();
    private ArrayList<String> entrada = new ArrayList<>();
    private MainActivity main = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_concierto);

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
//                    fecha.add(node.getChildNodes().item(1).getTextContent());
//                    lugar.add(node.getChildNodes().item(3).getTextContent() + " - " + node.getChildNodes().item(5).getTextContent());
                    fecha.add(elm.getElementsByTagName("fecha").item(0).getTextContent());
                    lugar.add(elm.getElementsByTagName("ciudad").item(0).getTextContent() + " - " + elm.getElementsByTagName("escenarios").item(0).getTextContent());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        muteOn = findViewById(R.id.listamuteOn);
        muteOff = findViewById(R.id.listamuteOff);
        muteOff.setEnabled(false);
        muteOn.setOnClickListener(v ->{
            main.getMp().pause();
            muteOn.setEnabled(false);
            muteOn.setVisibility(View.INVISIBLE);
            muteOff.setVisibility(View.VISIBLE);
            muteOff.setEnabled(true);
        });

        muteOff.setOnClickListener(v ->{
            main.getMp().start();
            muteOn.setEnabled(true);
            muteOn.setVisibility(View.VISIBLE);
            muteOff.setVisibility(View.INVISIBLE);
            muteOff.setEnabled(false);
        });



        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        CustomAdapter adapter = new CustomAdapter(fecha, lugar, entrada);
        CustomAdapter adapter = new CustomAdapter(fecha, lugar);
        recyclerView.setAdapter(adapter);

        tornar = findViewById(R.id.tornaListado);
        tornar.setOnClickListener(v -> {
            openPantalla();
        });

    }

    protected String getNodeValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        if(node!=null){
            if(node.hasChildNodes()){
                Node child = node.getFirstChild();
                while (child!=null){
                    if(child.getNodeType() == Node.TEXT_NODE){
                        return  child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    private void openPantalla() {
        finish();
    }
}