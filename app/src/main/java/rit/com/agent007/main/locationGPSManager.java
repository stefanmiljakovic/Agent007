package rit.com.agent007.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.widget.TextView;


import rit.com.agent007.R;

/**
 * Created by Stefan on 28/10/17.
 */

public class locationGPSManager extends mapFragmentManager {
    private LocationManager locationManager;
    private LocationListener locationListener;

    private ImageView imageView;
    private TextView textViewGPS, textView;

    private void regLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates("gps", 7000, 0, locationListener);
    }
    private void requestPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 1);
            }
            else{
                regLocationListener();
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                {
                    imageView.setImageResource(R.mipmap.gps_enabled);
                    textViewGPS.setText("GPS Enabled");
                }}
        }
        else{
            regLocationListener();
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                imageView.setImageResource(R.mipmap.gps_enabled);
                textViewGPS.setText("GPS Enabled");
            }}
    }

    private void locationServiceListener(final String agentName, final ImageView imageView){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                textView.setText("Welcome agent " + agentName + "\n\nLatitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
                if(location != null)
                onLocationChanges(location, agentName);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                imageView.setImageResource(R.mipmap.gps_enabled);
                textViewGPS.setText("GPS Enabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                imageView.setImageResource(R.mipmap.ic_launcher_round);
                textViewGPS.setText("GPS Disabled");
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageView = getImgViewById(R.id.main_ImageView_main);

        textViewGPS = getTextViewById(R.id.main_TextView_logo);
        textView = getTextViewById(R.id.main_TextView_coordinates);

        textView.setText("Welcome agent " + getExtra("NAME_SURNAME") + getString(R.string.sample_location));

        locationServiceListener(getExtra("NAME_SURNAME"), imageView);
        requestPermission();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    regLocationListener();
                return;
        }
    }
}
