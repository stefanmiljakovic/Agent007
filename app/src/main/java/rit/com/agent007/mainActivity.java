package rit.com.agent007;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.widget.TextView;

import rit.com.agent007.main.locationGPSManager;


public class mainActivity extends locationGPSManager {

    private TextView textView, textViewGPS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = getTextViewById(R.id.main_TextView_coordinates);
        textViewGPS = getTextViewById(R.id.main_TextView_logo);

        textViewGPS.setTypeface(getTypeFace());
        textView.setTypeface(getTypeFace());
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }



}
