package com.example.vendecar.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendecar.R;
import com.example.vendecar.adapter.CarAdapter;
import com.example.vendecar.entity.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * show cars info and allow the user to delete one entry at a time.
 */
public class DeleteCarActivity extends AppCompatActivity {
    /**
     * RecyclerView that display car info.
     */
    private RecyclerView recyclerViewDelete;
    /**
     * Adapter that the recyclerView uses.
     */
    private CarAdapter carAdapter;
    /**
     * CarViewModel from which the activity gets info.
     */
    private CarViewModel carViewModel;
    /**
     * CarAdapter shows this list info.
     */
    private List<Car> cars;
    private List<Car> carsToSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_car);
        //Program checks for sharedPreferences state to display one or other info.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean modo = sharedPreferences.getBoolean("only_sold_cars", false);
        //List
        cars = new ArrayList<>();
        //ViewModel from which the Activity gets info.
        carViewModel = new ViewModelProvider(this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(CarViewModel.class);
        //Setting recyclerView
        setRecyclerView();
        //Getting cars info from the adapter.
        cars = carAdapter.getSavedCars();
        if(modo) {
            carsToSell = new ArrayList<>();
            for(int i=0;i<cars.size();i++){
                if(cars.get(i).getVendido() == 0){
                    carsToSell.add((cars.get(i)));
                }
            }
            carAdapter.submitList(carsToSell);
            //telling the adapter what info to show.

        }else{
            carAdapter.submitList(cars);
        }
        //setting action bar.
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        setTitle("Eliminar Coche");
    }

    private void setRecyclerView() {
        recyclerViewDelete = findViewById(R.id.recyclerViewDelete);
        recyclerViewDelete.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new CarAdapter(CarAdapter.DELETE_CARS, new CarAdapter.MyAdapterListener() {
            @Override
            public void iconImageViewOnClick(View v, int position) {
                popDialog(position);
            }
        });
        recyclerViewDelete.setAdapter(carAdapter);
    }
    /**
     * If delete icon is clicked, this dialog will pop. If "Si" clicked, the item will be deleted fom the
     * Database. Else, nothing happens.
     */
    private void popDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Borrar coche?")
                .setMessage("Confirmar borrar coche.")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        carViewModel.delete(cars.get(position));
                        Toast.makeText(DeleteCarActivity.this, "Coche Borrado.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create();
        builder.show();
    }
}