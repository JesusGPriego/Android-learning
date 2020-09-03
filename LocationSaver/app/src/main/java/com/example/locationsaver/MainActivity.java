package com.example.locationsaver;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements EditAddressDialog.EditAddressDialogListener {
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
    /**
     * Array to save LatLngs.
     */
    public static List<LatLng> savedLatLng = new ArrayList<>();

    private String newAddress;
    private int index;
    private boolean delete;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //vars init here.
        locationListView = findViewById(R.id.locationListView);
        locationAddressArrayList = new ArrayList<>();
        locationArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationAddressArrayList);
        locationListView.setAdapter(locationArrayAdapter);
        sharedPreferences = this.getSharedPreferences("com.example.locationsaver", Context.MODE_PRIVATE);
        delete = false;
        index = -1;
        //Method call here.
        onLocationListViewClicked();
        getSavedAddressData();
        onLocationListViewLongClicked();
        if(locationAddressArrayList.size()==0){
            locationAddressArrayList.add("Add new place");
        }
    }


    public void onLocationListViewClicked() {
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("listPosition", i);
                intent.putExtra("Address", locationAddressArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    /**
     * On long click, a dialog is showed. Here the user can edit or remove the selected </br>
     * item.
     */
    public void onLocationListViewLongClicked(){
        locationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                openDialog();
                index=i;
                return true;
            }
        });
    }

    /**
     * if there's info saved, it takes it and set the data in the arrays.
     */
    public void getSavedAddressData(){
        String savedData = sharedPreferences.getString("Addresses", "");
        if(savedData.length()>1) {
            String[] addressArray = savedData.split("%20");
            locationAddressArrayList.addAll(Arrays.asList(addressArray));
        }
        String savedLatitudes = sharedPreferences.getString("latitudes", "");
        if(savedLatitudes.length()>1) {
            String[] latitudesArray = savedLatitudes.split("%20");
            String savedLongitudes = sharedPreferences.getString("longitudes", "0");
            String[] longitudesArray = savedLongitudes.split("%20");

            for (int i = 0; i < latitudesArray.length; i++) {
                LatLng savedLatlng = new LatLng(
                        Double.parseDouble(latitudesArray[i]),
                        Double.parseDouble(longitudesArray[i]));
                savedLatLng.add(savedLatlng);
            }
        }

    }

    /**
     * deletes all the data. </br>
     * its not like i find this tool useful, but just wanted to show other skill.
     *
     * @param view
     */
    public void deleteList(View view){
        locationAddressArrayList.clear();
        locationAddressArrayList.add("Add new place");
        savedLatLng.clear();
        locationArrayAdapter.notifyDataSetChanged();
        sharedPreferences.edit().clear().apply();
        Toast.makeText(getApplicationContext(), "Data cleared" ,Toast.LENGTH_SHORT).show();
    }

    /**
     * calls an instace of EditAddressDialog class.
     */
    public void openDialog(){
        EditAddressDialog editAddressDialog = new EditAddressDialog();
        editAddressDialog.show(getSupportFragmentManager(), "EditTextDialog");
    }

    /**
     * If the user taps edit button in the dialog, address is updated.
     * @param address
     */
    @Override
    public void applyText(String address) {
        newAddress = address;
        locationAddressArrayList.set(index, address);
        locationArrayAdapter.notifyDataSetChanged();
        MapsActivity.saveAddressData(sharedPreferences);
        Toast.makeText(getApplicationContext(), "Address updated", Toast.LENGTH_SHORT).show();
    }

    /**
     * if the user taps the delete button in the dialog, address is deleted, and so </br>
     * its LatLng
     * @param delete
     */
    @Override
    public void deleteText(boolean delete) {
        locationAddressArrayList.remove(index);
        savedLatLng.remove(index-1);
        locationArrayAdapter.notifyDataSetChanged();
        MapsActivity.saveAddressData(sharedPreferences);
        Toast.makeText(getApplicationContext(), "Place deleted", Toast.LENGTH_SHORT).show();
    }
}