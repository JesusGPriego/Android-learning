package com.example.vendecar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.vendecar.R;
import com.example.vendecar.entity.Car;

import java.util.ArrayList;

/**
 * Shows all car attributes (but id) so we can add / edit a car, depending on how we get to here.
 */
public class AddEditCarActivity extends AppCompatActivity {
    /**
     * It shows all the available car's brand, User can only select one at a time.
     */
    private Spinner spinnerMarca;
    /**
     * App gets car model info from here.
     */
    private EditText editTextModelo;
    /**
     * App gets car total kilometers from here
     */
    private EditText editTextkm;
    /**
     * App gets car registration year from here.
     */
    private EditText editTextAnio;
    /**
     * App gets car Ccs from here.
     */
    private EditText editTextCc;
    /**
     * App gets car's total horse power from here.
     */
    private EditText editTextCv;
    /**
     * App gets car's price from here.
     */
    private EditText editTextPrecio;
    /**
     * If checked means car is sold, else it isn't.
     */
    private CheckBox checkBoxSold;
    /**
     * populating spinner info with this array.
     */
    private ArrayList<String> spinnerValues;
    /**
     * Car's brand.
     */
    private String marca;
    /**
     * ViewModel from which this activity gets info.
     */
    private CarViewModel carViewModel;
    /**
     * if true, it will show car's info in its respective field (EditActivity enabled).
     * Else, it allow the user to add a new car.
     */
    private boolean edit = false;
    /**
     * car's id (to use in Edit mode).
     */
    private int id = -1;

    //constants to get intent info:
    public static final String EXTRA_ID =
            "com.example.garciapriegojesus03.ui.EXTRA_ID";
    public static final String EXTRA_MARCA =
            "com.example.garciapriegojesus03.ui.EXTRA_MARCA";
    public static final String EXTRA_MODELO =
            "com.example.garciapriegojesus03.ui.iEXTRA_MODELO";
    public static final String EXTRA_ANIO =
            "com.example.garciapriegojesus03.ui.EXTRA_ANIO";
    public static final String EXTRA_KM =
            "com.example.garciapriegojesus03.ui.EXTRA_KM";
    public static final String EXTRA_CC =
            "com.example.garciapriegojesus03.ui.EXTRA_CC";
    public static final String EXTRA_CV =
            "com.example.garciapriegojesus03.ui.EXTRA_CV";
    public static final String EXTRA_PRECIO =
            "com.example.garciapriegojesus03.ui.iEXTRA_PRECIO";
    public static final String EXTRA_VENDIDO =
            "com.example.garciapriegojesus03.ui.EXTRA_VENDIDO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        //Settin our vars up.
        editTextModelo = findViewById(R.id.editTextModelo);
        editTextkm = findViewById(R.id.editTextKm);
        editTextAnio = findViewById(R.id.editTextAnio);
        editTextCc = findViewById(R.id.editTextCc);
        editTextCv = findViewById(R.id.editTextCv);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        checkBoxSold = findViewById(R.id.checkBoxSold);
        spinnerValues = new ArrayList<>();
        //Setting viewmodel up.
        carViewModel = new ViewModelProvider(this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(CarViewModel.class);
        //Setting actionbar up.
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        //Setting spiner up.
        setSpinnerMarca();
        Intent intent = getIntent();

        EditOrAdd(intent);


    }

