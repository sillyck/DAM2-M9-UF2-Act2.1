package com.example.gira;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomVH>{

    ArrayList<String> fecha;
    ArrayList<String> lugar;
    ArrayList<String> entrada;
    ArrayList<String> lat;
    ArrayList<String> lng;
    Context context;
    Constants constants;

    public CustomAdapter(Context context, ArrayList<String> fecha, ArrayList<String> lugar, ArrayList<String> entrada,
                         ArrayList<String> lat, ArrayList<String> lng) {
        this.context = context;
        this.fecha = fecha;
        this.lugar = lugar;
        this.entrada = entrada;
        this.lat = lat;
        this.lng = lng;
    }

    @NonNull
    @Override
    public CustomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_concierto, parent, false);
        return new CustomVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomVH holder, int position) {
        holder.textFecha.setText(fecha.get(position));
        holder.textLugar.setText((lugar.get(position)));
        holder.imgEntrada.setOnClickListener( v -> {
            Uri url = Uri.parse(entrada.get(position));
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            context.startActivity(intent);
        });

        holder.imgMapa.setOnClickListener( v -> {
            constants.setNombreGps(lugar.get(position));
            constants.setLat(Double.parseDouble(lat.get(position)));
            constants.setLng(Double.parseDouble(lng.get(position)));
            Intent intent  = new Intent(context, MapsActivity.class);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return fecha.size();
    }
}

class CustomVH extends RecyclerView.ViewHolder{
    TextView textFecha, textLugar;
    ImageView imgEntrada, imgMapa;
    private CustomAdapter adapter;

    public CustomVH(@NonNull View itemView) {
        super(itemView);
        textFecha = itemView.findViewById(R.id.fecha);
        textLugar = itemView.findViewById(R.id.lugar);
        imgEntrada = itemView.findViewById(R.id.ticket);
        imgMapa = itemView.findViewById(R.id.mapa);
    }

    public CustomVH linkAdapter(CustomAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}
