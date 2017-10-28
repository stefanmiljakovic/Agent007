package rit.com.agent007.main;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import rit.com.agent007.R;
import rit.com.agent007.SharedAppToolbox;

/**
 * Created by Stefan on 28/10/17.
 */

public class sensorGyroscope extends SharedAppToolbox{

    private SensorEventListener gyroscopeEventListener;
    private Sensor gyroscopeSensor;
    private SensorManager sensorManager;

    private void implementSensor(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        final TextView textViewHide2 = (TextView)findViewById(R.id.main_TextView_coordinates);
        final TextView textViewHide1 = (TextView)findViewById(R.id.main_TextView_logo);

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

    private void switchVisibility(View v){
        if(v.getVisibility() == View.INVISIBLE)
            v.setVisibility(View.VISIBLE);
        else
            v.setVisibility(View.INVISIBLE);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

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
