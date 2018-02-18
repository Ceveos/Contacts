package com.fafaffy.contacts;

/* Created by Alex Casasola & Brian Gardner */


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.fafaffy.contacts.Controllers.FileController;
import com.fafaffy.contacts.Fragments.DatePickerFragment;

import static android.app.DatePickerDialog.*;

/*
    This activity shows a contact's detail.
 */

public class DetailContact extends AppCompatActivity {

    //create field just for firstname testing
    public static EditText firstNameEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_contact);

        //Test code for firstname field
        firstNameEditText = (EditText)findViewById(R.id.firstNameTextBox);

    }
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setButton((Button)v);
        newFragment.show(getFragmentManager(), "date picker");
        //((Button)v).setText(newFragment.getSelectedDate().toString());
    }

    public void onSaveClicked(View v){
        //get the text from firstname
        //pass it into fileController.saveContact(firstname)
        Log.v("DetailContact: ", "onSaveClicked Function Reached" );
        FileController fc = new FileController();
        fc.saveContact(firstNameEditText.toString());

    }



}
