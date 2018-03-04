package com.fafaffy.contacts.Controllers;

//Created by Brian on Mar 2-18

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorController extends Activity implements SensorEventListener {

    // variable to hold context
    private Context context;

    // Store last updated system time
    private long lastUpdate;

    // create Sensor Manager object
    public SensorManager sensorManager;


    // Constructor takes context input from main class
    public SensorController(Context context){
        this.context = context;
        //set last updated time
        lastUpdate = System.currentTimeMillis();

    }

    public SensorManager getSensorManager(){
        // Assign sensorManager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        return sensorManager;
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(sensorEvent);
        }
    }

    public void getAccelerometer(SensorEvent event) {

        Log.e("Accelerometer", "getAccel FUNCTION");

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
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Function is Not Needed
    }


}
