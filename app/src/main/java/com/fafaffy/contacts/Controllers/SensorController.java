package com.fafaffy.contacts.Controllers;

//Created by Brian on Mar 2-18
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorController implements SensorEventListener {

    // variable to hold context
    private Context context;

    // create Sensor Manager object
    public SensorManager sensorManager;


    // Constructor takes context input from main class
    public SensorController(Context context){
        this.context = context;
    }

    public SensorManager getSensor(){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        return sensorManager;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
