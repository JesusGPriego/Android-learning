package com.example.locationsaver;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    /**
     * listview that shows info stored in locationArrayList.
     */
    private ListView locationListView;
    /**
     * stores locations' addresses.
     */
    public static List<String> locationAddressArrayList;
    /**
     * links the locationArrayList to the locationListView.
     */
    public static ArrayAdapter<String> locationArrayAdapter;

    public static List<LatLng> savedLatLng = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //vars init here.
        locationListView = findViewById(R.id.locationListView);
        locationAddressArrayList = new ArrayList<>();
        locationAddressArrayList.add(("Add new loation"));
        locationArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationAddressArrayList);
        locationListView.setAdapter(locationArrayAdapter);

        //Method call here.
        onLocationListViewClicked();
    }


    public void onLocationListViewClicked() {
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("listPosition", i);
                startActivity(intent);
            }
        });
    }


}