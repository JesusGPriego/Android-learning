package com.example.pizzeria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {


    ArrayList<String> tipoRecogida;
    ImageButton buttonAtras;
    ImageButton buttonAvance;
    //Datos necesarios para crear el cliente
    String nombre;
    String telefono;
    String recogida;
    String direccion;
    //EditTexts desde donde obtendremos la información para crear el cliente.
    EditText editTextNombre;
    EditText editTextTelefono;
    EditText editTextDireccion;
    Spinner spinnerRecogida;
    //Cliente:
    Cliente cliente;
    TextView direccionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        direccionTextView = findViewById(R.id.direccionTextView);

        //Iniciando la lista
        initiateList();
        //llamamos al metodo spinnersetup
        spinnerSetUp();


    }
    private void spinnerSetUp(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipoRecogida);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRecogida=findViewById(R.id.spinnerRecogida);
        spinnerRecogida.setAdapter(adapter);
        spinnerchanged();


    }

    private void initiateList(){
        tipoRecogida = new ArrayList<>();
        tipoRecogida.add("Local");
        tipoRecogida.add("A domicilio");
    }

    public void atras(View view){
        buttonAtras = findViewById(R.id.buttonAtras);
        finish();
    }

    /**
     * Usa los métodos checkContent y checkSpinnerItem para crear el cilente. Se ejecutará </br>
     * este método cuando se quiera avanzar a la siguiente ventana. Si faltan datos, </br>
     * aparecerá un diálogo informando de que faltan datos.
     */
    public void checkCliente(View v) {

        if(editTextDireccion.getVisibility() == View.INVISIBLE){
            if(checkEditText(editTextNombre) && checkEditText(editTextTelefono)){
                nombre = editTextNombre.getText().toString();
                telefono = editTextTelefono.getText().toString();
                cliente = new Cliente(nombre, telefono, recogida);
                Log.i("Cliente x3", "Nombre: " + cliente.getNombre() + " Telefono: " +
                        cliente.getTelefono() + " Recogida: " + cliente.getRecogida());
                nextActivity();
            }else{
                popDialog();
            }

        }else{
            if(checkEditText(editTextNombre) && checkEditText(editTextTelefono) && checkEditText(editTextDireccion)){
                nombre = editTextNombre.getText().toString();
                telefono = editTextTelefono.getText().toString();
                direccion = editTextDireccion.getText().toString();

                cliente = new Cliente(nombre, telefono, recogida, direccion);
                Log.i("Cliente x4", "Nombre: " + cliente.getNombre() + " Telefono: " +
                        cliente.getTelefono() + " Recogida: " + cliente.getRecogida() + " Direccion: "
                        + cliente.getDireccion());
                nextActivity();

            }else{
                popDialog();
            }

        }

    }

    /**
     * Comprubea que el editText que se le pasa por argumento no esté vacío. </br>
     * de esta forma podremos determinar si se puede pasar o no a la siguietne activity.
     * @param editText
     * @return
     */
    private boolean checkEditText(EditText editText){
        if(!editText.getText().toString().equals("")) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Comprueba el valor del spinner, para que el editTextDireccion sea o no visible.
     */
    private void spinnerchanged(){

        spinnerRecogida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int getid = parent.getSelectedItemPosition();   ///get the selected element place id
                Log.i("IDspinner", "Position of selected element:" + getid);
                recogida = String.valueOf(parent.getItemAtPosition(position));   // getting the selected element value
                Log.i("ValueSpinner", "onItemSelected: " + recogida);

                if(recogida.equals("A domicilio")){
                    editTextDireccion.setVisibility(View.VISIBLE);
                    direccionTextView.setVisibility(View.VISIBLE);
                }else{
                    editTextDireccion.setVisibility(View.INVISIBLE);
                    direccionTextView.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void nextActivity(){
        Intent intent = new Intent(this, PizzaActivity.class);
        intent.putExtra("Cliente", cliente);
        Log.i("Direccion", "Cliente direccion: " + cliente.getDireccion());
        startActivity(intent);
    }

    private void popDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Revise los datos")
                .setMessage("Por favor, rellene todos los campos")
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