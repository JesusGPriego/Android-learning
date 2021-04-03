package com.example.vendecar.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vendecar.entity.Car;

import java.util.List;

//Interface or abstract class.
//Room will autogenerate the code so we can operate with the database..
//Daos are used to manage operations with the database.
@Dao
public interface CarDao {
    //Insert method. Room uses this method as insert.
    @Insert
    void insert(Car car);
    //Same logic as Insert.
    @Update
    void update(Car car);
    //Delete method.
    @Delete
    void delete(Car car);

    //Delete all registers in table.
    @Query("DELETE FROM car_table")
    void deleteAllCar();

    //Get all registers from table::
    @Query("SELECT * FROM car_table ORDER BY id DESC")
    LiveData<List<Car>> getAllCars();

    //LiveData notificates when changes are made, so we can update our list as soon as they are made.
}
