package com.fafaffy.contacts;

/* Created by Alex Casasola & Brian Gardner */


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.fafaffy.contacts.Fragments.DatePickerFragment;

import static android.app.DatePickerDialog.*;

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
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setButton((Button)v);
        newFragment.show(getFragmentManager(), "date picker");
        //((Button)v).setText(newFragment.getSelectedDate().toString());
    }
}
