package com.example.locationsaver;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.locationsaver.MainActivity.savedLatLng;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    /**
     * ArrayList that stores the locations saved.
     */

    private int listPosition;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Intent intent;
    private Location lastKnownLocation;
    private LatLng lastKnownLatLng;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Vars init.
        intent = getIntent();
        listPosition = intent.getIntExtra("listPosition", 0);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        sharedPreferences = this.getSharedPreferences("com.example.locationsaver", Context.MODE_PRIVATE);
        //Method calls
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        listPosition = intent.getIntExtra("listPosition", 0);
        setUpLocationListener();
        if(listPosition == 0) {
            mMap.clear();
            moveCameraToLocation(lastKnownLatLng, "You are here");
        }else{
           LatLng savedLatLng = MainActivity.savedLatLng.get(listPosition-1);
           moveCameraToLocation(savedLatLng, intent.getStringExtra("Address"));
        }
    }

    /**
     * Asking for GPS permission.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //If permission is granted, it clear map, seta marker and moves to the location.
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                lastKnownLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void setUpLocationListener(){
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };
        //Checking iff permmissions is granted or not.
        //If it is granted, moves the camare and adds a marker to the last known position.
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            moveCameraToLocation(lastKnownLatLng, "title");
        }else{ //if not, asks for permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /**
     * Moves the camera to the location
     * @param latLng location where the user is.
     * @param title Is the marker's title.    public void saveDataIntoSharedPreferences(){
        String stringToSave = "";
        for (int i=0;i<)
    }

     */
    public void moveCameraToLocation(LatLng latLng, String title){
        if(latLng!=null) {

            mMap.addMarker(new MarkerOptions().position(latLng).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        }
    }

    /**
     * on long click, adds a marker to the map, getting it address and adds </br>
     * it to the locationAddressArrayList, adding the latLng to the savedLatLng arrayList.
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        String address = getAddressFromGeocoder(latLng.latitude, latLng.longitude);
        mMap.addMarker(new MarkerOptions()
            .position(latLng)
            .title(address));
        MainActivity.locationAddressArrayList.add(address);
        savedLatLng.add(latLng);
        MainActivity.locationArrayAdapter.notifyDataSetChanged();
        saveAddressData(sharedPreferences);
        Toast.makeText(getApplicationContext(), "Place added", Toast.LENGTH_SHORT).show();
    }

    /**
     * gets info from a given location. If no info is found, then it sets current date as address name. </br>
     * this method will be called in onMapLongClick method to set the marker title and the new adddress to </br>
     * the locationAddressArrayList.
     * @param latitude required value to init the geocoder.
     * @param longitude required value to init the geocoder.
     * @return
     */
    public String getAddressFromGeocoder(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";

        try{
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if(addressList != null && addressList.size()>0){
                if(addressList.get(0).getThoroughfare()!=null){
                    if(addressList.get(0).getSubThoroughfare()!=null){
                        address += addressList.get(0).getSubThoroughfare() + " ";
                    }
                    address += addressList.get(0).getThoroughfare();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(address.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
            address += sdf.format(new Date());
        }

        return address;
    }

    /**
     * Converts locationAddresssArrayList and savedLatLng data </br>
     * into Strings. Then writes those strings in sharedPreferences.
     * @param sharedPreferences this is where the data will be stored.
     */
    public static void saveAddressData(SharedPreferences sharedPreferences) {

        String addressToSave;
        String latitudesToSave;
        String longitudesToSave;
        StringBuilder addressSB = new StringBuilder();
        StringBuilder latsSB = new StringBuilder();
        StringBuilder longsSB = new StringBuilder();

        for (int i = 0; i < MainActivity.locationAddressArrayList.size(); i++) {
            addressSB.append(MainActivity.locationAddressArrayList.get(i));
            if (i < MainActivity.locationAddressArrayList.size() - 1) {
                addressSB.append("%20");
            }
        }
        for (int i = 0; i < savedLatLng.size(); i++) {
            latsSB.append(savedLatLng.get(i).latitude);
            longsSB.append(savedLatLng.get(i).longitude);
            latsSB.append("%20");
            longsSB.append("%20");
        }
        addressToSave = addressSB.toString();
        latitudesToSave = latsSB.toString();
        longitudesToSave = longsSB.toString();
        sharedPreferences.edit().putString("Addresses", addressToSave).apply();
        sharedPreferences.edit().putString("latitudes", latitudesToSave).apply();
        sharedPreferences.edit().putString("longitudes", longitudesToSave).apply();


    }

}