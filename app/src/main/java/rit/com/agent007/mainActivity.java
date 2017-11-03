package rit.com.agent007;

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

}
