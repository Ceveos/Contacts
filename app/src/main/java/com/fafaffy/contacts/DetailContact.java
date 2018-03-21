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

import com.fafaffy.contacts.Controllers.DatabaseController;
import com.fafaffy.contacts.Controllers.FileController;
import com.fafaffy.contacts.Fragments.DatePickerFragment;
import com.fafaffy.contacts.Models.Contact;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private List<Contact> listOfContacts;
    private int selectedContactIndex = -1;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

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


        // See if we have intents
        Intent curIntent = getIntent();
        if (curIntent == null ) {
            return;
        }

        Bundle extras = curIntent.getExtras();
        if (extras != null) {
            listOfContacts = (ArrayList<Contact>)extras.get("contacts");

            selectedContactIndex = (int)extras.get("index");
            if (listOfContacts.isEmpty() || selectedContactIndex < 0) return;
            Contact curContact = listOfContacts.get(selectedContactIndex);
            firstNameEditText.setText(curContact.getFirstName());
            middleInitialEditText.setText(curContact.getMiddleInitial().toString());
            lastNameEditText.setText(curContact.getLastName());
            phoneNumberEditText.setText(curContact.getPhoneNumber());
            if (curContact.getBirthday() != null) {
                birthdateButton.setText(sdf.format(curContact.getBirthday()));
            }
            firstMetButton.setText(sdf.format(curContact.getFirstMet()));
        } else {
            firstMetButton.setText(sdf.format(Calendar.getInstance().getTime()));
        }
    }

    // Opens a fragment to choose birthdate or when you first met
    // By Alex
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setButton((Button)v);
        newFragment.show(getFragmentManager(), "date picker");
        //((Button)v).setText(newFragment.getSelectedDate().toString());
    }


    // Delete function creates a contact from field data
    // and then writes the file to contactList.txt on internal private storage
    public void onDeleteClicked(View v) {
        if (listOfContacts.size() > 0 && selectedContactIndex >= 0 && selectedContactIndex < listOfContacts.size()) {
            FileController fw = new FileController(getApplicationContext());
            listOfContacts.remove(selectedContactIndex);
            fw.saveAllContacts(listOfContacts);
        }
        //display file saved confirmation message
        Toast.makeText(this, "Contact deleted successfully",
                Toast.LENGTH_SHORT).show();

        finish();
    }


    // NEW SAVE FUNCTION USING SQLITE------------------------------------------------
    // Save function creates a contact from field data
    // and then writes the file to contactList.txt on internal private storage
    public void onSaveClicked(View v) {
        //Ensure user has filled out all fields before attempting
        //to create a contact object and saving it
        if(firstNameEditText.getText().toString().isEmpty() ||
                lastNameEditText.getText().toString().isEmpty() ||
                phoneNumberEditText.getText().toString().isEmpty()){
                Toast.makeText(this, "Please fill out all form data before saving.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // Create a new contact from form data
            Contact contact = createContact();

            // Create db instance
            DatabaseController myDb = new DatabaseController(getApplicationContext());

            // Insert contact data into SQLite DB, cast date objects to strings and mid initial to string
            myDb.insertData(contact.getFirstName(), contact.getLastName(), contact.getMiddleInitial().toString(),
                    contact.getPhoneNumber(), contact.getBirthday().toString(), contact.getFirstMet().toString());


            // Modifying existing contact
//            if (listOfContacts != null && listOfContacts.size() > 0 && selectedContactIndex >= 0 && selectedContactIndex < listOfContacts.size()) {
//
//                listOfContacts.set(selectedContactIndex, contact);
//                fw.saveAllContacts(listOfContacts);
//            } else {
//                fw.saveContact(contact);
//            }
            //display file saved confirmation message
            Toast.makeText(this, contact.getFirstName() + " " + contact.getLastName() + " saved successfully",
                    Toast.LENGTH_SHORT).show();

            finish();
        }
    }




//   OLD SAVE FUNCTION USING FILES----------------------------------
//    // Save function creates a contact from field data
//    // and then writes the file to contactList.txt on internal private storage
//    public void onSaveClicked(View v) {
//        //Ensure user has filled out all fields before attempting
//        //to create a contact object and saving it
//        if(firstNameEditText.getText().toString().isEmpty() ||
//                lastNameEditText.getText().toString().isEmpty() ||
//                phoneNumberEditText.getText().toString().isEmpty()){
//                Toast.makeText(this, "Please fill out all form data before saving.",
//                    Toast.LENGTH_SHORT).show();
//        }
//        else {
//            // Create a new contact from form data
//            Contact contact = createContact();
//            FileController fw = new FileController(getApplicationContext());
//
//            // Modifying existing contact
//            if (listOfContacts != null && listOfContacts.size() > 0 && selectedContactIndex >= 0 && selectedContactIndex < listOfContacts.size()) {
//
//                listOfContacts.set(selectedContactIndex, contact);
//                fw.saveAllContacts(listOfContacts);
//            } else {
//                fw.saveContact(contact);
//            }
//            //display file saved confirmation message
//            Toast.makeText(this, contact.getFirstName() + " " + contact.getLastName() + " saved successfully",
//                    Toast.LENGTH_SHORT).show();
//
//            finish();
//        }
//    }

    // Method creates a contact from form input
    // Middle initial is converted from string to char
    // Birth date & firstMet date are both converted from CharSequence to Date objects
    public Contact createContact()  {
        Contact c = new Contact();
        c.setFirstName(firstNameEditText.getText().toString());
        c.setLastName(lastNameEditText.getText().toString());
        c.setFirstMet(convertToDateObject(firstMetButton.getText()));
        c.setPhoneNumber(phoneNumberEditText.getText().toString());
        if (middleInitialEditText.getText().length() > 0) {
            c.setMiddleInitial(middleInitialEditText.getText().charAt(0));
        }
        if (!birthdateButton.getText().equals("N/A")) {
            c.setBirthday(convertToDateObject(birthdateButton.getText()));
        }
        return c;
    }

    // Helper method from createContact method -- birtdate & firstmet date need to be converted
    // from CharSequence to Date objects to match Contact model
    private Date convertToDateObject(CharSequence input) {

        Date date = null;
        try {
            date = sdf.parse(input.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
