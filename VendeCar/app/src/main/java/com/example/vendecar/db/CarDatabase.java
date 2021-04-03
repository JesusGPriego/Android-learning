package com.example.vendecar.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vendecar.db.dao.CarDao;
import com.example.vendecar.entity.Car;


//Letting Room know this is the Database itself.
//The only Entity it will create is Car class, which is a Dao
@Database(entities = Car.class, version = 1)
public abstract class CarDatabase extends RoomDatabase {
    //private static means only the class can create objects.
    //It will be only one instance at time.
    private static CarDatabase instance;
    //Dao contains all CRUD operations.
    //Room will autogenerate the necessary code to get a CarDao Object .
    public abstract CarDao carDao();
    //Synchronized means only one thread can access this method at a time.
    //If there is no instance yet, one will be created, else, it returns existing instance.
    public static synchronized  CarDatabase getInstance(Context context){
        if(instance == null){
            //If there is no db, Room builder generates an instance of CarDatabase.
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CarDatabase.class, "car_database")
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }

}
