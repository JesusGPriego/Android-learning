package com.example.pizzeria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;

public class BebidaActivity extends AppCompatActivity {

    ArrayList<Bebida> bebidaArrayList;

    private RecyclerView mRecyclerView;
    private RVAdapterBebida mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Integer> itemClicked;
    private ArrayList<Pizza> selectedPizzas;
    private Cliente cliente;
    private ArrayList<Bebida> selectedBebidas;
    private CheckBox checkBoxOver18;

    private boolean thereRPizzas = false;
    private boolean over18 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida);


        bebidaArrayList = new ArrayList<>();
        bebidaArrayList.add(new Bebida("Agua", "2€", R.drawable.solan, 0));
        bebidaArrayList.add(new Bebida("Refresco", "3€", R.drawable.coca, 0));
        bebidaArrayList.add(new Bebida("Cerveza", "4€", R.drawable.cerveza, 0));

        cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        //Comprobando si el usuario ha seleccionado alguna pizza.
        try{
            selectedPizzas = (ArrayList<Pizza>)getIntent().getSerializableExtra("Pizzas");
            thereRPizzas = true;
        }catch(Exception e){
            Log.i("No Pizzas", "onCreate: No hay pizzas.");
        }
        checkBoxOver18 = findViewById(R.id.checkBoxOver18);
        itemClicked = new ArrayList<>();
        selectedBebidas = new ArrayList<>();
        initRecyclerVIew();

    }

    private void initRecyclerVIew(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RVAdapterBebida(bebidaArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnitemclickListener(position -> {
            Bebida bebidaActual = bebidaArrayList.get(position);
            bebidaActual.setCantidad(bebidaActual.getIntCantidad() + 1);
            mAdapter.notifyItemChanged(position);
            itemClicked.add(position);
            if(bebidaActual.getNombre().equals("Cerveza")){
                over18 = true;
            }
        });

    }

    public void goBack(View v){
        finish();
    }

    public void goEnd(View v){
        if(over18) {
            if(checkBoxOver18.isChecked()) {
                startFinalActivity();
            }else{
                popDialog();
            }
        }else{
            startFinalActivity();
        }
    }

    private void startFinalActivity() {
        checkSelectedBebidas();
        if(selectedBebidas.size()>0) {
            Intent intent = new Intent(this, FinalActivity.class);
            if (thereRPizzas) {
                intent.putExtra("Pizzas", selectedPizzas);
            }
            intent.putExtra("Cliente", cliente);
            intent.putExtra("Bebidas", selectedBebidas);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "No se ha seleccionado ninguna bebida", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FinalActivity.class);
            intent.putExtra("Cliente", cliente);
            if (thereRPizzas) {
                intent.putExtra("Pizzas", selectedPizzas);
            }
            Log.i("SoloCliente", "avanzarPizza: Solo cliente");
            startActivity(intent);
        }
    }

    private void checkSelectedBebidas(){
        for(int i=0; i<bebidaArrayList.size();i++){
            Bebida bebidaActual = bebidaArrayList.get(i);
            if(bebidaActual.getIntCantidad() > 0){
                selectedBebidas.add(new Bebida(bebidaActual.getNombre(), bebidaActual.getPrecio(),
                        bebidaActual.getIntCantidad()));
            }
        }
    }

    public void bebidaReset(View v){
        if(itemClicked.size() > 0) {
            for (int i = 0; i < itemClicked.size(); i++) {
                int position = itemClicked.get(i);
                bebidaArrayList.get(position).setCantidad(0);
                mAdapter.notifyItemChanged(position);
            }
            clearArrayLists();
        }
        if(checkBoxOver18.isChecked()){
            checkBoxOver18.toggle();
        }
        over18 = false;
    }

    private void clearArrayLists(){
        if(itemClicked.size() > 0) {
            itemClicked.clear();
        }
        if(selectedBebidas.size() > 0) {
            selectedBebidas.clear();
        }
    }

    private void popDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Eres mayor de edad?")
                .setMessage("No puedes comprar cerveza si no eres mayor de edad.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

}