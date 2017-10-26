package rit.com.agent007;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class mainActivity extends AppCompatActivity {


    private LocationManager locationManager;
    private LocationListener locationListener;
    private SensorEventListener gyroscopeEventListener;
    private Sensor gyroscopeSensor;
    private SensorManager sensorManager;




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

    private void switchVisibility(View v){
        if(v.getVisibility() == View.INVISIBLE)
            v.setVisibility(View.VISIBLE);
        else
            v.setVisibility(View.INVISIBLE);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

    }
    private void implementSensor(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        final TextView textViewHide2 = (TextView)findViewById(R.id.textViewGPSEnabled);
        final TextView textViewHide1 = (TextView)findViewById(R.id.textViewLocation);

        if(gyroscopeSensor==null)
            finish();

        gyroscopeEventListener = new SensorEventListener(){

            private int rotationCount = 1;
            private boolean switchRotationCount = true;

            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.values[1] > 3.0f && switchRotationCount){
                    rotationCount++;
                    switchRotationCount = !switchRotationCount;
                }
                else if(event.values[1] < -3.0f && !switchRotationCount){
                    rotationCount++;
                    switchRotationCount = !switchRotationCount;
                }

                if(rotationCount % 7 == 0){
                    switchVisibility(textViewHide1);
                    switchVisibility(textViewHide2);

                    rotationCount = 1;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
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

    @Override
    protected void onResume(){
        super.onResume();

        if(sensorManager == null)
            implementSensor();

        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }

}
