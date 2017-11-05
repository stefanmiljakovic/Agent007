package rit.com.agent007.main;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import rit.com.agent007.R;
import rit.com.agent007.SharedAppToolbox;

/**
 * Created by Stefan on 28/10/17.
 */

public class sensorGyroscope extends SharedAppToolbox{

    private static float GYRO_BREAKPOINT = 4.5f;

    private SensorEventListener gyroscopeEventListener;
    private Sensor gyroscopeSensor;
    private SensorManager sensorManager;

    private Boolean noGyroscope = false;

    private LinearLayout linearLayout;

    private void implementSensor(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscopeSensor==null){
            noGyroscope = true;
            return;
        }

        gyroscopeEventListener = new SensorEventListener(){

            private int rotationCount = 1;
            private boolean switchRotationCount = true;

            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.values[1] > GYRO_BREAKPOINT && switchRotationCount){
                    rotationCount++;
                    switchRotationCount = !switchRotationCount;
                }
                else if(event.values[1] < -GYRO_BREAKPOINT && !switchRotationCount){
                    rotationCount++;
                    switchRotationCount = !switchRotationCount;
                }

                if(rotationCount % 7 == 0){
                    switchVisibility();

                    rotationCount = 1;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void implementButtonListener(){

        ImageView imageView = getImgViewById(R.id.main_ImageView_main);
        imageView.setClickable(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchVisibility();
            }
        });

    }

    private void switchVisibility(){
        if(linearLayout.getVisibility() == View.VISIBLE)
            linearLayout.setVisibility(View.INVISIBLE);
        else
            linearLayout.setVisibility(View.VISIBLE);


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linearLayout = getLinearLayoutById(R.id.main_LinearLayout_switch);

        implementSensor();

        if(noGyroscope)
            implementButtonListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(sensorManager == null)
            implementSensor();

        if(gyroscopeSensor==null)
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(gyroscopeSensor==null)
        sensorManager.unregisterListener(gyroscopeEventListener);
    }


}
