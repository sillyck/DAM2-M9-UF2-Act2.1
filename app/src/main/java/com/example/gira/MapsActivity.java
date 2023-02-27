package com.example.gira;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.gira.databinding.MapaBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap, nMapView;
    private MapaBinding binding;
    private Button inicio;
    private Button mute;
    private Musica musica;
    private Constants constants;
    private boolean mapa = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mapa);
        musica = new Musica();
        mute = findViewById(R.id.volumenMaps);

        if(!musica.isMuted()){
            mute.setBackgroundResource(R.drawable.volumenon);
        }else {
            mute.setBackgroundResource(R.drawable.volumenoff);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inicio = findViewById(R.id.tornaMaps);

        inicio.setOnClickListener(v -> {
            musica.musicaBotones(MapsActivity.this);
            constants.setMapaGeneral(false);
            openPantalla(MainActivity.class);
        });

        mute.setOnClickListener(v ->{
            musica.musicaBotones(MapsActivity.this);
            if(!musica.isMuted()){
                musica.pausaAudio();
                musica.setMuted(true);
                mute.setBackgroundResource(R.drawable.volumenoff);
            }else {
                musica.resumeAudio();
                musica.setMuted(false);
                mute.setBackgroundResource(R.drawable.volumenon);
            }
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
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        nMapView = googleMap;
        mMap = googleMap;

        if(constants.isMapaGeneral()) {
            LatLng zoom = new LatLng(40.4170525179115, -3.695737013858613);
            nMapView.moveCamera(CameraUpdateFactory.newLatLng(zoom));
            nMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(zoom, 5f));

            markersMap();
        }else {
            LatLng zoom = new LatLng(constants.getLat(), constants.getLng());
            nMapView.moveCamera(CameraUpdateFactory.newLatLng(zoom));
            nMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(zoom, 9f));

            LatLng LOC = new LatLng(constants.getLat(), constants.getLng());
            mMap.addMarker(new MarkerOptions().position(LOC).title(constants.getNombreGps()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));

            markersMap();


        }
    }

    private void markersMap(){
        LatLng BARCELONA = new LatLng(41.36342804571407, 2.152582269405529);
        LatLng CADIZ = new LatLng(36.70942989964542, -6.033733478396658);
        LatLng PAMPLONA = new LatLng(42.79604851460114, -1.6352923749240087);
        LatLng VALENCIA = new LatLng(39.46663376209704, -0.3761627173285688);
        LatLng SALAMANCA = new LatLng(40.97861617738214, -5.650137430789435);
        LatLng BILBAO = new LatLng(43.25214436050138, -2.9260665802778916);
        LatLng ALICANTE = new LatLng(38.352577566030995, -0.48456845968182516);
        LatLng MALLORCA = new LatLng(39.505014292717306, 2.5212251136752086);
        LatLng MURCIA = new LatLng(37.98377767244282, -1.1122911438896461);

        mMap.addMarker(new MarkerOptions().position(BARCELONA).title("BARCELONA").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(CADIZ).title("CADIZ").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(PAMPLONA).title("PAMPLONA").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(VALENCIA).title("VALENCIA").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(SALAMANCA).title("SALAMANCA").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(BILBAO).title("BILBAO").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(ALICANTE).title("ALICANTE").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(MALLORCA).title("MALLORCA").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
        mMap.addMarker(new MarkerOptions().position(MURCIA).title("MURCIA").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorhdlr)));
    }

    private void openPantalla(Class clase) {
        finish();
    }
}