package com.bhanuka.kodiak;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import permissions.dispatcher.RuntimePermissions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    String provider;
    Location location;
    private int LOCATION_ACCESS_PERMISSION=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Name of the location provider. 1) gsp 2) wifi or mobile data




        //get last known location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){//&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
//                    Toast.makeText(this,"Permission required to gather user location",Toast.LENGTH_LONG);
//                }
//
//                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1 );
//
//            }
            provider = locationManager.getBestProvider(new Criteria(), false);
            location = getLastKnownLocation();

        }
        else{
//            provider = locationManager.getBestProvider(new Criteria(), false);
//            location = getLastKnownLocation();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(this,"Permission required to gather user location",Toast.LENGTH_LONG);
                }
                else{
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1 );
                }


            }
        }


//        if (location != null) {
//            Log.i("Location Info", "Location achived");
//        } else {
//            Log.i("Location Info", "Location Not Achieved");
//        }


    }


    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {

            @SuppressLint("MissingPermission")
            Location l = locationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void ammo(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Name of the location provider. 1) gsp 2) wifi or mobile data
        provider = locationManager.getBestProvider(new Criteria(), false);



        //get last known location
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            Log.i("Location Info", "Location achived");
        } else {
            Log.i("Location Info", "Location Not Achieved");
        }
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED){
                provider = locationManager.getBestProvider(new Criteria(), false);
                //@SuppressLint("MissingPermission")
                location = getLastKnownLocation();
            }
            else{
                Toast.makeText(this,"Permission was not granted",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 2){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED){
                //provider = locationManager.getBestProvider(new Criteria(), false);
                locationManager.requestLocationUpdates(provider, 400, 10, this);
            }
            else{
                Toast.makeText(this,"Permission was not granted",Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //get location as soon as get updated. To track users location we use this method
        //call every time user location get updated
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Hi Kodiak"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),15));

        Log.i("Latitude : ", lat.toString());
        Log.i("Longitude : ", lng.toString());

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){//&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
//                    Toast.makeText(this,"Permission required to gather user location",Toast.LENGTH_LONG);
//                }
//
//                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},2 );
//            }
            locationManager.requestLocationUpdates(provider, 400, 10, this);

        }
        else{
//            locationManager.requestLocationUpdates(provider, 400, 10, this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(this,"Permission required to gather user location",Toast.LENGTH_LONG);
                }

                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},2 );
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //calls when user location available after some time where user location
        //not available for some time. This use rearly
    }

    @Override
    public void onProviderEnabled(String provider) {
        //use to know when the gps location is available
    }

    @Override
    public void onProviderDisabled(String provider) {
        //use to know where the gps location is unavailable
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
       onLocationChanged(location);
       //ammo();




        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(70, 79.8613);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("University Of Colombo")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


}
