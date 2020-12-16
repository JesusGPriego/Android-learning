package com.example.pizzeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FinalActivity extends AppCompatActivity {

    private ArrayList<Pizza> selectedPizzas;
    private ArrayList<Bebida> bebidasSeleccionadas;
    private Cliente cliente;

    private int subTotalPizzas = 0;
    private int subTotalBebidas = 0;

    private TableRow tableRowPizza1;
    private TableRow tableRowPizza2;
    private TableRow tableRowPizza3;
    private TableRow tableRowBebida1;
    private TableRow tableRowBebida2;
    private TableRow tableRowBebida3;
    private TableRow tableRowDireccion;

    private String tipoRecogida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        cliente = (Cliente) getIntent().getSerializableExtra("Cliente");

        try{
            selectedPizzas = (ArrayList<Pizza>)getIntent().getSerializableExtra("Pizzas");

            setPizzasSeleccionadasText();
        }catch(Exception e){
            Log.i("No Pizzas", "onCreate: No hay pizzas.");
        }

        try{
            bebidasSeleccionadas = (ArrayList<Bebida>) getIntent().getSerializableExtra("Bebidas");

            setBebidasSeleccionadasText();
        }catch(Exception e){
            Log.i("No Pizzas", "onCreate: No hay pizzas.");
        }




        setClienteText();
        setRecargoText();
        settextViewPrecioTotalText();
        setTime();


    }


    public void setPizzasSeleccionadasText(){
        TextView textViewPizzasSeleccionadas = findViewById(R.id.textViewPizzaSeleccionada);
        TextView textViewPrecioPizzas = findViewById(R.id.textViewPrecioPizzas);
        TextView textViewSubtotalPizzas = findViewById(R.id.textViewSubtotalPizzas);
        TextView textViewCantidadPizzas = findViewById(R.id.textViewCantidadPizzas);

        StringBuilder pizzas = new StringBuilder();
        StringBuilder precio = new StringBuilder();
        StringBuilder subtotal = new StringBuilder();
        StringBuilder cantidad = new StringBuilder();

        for(int i=0; i<selectedPizzas.size();i++){
            if(Integer.parseInt(selectedPizzas.get(i).getStringCantidad()) > 0) {
                Pizza pizzaActual = selectedPizzas.get(i);
                pizzas.append(pizzaActual.getNombre());
                precio.append(pizzaActual.getPrecio());
                subtotal.append(String.valueOf(Integer.parseInt(pizzaActual.getPrecio().replace("€", "")) *
                        Integer.parseInt(pizzaActual.getStringCantidad()))+"€");
                cantidad.append(pizzaActual.getStringCantidad());

                subTotalPizzas += Integer.parseInt(pizzaActual.getPrecio().replace("€", ""));

                if (i != selectedPizzas.size() - 1) {
                    pizzas.append("\n");
                    precio.append("\n");
                    subtotal.append("\n");
                    cantidad.append("\n");
                }
            }
        }
        makePizzaTextVisible();
        textViewPizzasSeleccionadas.setText(pizzas.toString());
        textViewPrecioPizzas.setText(precio.toString());
        textViewCantidadPizzas.setText(cantidad.toString());
        textViewSubtotalPizzas.setText(subtotal.toString());
    }

    public void setBebidasSeleccionadasText(){

        TextView textViewBebidasSeleccionada = findViewById(R.id.textViewBebidaSeleccionada);
        TextView textViewPrecioBebidas = findViewById(R.id.textViewPrecioBebidas);
        TextView textViewSubtotalBebidas = findViewById(R.id.textViewSubtotalBebidas);
        TextView textViewCantidadBebidas = findViewById(R.id.textViewCantidadBebidas);

        StringBuilder bebidas = new StringBuilder();
        StringBuilder precio = new StringBuilder();
        StringBuilder subtotal = new StringBuilder();
        StringBuilder cantidad = new StringBuilder();

        for(int i=0; i<bebidasSeleccionadas.size();i++){
            if(Integer.parseInt(bebidasSeleccionadas.get(i).getCantidad()) > 0) {
                Bebida bebidaActual = bebidasSeleccionadas.get(i);
                bebidas.append(bebidaActual.getNombre());
                precio.append(bebidaActual.getPrecio());
                subtotal.append(Integer.parseInt(bebidaActual.getPrecio().replace("€", "")) *
                        Integer.parseInt(bebidaActual.getCantidad()) + "€");
                cantidad.append(bebidaActual.getCantidad());

                subTotalBebidas += Integer.parseInt(bebidaActual.getPrecio().replace("€", ""));

                if (i != bebidasSeleccionadas.size() - 1) {
                    bebidas.append("\n");
                    precio.append("\n");
                    subtotal.append("\n");
                    cantidad.append("\n");
                }
            }
        }
        makeBebidaTextVisible();
        textViewBebidasSeleccionada.setText(bebidas.toString());
        textViewPrecioBebidas.setText(precio.toString());
        textViewCantidadBebidas.setText(cantidad.toString());
        textViewSubtotalBebidas.setText(subtotal.toString());

    }

    private void setClienteText(){
        TextView TextViewClienteNombre = findViewById(R.id.TextViewClienteNombre);
        TextView textViewClienteTelefono = findViewById(R.id.textViewClienteTelefono);
        TextView textViewClienteRecogida = findViewById(R.id.textViewClienteRecogida);
        TextView textViewClieneDireccion = findViewById(R.id.textViewClieneDireccion);
        tableRowDireccion = findViewById(R.id.tableRowDireccion);

        TextViewClienteNombre.setText(cliente.getNombre());
        textViewClienteTelefono.setText(cliente.getTelefono());
        textViewClienteRecogida.setText(cliente.getRecogida());
        tipoRecogida = cliente.getRecogida();

        if(cliente.getDireccion() != null){
            textViewClieneDireccion.setText(cliente.getDireccion());
            tableRowDireccion.setVisibility(View.VISIBLE);
        }

    }

    public void resetApp(View v){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    private void setRecargoText(){
        TextView textViewRecargo = findViewById(R.id.textViewRecargo);
        if(tipoRecogida.equals("A domicilio")){
            textViewRecargo.setText("2€");
        }else{
            textViewRecargo.setText("No");
        }
    }

    private void settextViewPrecioTotalText(){
        TextView textViewPrecioTotal = findViewById(R.id.textViewPrecioTotal);
        int recargo = 0;
        if(tipoRecogida.equals("A domicilio")){
            recargo = 2;
        }
        int precioTotal = subTotalBebidas + subTotalPizzas + recargo;
        textViewPrecioTotal.setText(String.valueOf(precioTotal) + "€");
    }


    private void setTime(){
        TextView textView=findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy',' HH:mm");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);
    }

    private void makePizzaTextVisible(){
        tableRowPizza1 = findViewById(R.id.tableRowPizza1);
        tableRowPizza2 = findViewById(R.id.tableRowPizza2);
        tableRowPizza3 = findViewById(R.id.tableRowPizza3);

        tableRowPizza2.setVisibility(View.VISIBLE);
        tableRowPizza1.setVisibility(View.VISIBLE);
        tableRowPizza3.setVisibility(View.VISIBLE);
    }

    private void makeBebidaTextVisible(){
        tableRowBebida1 = findViewById(R.id.tableRowBebida1);
        tableRowBebida2 = findViewById(R.id.tableRowBebida2);
        tableRowBebida3 = findViewById(R.id.tableRowBebida3);

        tableRowBebida2.setVisibility(View.VISIBLE);
        tableRowBebida1.setVisibility(View.VISIBLE);
        tableRowBebida3.setVisibility(View.VISIBLE);
    }
}