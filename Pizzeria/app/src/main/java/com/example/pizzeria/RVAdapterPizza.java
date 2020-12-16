package com.example.pizzeria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapterPizza extends RecyclerView.Adapter<RVAdapterPizza.pViewHolder> {

    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnitemclickListener(onItemClickListener listener){
        mListener = listener;
    }

    private ArrayList<Pizza> pizzaArrayList;



    public static class pViewHolder extends RecyclerView.ViewHolder {

        public ImageView pizzaImage;
        public TextView textViewPizzaNombre;
        public TextView textViewPizzaPrecio;
        public TextView textViewPizzaCantidad;
        public ImageButton imageButtonAddPizza;

        public pViewHolder(@NonNull View itemView, onItemClickListener listener){
            super(itemView);

            pizzaImage = itemView.findViewById(R.id.imageViewPizza);
            textViewPizzaNombre = itemView.findViewById(R.id.textViewPizzaNombre);
            textViewPizzaPrecio = itemView.findViewById(R.id.textViewPizzaPrecio);
            textViewPizzaCantidad = itemView.findViewById(R.id.textViewPizzaCantidad);
            imageButtonAddPizza = itemView.findViewById(R.id.imageButtonAddPizza);

            imageButtonAddPizza.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }

                }
            });
        }
    }

    public RVAdapterPizza(ArrayList<Pizza> pizzaArrayList){
        this.pizzaArrayList = pizzaArrayList;
    }

    @NonNull
    @Override
    public pViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_layout, parent, false);
        pViewHolder pvh = new pViewHolder(v, mListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull pViewHolder holder, int position) {
        Pizza pizzaActual = pizzaArrayList.get(position);

        holder.pizzaImage.setImageResource(pizzaActual.getPizzaImage());
        holder.textViewPizzaNombre.setText(pizzaActual.getNombre());
        holder.textViewPizzaPrecio.setText(pizzaActual.getPrecio());
        holder.textViewPizzaCantidad.setText(pizzaActual.getStringCantidad());



    }

    @Override
    public int getItemCount() {
        return pizzaArrayList.size();
    }
}
