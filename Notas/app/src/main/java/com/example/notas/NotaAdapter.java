package com.example.notas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {

    private ArrayList<Nota> notaArrayList;
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    public interface OnItemLongClickListener{
        boolean longClick(int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longListener){
        this.longListener = longListener;
    }

    public interface OnItemClickListener {
        void OnItemCick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class NotaViewHolder extends RecyclerView.ViewHolder{

        public TextView tituloTextView;
        public TextView textoTextView;


        public NotaViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemLongClickListener longListener) {
            super(itemView);

            tituloTextView = itemView.findViewById(R.id.titulo);
            textoTextView = itemView.findViewById(R.id.texto);

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.OnItemCick(position);
                    }
                }
            });
            itemView.setOnLongClickListener(v -> {
                if(longListener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        longListener.longClick(position);
                    }
                }
                return true;
            });
        }
    }

    public NotaAdapter(ArrayList<Nota> notaArrayList){
        this.notaArrayList = notaArrayList;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Aquí se establece el layout que tendrá el recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota, parent, false);
        NotaViewHolder nvh = new NotaViewHolder(v, listener, longListener);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota notaActual = notaArrayList.get(position);

        holder.tituloTextView.setText(notaActual.getTitle());
        holder.textoTextView.setText(notaActual.getText());

    }

    @Override
    public int getItemCount() {
        return notaArrayList.size();
    }
}
