package com.fafaffy.contacts.Controllers;

// Created by Brian on Mar 2-18

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

    private OnShakeListener listener;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    //Constructor takes context input from main class and
    // registers the accelerometer with the listener
    public SensorController(Context context)  {
        this.context = context;

        //Set sensor last updated time
        lastUpdate = System.currentTimeMillis();
        SensorManager manager = (SensorManager)context.getSystemService( Context.SENSOR_SERVICE );
        Sensor accel = manager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );

        //TEST CODE
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;


        manager.registerListener( this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        listener = null;
    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(sensorEvent);
        }
    }

    public void getAccelerometer(SensorEvent event) {
        float[] values = event.values;

        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float)Math.sqrt((double) (x*x + y*y + z*z));
        float delta = mAccelCurrent -mAccelLast;
        mAccel = mAccel * 0.9f + delta; // low cut filter

        if (mAccel > 12) {
            listener.OnShake();
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Function is Not Needed
    }

    public interface OnShakeListener {
        void OnShake();
    }


}
