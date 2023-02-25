package com.example.gira;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomVH>{

    List<String> fecha;
    List<String> lugar;

    public CustomAdapter(List<String> fecha, List<String> lugar) {
        this.fecha = fecha;
        this.lugar = lugar;
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
    }

    @Override
    public int getItemCount() {
        return fecha.size();
    }
}

class CustomVH extends RecyclerView.ViewHolder{
    TextView textFecha, textLugar;
    private CustomAdapter adapter;

    public CustomVH(@NonNull View itemView) {
        super(itemView);

        textFecha = itemView.findViewById(R.id.fecha);
        textLugar = itemView.findViewById(R.id.lugar);
    }

    public CustomVH linkAdapter(CustomAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}
