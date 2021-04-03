package com.example.vendecar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendecar.R;
import com.example.vendecar.entity.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter both of recyclers view in the app work with.
 * It has two behaviours depending of which recycler works with it.
 * If CarListActivity recycler's: show more car's data and has it own onclick.
 * If DeleteCarActivity, recycler's show less info and has a trash icon with it own onclick.
 */
public class CarAdapter extends ListAdapter<Car, CarAdapter.CarHolder> {
    //Cars storen in the db.
    private static List<Car> savedCars = new ArrayList<>();
    private OnItemClickListener listener;
    private String modo = "";

    //ImageView click
    public MyAdapterListener onClickListener;
    //Constructor no methods.
    public CarAdapter() {
        super(DIFF_CALLBACK);
    }
    //Constructor for DeleteCarActivity
    public CarAdapter(String modo, MyAdapterListener listener){
        super(DIFF_CALLBACK);
        this.modo = modo;
        this.onClickListener = listener;

    }

    /**
     * Checks items for differences.
     */
    private static final DiffUtil.ItemCallback<Car> DIFF_CALLBACK = new DiffUtil.ItemCallback<Car>() {
        @Override
        public boolean areItemsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.getMarca().equals(newItem.getMarca()) &&
                    oldItem.getModelo().equals(newItem.getModelo()) &&
                    oldItem.getKm() == newItem.getKm() &&
                    oldItem.getAnio() == newItem.getAnio() &&
                    oldItem.getCc() == newItem.getAnio() &&
                    oldItem.getCv() == newItem.getCv() &&
                    oldItem.getPrecio() == newItem.getPrecio() &&
                    oldItem.getVendido() == newItem.getVendido();
        }
    };

    public interface MyAdapterListener {
        void iconImageViewOnClick(View v, int position);
    }
    public static final String DELETE_CARS = "delete";



    public interface OnItemClickListener{
        void onItemClick(Car car);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //ViewHolder for the Adapter
    class CarHolder extends RecyclerView.ViewHolder{
        //All the views it uses.
        private ConstraintLayout cardViewConstraintLayout;
        private TextView textViewMarca;
        private TextView textViewModelo;
        private TextView textViewKm;
        private TextView textViewAnio;
        private TextView textViewPrecio;
        private ImageView imageViewCheck;
        private TextView textViewLabelPrecio;
        private ImageView imageViewMarca;

        public CarHolder(@NonNull View itemView) {
            super(itemView);
            //Setting views.
            cardViewConstraintLayout = itemView.findViewById(R.id.cardviewConstraintLayout);
            imageViewCheck = itemView.findViewById(R.id.imageViewCheck);
            textViewMarca = itemView.findViewById(R.id.textViewMarca);
            textViewModelo = itemView.findViewById(R.id.textViewModelo);
            textViewAnio = itemView.findViewById(R.id.textViewModelo);
            textViewKm = itemView.findViewById(R.id.textViewKm);
            textViewAnio = itemView.findViewById(R.id.textViewAnio);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecio);
            textViewLabelPrecio = itemView.findViewById(R.id.textViewLabelPrecio);
            imageViewMarca = itemView.findViewById(R.id.imageViewMarca);
            //If Delete mode, this listener will work
            if(modo.equals(DELETE_CARS)){
                imageViewCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.iconImageViewOnClick(v, getAdapterPosition());
                    }
                });
            }
            //Default listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_list, parent, false);

        return new CarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {
        //Settings views info
        savedCars = getCurrentList();
        Car current = getItem(position);
        setStyle(holder, position, current);
        holder.textViewMarca.setText(current.getMarca());
        holder.textViewModelo.setText(current.getModelo());
        holder.textViewKm.setText(String.valueOf(current.getKm()));
        holder.textViewAnio.setText(String.valueOf(current.getAnio()));
        holder.textViewPrecio.setText(String.valueOf(current.getPrecio()));
    }


    public List<Car> getSavedCars() {
        return savedCars;
    }

    /**
     * Set the cardview background color and check icon depending on its position
     * and Car attribute vendido value respectively.
     *
     * It also set brand logo depending on car's brand.
     *
     * @param holder
     * @param position
     * @param current
     */
    private void setStyle(@NonNull CarHolder holder, int position, Car current) {
        if(modo.equals(DELETE_CARS)){
            holder.textViewPrecio.setVisibility(View.GONE);
            holder.textViewLabelPrecio.setVisibility(View.GONE);
            holder.imageViewCheck.setImageResource(R.drawable.ic_delete);
        }
        else if(current.getVendido()==0){
            holder.imageViewCheck.setImageResource(R.drawable.ic_close);
        }else{
            holder.imageViewCheck.setImageResource(R.drawable.ic_check);
        }

        int flag = position+1;

        if(flag%3==0){
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.light_red_gradient);
        }else if((flag+2)%3==0){
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.light_blue_gradient);
        }else if((flag+1)%3==0){
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.light_green_gradient);
        }


        switch(current.getMarca()){
            case "BMW":
                holder.imageViewMarca.setImageResource(R.drawable.bmw_logo);
                break;
            case "Audi":
                holder.imageViewMarca.setImageResource(R.drawable.auid_logo);
                break;
            case "Seat":
                holder.imageViewMarca.setImageResource(R.drawable.seat_logo);
                break;
            case "Renault":
                holder.imageViewMarca.setImageResource(R.drawable.renault_logo);
                break;
            case "Citroen":
                holder.imageViewMarca.setImageResource(R.drawable.citroen_logo);
                break;
            case "Kia":
                holder.imageViewMarca.setImageResource(R.drawable.kia_logo);
                break;
            case "Ford":
                holder.imageViewMarca.setImageResource(R.drawable.ford_logo);
                break;
            case "Nissan":
                    holder.imageViewMarca.setImageResource(R.drawable.nissan_logo);
                    break;
            default:
                break;
        }

    }

}
