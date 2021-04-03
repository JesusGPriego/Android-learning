package com.example.vendecar.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendecar.R;
import com.example.vendecar.adapter.CarAdapter;
import com.example.vendecar.entity.Car;

import java.util.ArrayList;
import java.util.List;

public class CarListActivity extends AppCompatActivity {
    /**
     * RecyclerView to display Data.
     */
    private RecyclerView recyclerViewLaunh;
    /**
     * Our custom ListAdapter recyclers view needs to work.
     */
    private CarAdapter carAdapter;
    /**
     * ViewModel from which this activity works with data.
     */
    private CarViewModel carViewModel;
    private SharedPreferences sharedPreferences;
    private List<Car> allCars;
    private List<Car> allNonSoldCars;

    private boolean showSoldCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        showSoldCars = sharedPreferences.getBoolean("only_sold_cars", false);
        allCars = new ArrayList<>();
        allNonSoldCars = new ArrayList<>();
        //Linking the activity to a viewmodel
        carViewModel = new ViewModelProvider(this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(CarViewModel.class);
        //This method here starts our recyclerView with all its necessary components.
        setRecyclerViewLaunch();
        //getting data for our recycler through carViewModel.get all cars.
        //It returns a LiveData, so it will update every time any change happens.

        carViewModel.getAllCars().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                allCars = cars;
                if(showSoldCars) {
                    ArrayList<Car> nonSoldCars = new ArrayList<>();

                    for (int i = 0; i < allCars.size(); i++) {
                        if (allCars.get(i).getVendido() == 0) {
                            nonSoldCars.add((allCars.get(i)));
                        }
                    }
                    if(nonSoldCars.size()==0) {
                        Toast.makeText(CarListActivity.this, "No hay resultados que mostrar", Toast.LENGTH_SHORT).show();
                    }

                    carAdapter.submitList(nonSoldCars);
                }else{
                    if(allCars.size()>0) {

                    }else{
                        Toast.makeText(CarListActivity.this, "No hay resultados que mostrar", Toast.LENGTH_SHORT).show();
                    }
                    carAdapter.submitList(allCars);
                }

            }
        });
        //Setting toolbar up
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        setTitle("Lista de Coches");
    }

    /**
     * Basically initiates our RecyclerView and its components.
     * Also set recyclerview.onclicklistener.
     * If any element of the recyclerview is clicked, it get us to EditActivity.
     */
    public void setRecyclerViewLaunch() {
        recyclerViewLaunh = findViewById(R.id.recyclerViewLaunch);
        recyclerViewLaunh.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLaunh.setHasFixedSize(true);
        carAdapter = new CarAdapter();
        recyclerViewLaunh.setAdapter(carAdapter);
        carAdapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Car car) {
             onRecyclerViewClick(car);
            }
        });
    }

    /**
     * Setting up our menu.
     * @param menu the menu this activity's custom bar will display.
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.launch_menu, menu);

        return true;
    }

    /**
     * Different trigger for every option.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemPreferences:
                //Open Preferences.
                goToPreferences();
                return true;
            case R.id.menuItemAddCar:
                //Add Car
                goToNewCar();
                return true;
            case R.id.menuItemDeleteCar:
                //Delete car
                goToDelete();
                return true;
            case R.id.sampleList:
                createSampleList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Start MyPreferences Activity
     */
    public void goToPreferences(){
        Intent intent = new Intent(this, MyPreferences.class);
        startActivityForResult(intent, 2);
    }

    /**
     * Start AddEditNewCar (Add version)
     */
    public void goToNewCar(){
        Intent intent = new Intent(this, AddEditCarActivity.class);

        startActivity(intent);
    }



    /**
     * Start DeleteCarActivity.
     */
    private void goToDelete(){
        Intent intent = new Intent(this, DeleteCarActivity.class);
        startActivity(intent);
    }

    /**
     * Start AddEditCarActivity (Edit Version)
     * It uses intent.putExtra here to set in that activity the data it has to display.
     * It uses AddEditCarActivity constants to pass info.
     * @param car It gets this car info and pass it to AddEditCarActivity.
     */
    private void onRecyclerViewClick(Car car){
        Intent intent = new Intent(CarListActivity.this, AddEditCarActivity.class);
        intent.putExtra(AddEditCarActivity.EXTRA_ID,car.getId());
        intent.putExtra(AddEditCarActivity.EXTRA_MARCA,car.getMarca());
        intent.putExtra(AddEditCarActivity.EXTRA_MODELO,car.getModelo());
        intent.putExtra(AddEditCarActivity.EXTRA_KM,car.getKm());
        intent.putExtra(AddEditCarActivity.EXTRA_ANIO,car.getAnio());
        intent.putExtra(AddEditCarActivity.EXTRA_CC,car.getCc());
        intent.putExtra(AddEditCarActivity.EXTRA_CV,car.getCv());
        intent.putExtra(AddEditCarActivity.EXTRA_PRECIO,car.getPrecio());
        intent.putExtra(AddEditCarActivity.EXTRA_VENDIDO,car.getVendido());
        startActivity(intent);
    }

    private void createSampleList(){
        Car car1 = new Car("Seat", "Ibiza", 20500, 2015, 1600, 110, 7500, 0);
        Car car2 = new Car("Audi", "A3", 103000, 2012, 2000, 150, 15000, 1);
        Car car3 = new Car("Citroen", "C5", 8500, 2014, 1900, 140, 9000, 0);
        Car car4 = new Car("Nissan", "Qashqai", 34000, 2018, 1600, 110, 12000, 0);
        Car car5 = new Car("Renault", "Clio", 10000, 2017, 1100, 90, 8000, 1);

        carViewModel.insert(car1);
        carViewModel.insert(car2);
        carViewModel.insert(car3);
        carViewModel.insert(car4);
        carViewModel.insert(car5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showSoldCars = sharedPreferences.getBoolean("only_sold_cars", false);
        if(requestCode == 2){
            if(showSoldCars) {
                ArrayList<Car> nonSoldCars = new ArrayList<>();

                for (int i = 0; i < allCars.size(); i++) {
                    if (allCars.get(i).getVendido() == 0) {
                        nonSoldCars.add((allCars.get(i)));
                    }
                }
                if(nonSoldCars.size()==0) {
                    Toast.makeText(CarListActivity.this, "No hay resultados que mostrar", Toast.LENGTH_SHORT).show();
                }

                carAdapter.submitList(nonSoldCars);
            }else{
                if(allCars.size()>0) {

                }else{
                    Toast.makeText(CarListActivity.this, "No hay resultados que mostrar", Toast.LENGTH_SHORT).show();
                }
                carAdapter.submitList(allCars);
            }

        }
    }
}