    /**
     *
     * @param intent If it has info, the activity shows EditMode, else, it shows NewCarMode.
     */
    private void EditOrAdd(Intent intent) {
        //If it has info:
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Editar Coche");
            marca = intent.getStringExtra(EXTRA_MARCA);
            getMarcaPosition();
            editTextModelo.setText(intent.getStringExtra(EXTRA_MODELO));
            editTextkm.setText(String.valueOf(intent.getIntExtra(EXTRA_KM, 0)));
            editTextAnio.setText(String.valueOf(intent.getIntExtra(EXTRA_ANIO, 0)));
            editTextCc.setText(String.valueOf(intent.getIntExtra(EXTRA_CC, 0)));
            editTextCv.setText(String.valueOf(intent.getIntExtra(EXTRA_CV, 0)));
            editTextPrecio.setText(String.valueOf(intent.getIntExtra(EXTRA_PRECIO, 0)));
            int vendido = intent.getIntExtra(EXTRA_VENDIDO, 0);
            if(vendido == 0){
                checkBoxSold.setChecked(false);
            }else{
                checkBoxSold.setChecked(true);
            }

            id = intent.getIntExtra(EXTRA_ID, -1);
            edit = true;
        //Else
        }else {
            setTitle("Añadir Coche");
        }
    }

    /**
     * Populates the spinner with info and set its layout.
     */
    private void setSpinnerMarca() {
        spinnerMarca = findViewById(R.id.spinnerMarca);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.marcas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerMarca.setAdapter(adapter);
    }

    /**
     * Read fields info. If edit, and everything correct, it will edit an existing car.
     * Else, it will insert a new car.
     */
    private void insertOrUpdateCar(){
        String marca = spinnerMarca.getSelectedItem().toString();
        String modelo = editTextModelo.getText().toString();
        int km = -1;
        int anio = -1;
        int cc = -1;
        int cv = -1;
        int precio = -1;
        int vendido;

        if(checkBoxSold.isChecked()){
            vendido=1;
        }else{
            vendido = 0;
        }
        //try-catch. No empty fields allowed here.

        String stringKm = editTextkm.getText().toString();
        String stringAnio = editTextAnio.getText().toString();
        String stringCc = editTextCc.getText().toString();
        String stringCv = editTextCv.getText().toString();
        String stringprecio = editTextPrecio.getText().toString();

        if(stringKm.trim().isEmpty() || stringAnio.trim().isEmpty() || stringCc.trim().isEmpty()
                || stringCv.trim().isEmpty() || stringprecio.trim().isEmpty()){
            Toast.makeText(this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show();
        }else{
             km = Integer.parseInt(editTextkm.getText().toString());
             anio = Integer.parseInt(editTextAnio.getText().toString());
             cc = Integer.parseInt(editTextCc.getText().toString());
             cv = Integer.parseInt(editTextCv.getText().toString());
             precio = Integer.parseInt(editTextPrecio.getText().toString());
        }
        //If no brand selected (default is), update / insert is no allowed.
        if(marca.equals("Seleccione marca")){
            Toast.makeText(this, "Seleccione una marca.", Toast.LENGTH_SHORT).show();
        }
        //If model field is empty, update / insert is no allowed.
        else if(modelo.trim().isEmpty() || km <0 || anio < 0 || cc < 0 || cv < 0 || precio < 0){
            Toast.makeText(this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show();
        }
        else{
            //Creating car with the fields data.
            Car car = new Car(marca, modelo, km,anio, cc, cv, precio, vendido);
            //Edit Mode. Update existing car.
            if(edit){
                //Set cars id so Room will update an existing car.
                car.setId(id);
                carViewModel.update(car);
                Toast.makeText(this, "Coche actualizado", Toast.LENGTH_SHORT).show();
            //Else insert new car.
            }else {
                carViewModel.insert(car);
                Toast.makeText(this, "Coche guardado.", Toast.LENGTH_SHORT).show();
            }
            //Finish the activity once the car is inserted / updated.
            finish();
        }
    }
    //Setting toolbar menu up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit_car_menu, menu);
        return true;
    }
    //OnItemselected.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveCar:

                insertOrUpdateCar();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method here guesses with option is selected in CarListActivity, if edit mode is running.
     */
    public void getMarcaPosition(){
       String st1 = "Seleccione marca";
       String st2 = "BMW";
       String st3 = "Audi";
       String st4 = "Seat";
       String st5 = "Ford";
       String st6 = "Nissan";
       String st7 = "Citroen";
       String st8 = "Renault";
       String st9 = "Kia";

       spinnerValues.add(st1);
       spinnerValues.add(st2);
       spinnerValues.add(st3);
       spinnerValues.add(st4);
       spinnerValues.add(st5);
       spinnerValues.add(st6);
       spinnerValues.add(st7);
       spinnerValues.add(st8);
       spinnerValues.add(st9);

       for(int i=0;i<spinnerValues.size(); i++){
           if(spinnerValues.get(i).equals(marca)){
               spinnerMarca.setSelection(i);
           }
        }


    }
}