package com.fafaffy.contacts.Controllers;

//Created by Brian on Mar 2-18

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class SensorController  implements SensorEventListener {

    // variable to hold context
    private Context context;

    // Store last updated system time
    private long lastUpdate;


    //Constructor takes context input from main class and
    // registers the accelerometer with the listener
    public SensorController(Context context)  {
        this.context = context;
        //Set sensor last updated time
        lastUpdate = System.currentTimeMillis();
        SensorManager manager = (SensorManager)context.getSystemService( Context.SENSOR_SERVICE );
        Sensor accel = manager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );
        manager.registerListener( this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            Toast.makeText(context, "on sensor changed", Toast.LENGTH_SHORT).show();
            getAccelerometer(sensorEvent);
        }
    }

    public void getAccelerometer(SensorEvent event) {
        float[] values = event.values;

        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelerationSquareRoot >= 2)
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(context, "DEVICE WAS SHAKEN", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Function is Not Needed
    }


}
