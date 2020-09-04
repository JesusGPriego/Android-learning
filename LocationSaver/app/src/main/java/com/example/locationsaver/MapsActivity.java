package com.example.locationsaver;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.locationsaver.MainActivity.savedLatLng;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    public FusedLocationProviderClient fusedLocationClient;

    /**
     * ArrayList that stores the locations saved.
     */

    private int listPosition;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Vars init.
        intent = getIntent();
        listPosition = intent.getIntExtra("listPosition", 0);
        sharedPreferences = this.getSharedPreferences("com.example.locationsaver", Context.MODE_PRIVATE);

        //Method calls

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        listPosition = intent.getIntExtra("listPosition", 0);
        if (listPosition == 0) {
            getUserLocation();

        } else {
            LatLng savedLatLng = MainActivity.savedLatLng.get(listPosition - 1);
            moveCameraToLocation(savedLatLng, intent.getStringExtra("Address"));
        }
        Toast.makeText(getApplicationContext(), "latitude: " + latitude +
                " longitude" + longitude, Toast.LENGTH_SHORT).show();
    }


    /**
     * Checks if permission needed has been granted, if not, it will ask for it.
     * @return
     */
    public void getUserLocation(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //Permission not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)){
                //Show why user has to grant permission
                new AlertDialog.Builder(this)
                        .setTitle("Location permission is required")
                        .setMessage("Need location permission to get ur location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
                                        1);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }else{
                //No explanation needed.
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);

            }
        }else{
            //If permission is granted, it will get the user location.
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            moveCameraToLocation(new LatLng(latitude, longitude),
                                    "You are here");

                        }
                    });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

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