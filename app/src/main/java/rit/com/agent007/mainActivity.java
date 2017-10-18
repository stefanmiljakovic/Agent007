package rit.com.agent007;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class mainActivity extends AppCompatActivity {


    private LocationManager locationManager;
    private LocationListener locationListener;


    private Typeface getTypeface() {
        return Typeface.createFromAsset(getAssets(), "good_times.ttf");
    }

    private TextView getTextViewById(int id) {
        return (TextView) findViewById(id);
    }

    private void runFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void hideBar() {
        getSupportActionBar().hide();
    }


    private void regLocationListener(){
        locationManager.requestLocationUpdates("gps", 7000, 0, locationListener);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runFullscreen();
        setContentView(R.layout.activity_main);
        hideBar();

        final String agentName = getIntent().getExtras().getString("NAME_SURNAME");

        final TextView textView = getTextViewById(R.id.textViewLocation);
        final ImageView imageView = (ImageView)findViewById(R.id.imgView);
        final TextView textViewGPS = getTextViewById(R.id.textViewGPSEnabled);

        textViewGPS.setTypeface(getTypeface());
        textView.setTypeface(getTypeface());

        textView.setText("Welcome agent " + agentName + getString(R.string.sample_location));

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textView.setText("Welcome agent " + agentName + "\n\nLatitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
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

        if (Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 1);
                return;
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

    @Override
    protected void onStart(){
        super.onStart();

        if(Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
