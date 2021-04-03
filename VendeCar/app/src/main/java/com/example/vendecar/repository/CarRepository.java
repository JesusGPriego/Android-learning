package com.example.vendecar.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.vendecar.db.CarDatabase;
import com.example.vendecar.db.dao.CarDao;
import com.example.vendecar.entity.Car;

import java.util.List;

//Repository manage access to information no matter from where it comes (Web or DB).
public class CarRepository {

    /**
     * Dao it will use to gett / manipulate info.
     */
    private CarDao carDao;
    /**
     * All cars in the DB.
     */
    private LiveData<List<Car>> allCar;

    public CarRepository(Application application){
        //Creates carDatabase object for a concrete context.
        //It calls CarDatabase.getInstance method, which will creates the database if it doesnt exist
        //and will return the database instance.
        CarDatabase carDatabase = CarDatabase.getInstance(application);
        //Calling abstract method is possible since Room autogenerates the code that returns a
        //carDao object.
        carDao = carDatabase.carDao();
        //Now we can get the info to populate our lists.:
        allCar = carDao.getAllCars();
    }
    //Geting carDao info
    public LiveData<List<Car>> getAllCar(){
        return allCar;
    }
    //CRUD Operations:
    //Basically here everytime we need to do one of these operations, we access the Dao Object and execute
    //its methods.
    public void insert(Car car){
        new InsertCarAsyncTask(carDao).execute(car);
    }

    public void update(Car car){
        new UpdateCarAsyncTask(carDao).execute(car);
    }

    public void delete(Car car){
        new DeleteCarAsyncTask(carDao).execute(car);
    }


    //AsyncTask are needed here since Room doesnt allow database operation in main thread.
    //insert
    public static class InsertCarAsyncTask extends AsyncTask<Car, Void, Void>{

        private CarDao carDao;
        //We get carDao from Constructor so we can make db operations.
        public InsertCarAsyncTask(CarDao carDao){
            this.carDao = carDao;
        }

        @Override
        protected Void doInBackground(Car... cars) {
            //Inserting new register:
            carDao.insert(cars[0]);

            return null;
        }
    }
    //Update
    public static class UpdateCarAsyncTask extends AsyncTask<Car, Void, Void>{

        private CarDao carDao;
        //We get carDao from Constructor so we can make db operations.
        public UpdateCarAsyncTask(CarDao carDao){
            this.carDao = carDao;
        }

        @Override
        protected Void doInBackground(Car... cars) {
            //Inserting new register:
            carDao.update(cars[0]);

            return null;
        }
    }

    //Delete
    public static class DeleteCarAsyncTask extends AsyncTask<Car, Void, Void>{

        private CarDao carDao;
        //We get carDao from Constructor so we can make db operations.
        public DeleteCarAsyncTask(CarDao carDao){
            this.carDao = carDao;
        }

        @Override
        protected Void doInBackground(Car... cars) {
            //Inserting new register:
            carDao.delete(cars[0]);

            return null;
        }
    }


}
