package com.example.vendecar.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vendecar.entity.Car;
import com.example.vendecar.repository.CarRepository;

import java.util.List;

/**
 * Gets the info that the activities will Display.
 * Activitys get info from here, so they dont need to call all these methods itself in every
 * start. It makes the app faster.
 */
public class CarViewModel extends AndroidViewModel {

    private CarRepository carRepository;
    private LiveData<List<Car>> allCars;


    public CarViewModel(@NonNull Application application) {
        super(application);

        carRepository = new CarRepository(application);
        allCars = carRepository.getAllCar();

    }

    //CRUD operations:
    //Insert
    public void insert(Car car){
        carRepository.insert(car);
    }
    //Update
    public void update(Car car){
        carRepository.update(car);
    }
    //Delete
    public void delete(Car car){carRepository.delete(car);}

    //Getting info:
    public LiveData<List<Car>> getAllCars(){
        return allCars;
    }

}
