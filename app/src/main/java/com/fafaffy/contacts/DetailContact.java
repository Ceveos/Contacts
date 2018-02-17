package com.fafaffy.contacts;

/* Created by Alex Casasola & Brian Gardner */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

/*
    This activity shows a contact's detail.
 */

public class DetailContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_contact);

    }
}
