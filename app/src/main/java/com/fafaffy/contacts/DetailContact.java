package com.fafaffy.contacts;

/* Created by Alex Casasola & Brian Gardner */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fafaffy.contacts.Controllers.FileController;
import com.fafaffy.contacts.Fragments.DatePickerFragment;
import com.fafaffy.contacts.Models.Contact;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// This activity allows a user to enter a contact's information
// and either save the contact, or delete an existing contact
// that was opened from the previous activity (main contact activity)

public class DetailContact extends AppCompatActivity {

    // Create vars for all form fields
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText middleInitialEditText;
    private EditText phoneNumberEditText;
    private Button birthdateButton;
    private Button firstMetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_contact);

        // Assign vars to form fields
        firstNameEditText       = (EditText)findViewById(R.id.firstNameTextBox);
        lastNameEditText        = (EditText)findViewById(R.id.lastNameTextBox);
        middleInitialEditText   = (EditText)findViewById(R.id.middleInitialTextBox);
        phoneNumberEditText     = (EditText)findViewById(R.id.phoneNumberTextBox);
        birthdateButton         = (Button)findViewById(R.id.birthdate);
        firstMetButton          = (Button)findViewById(R.id.firstContactDateButton);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setButton((Button)v);
        newFragment.show(getFragmentManager(), "date picker");
        //((Button)v).setText(newFragment.getSelectedDate().toString());
    }


    // Save function creates a contact from field data
    // and then writes the file to contactList.txt on internal private storage
    public void onSaveClicked(View v) {
        //Ensure user has filled out all fields before attempting
        //to create a contact object and saving it
        if(firstNameEditText.getText().toString().equals("") ||
                lastNameEditText.getText().toString().equals("") ||
                middleInitialEditText.getText().toString().equals("") ||
                phoneNumberEditText.getText().toString().equals("")){
                Toast.makeText(this, "Please fill out all form data before saving.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // Create a new contact from form data
            Contact contact = createContact();
            FileController fw = new FileController(getApplicationContext());
            fw.saveContact(contact);

            //display file saved confirmation message
            Toast.makeText(this, contact.getFirstName() + " " + contact.getLastName() + " saved successfully",
                    Toast.LENGTH_SHORT).show();

            // Navigate user back to main page where all contacts are displayed
            Intent intent = new Intent(this, MainContactActivity.class);
            startActivity(intent);
        }
    }

    // Method creates a contact from form input
    // Middle initial is converted from string to char
    // Birth date & firstMet date are both converted from CharSequence to Date objects
    public Contact createContact()  {
        return new Contact(
                firstNameEditText.getText().toString(),
                middleInitialEditText.getText().toString().charAt(0), //Convert string to char
                lastNameEditText.getText().toString(),
                phoneNumberEditText.getText().toString(),
                convertToDateObject(birthdateButton.getText()),     // Convert CharSequence to Date Obj
                convertToDateObject(firstMetButton.getText())       // Convert CharSequence to Date Obj
        );
    }

    // Helper method from createContact method -- birtdate & firstmet date need to be converted
    // from CharSequence to Date objects to match Contact model
    private Date convertToDateObject(CharSequence input) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            String dateString = sdf.format(input);
            date = sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    // Helper method for print writer to convert Date obj to a writeable string
//    private String convertDatePickerToString(Date inputDate){
//        int day = inputDate.getDay();
//        int month = inputDate.getMonth() + 1;
//        int year = inputDate.getYear();
//    }
}
