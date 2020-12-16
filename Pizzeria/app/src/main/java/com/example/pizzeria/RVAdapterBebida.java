package com.example.pizzeria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapterBebida extends RecyclerView.Adapter<RVAdapterBebida.ViewHolder>  {

    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnitemclickListener(onItemClickListener listener){
        mListener = listener;
    }


    private ArrayList<Bebida> bebidaArrayList;

    public static class  ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewBebida;
        public TextView textViewBebidaNombre;
        public TextView textViewBebidaPrecio;
        public TextView textViewBebidaCantidad;
        public ImageButton imageButtonAddBebida;

        public ViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);

            imageViewBebida = itemView.findViewById(R.id.imageViewBebida);
            textViewBebidaNombre = itemView.findViewById(R.id.textViewBebidaNombre);
            textViewBebidaPrecio = itemView.findViewById(R.id.textViewBebidaPrecio);
            textViewBebidaCantidad = itemView.findViewById(R.id.textViewBebidaCantidad);
            imageButtonAddBebida = itemView.findViewById(R.id.imageButtonBebidaAdd);

            imageButtonAddBebida.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }

                }
            });

        }

    }

    public RVAdapterBebida(ArrayList<Bebida> bebidaArrayList){
        this.bebidaArrayList = bebidaArrayList;
    }


    @NonNull
    @Override
    public RVAdapterBebida.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bebida_layout, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterBebida.ViewHolder holder, int position) {
        Bebida bebidaActual = bebidaArrayList.get(position);

        holder.imageViewBebida.setImageResource(bebidaActual.getImgBebida());
        holder.textViewBebidaNombre.setText(bebidaActual.getNombre());
        holder.textViewBebidaPrecio.setText(bebidaActual.getPrecio());
        holder.textViewBebidaCantidad.setText(bebidaActual.getCantidad());
    }

    @Override
    public int getItemCount() {
        return bebidaArrayList.size();
    }
}
