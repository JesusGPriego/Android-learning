package com.example.pizzeria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class PizzaActivity extends AppCompatActivity {

    private ImageButton buttonAtrasPizza;
    private ImageButton buttonAvanzarPizza;

    private ArrayList<Pizza> pizzaArrayList;

    private RecyclerView mRecyclerView;
    private RVAdapterPizza mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Integer> itemClicked;
    private ArrayList<Pizza> selectedPizzas;
    private Cliente cliente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        pizzaArrayList = new ArrayList<>();
        pizzaArrayList.add(new Pizza("Barbacoa", "14€", R.drawable.bbqpizza, 0));
        pizzaArrayList.add(new Pizza("Magarita", "10€", R.drawable.margherita, 0));
        pizzaArrayList.add(new Pizza("Carbonara", "15€", R.drawable.carbonara, 0));
        pizzaArrayList.add(new Pizza("Hawaiana", "25€", R.drawable.hawaiana, 0));
        itemClicked = new ArrayList<>();
        selectedPizzas = new ArrayList<>();


        initRecyclerView();
    }

    public void initRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerViewPizza);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new RVAdapterPizza(pizzaArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnitemclickListener(position -> {
            Pizza pizzaActual = pizzaArrayList.get(position);
            pizzaActual.setCantidad(pizzaActual.getIntCantidad()+1);
            mAdapter.notifyItemChanged(position);
            itemClicked.add(position);
        });

    }

    /**
     * Vuelve a la actividad anterior.
     * @param v
     */
    public void atrasPizza(View v){
        buttonAtrasPizza = findViewById(R.id.buttonAtrasPizza);
        finish();
    }

    public void avanzarPizza(View v){
        checkPizzasSelected();
        if(selectedPizzas.size() == 0){
            Toast.makeText(getApplicationContext(), "No se ha seleccionado ninguna pizza", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BebidaActivity.class);
            intent.putExtra("Cliente", cliente);
            Log.i("SoloCliente", "avanzarPizza: Solo cliente");
            startActivity(intent);
        }else{
            buttonAtrasPizza = findViewById(R.id.ButtonAvanzarPizza);
            Intent intent = new Intent(this, BebidaActivity.class);
            intent.putExtra("Cliente", cliente);
            intent.putExtra("Pizzas", selectedPizzas);
            startActivity(intent);
            Log.i("pizzaSelected size", "avanzarPizza: " + selectedPizzas.size());
        }

    }

    /**
     * Resetea la activity Pizza
     * @param v
     */
    public void pizzaReset(View v){
        if(itemClicked.size() > 0) {
            for (int i = 0; i < itemClicked.size(); i++) {
                int position = itemClicked.get(i);
                pizzaArrayList.get(position).setCantidad(0);
                mAdapter.notifyItemChanged(position);
            }
            clearArrayLists();
        }
    }

    /**
     * Limpia el arraylist de items seleccionados.
     */
    private void clearArrayLists(){
        if(itemClicked.size() > 0) {
            itemClicked.clear();
        }
        if(selectedPizzas.size() > 0) {
            selectedPizzas.clear();
        }
    }

    /**
     * Recorre el arraylist de pizzas para ver cuál tiene un valor de más de 0 para añadirlas </br>
     * al arraylist selectedPizzas. Este array se pasará en el intent a la siguiente actividad.
     */
    private void checkPizzasSelected(){
        for(int i=0; i<pizzaArrayList.size();i++){
            Pizza pizzaActual = pizzaArrayList.get(i);
            if(pizzaActual.getIntCantidad() > 0){
                selectedPizzas.add(new Pizza(pizzaActual.getNombre(), pizzaActual.getPrecio(),
                        pizzaActual.getIntCantidad()));
            }
        }
    }



}