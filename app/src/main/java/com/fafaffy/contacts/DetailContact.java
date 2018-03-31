package com.fafaffy.contacts;

/* Created by Alex Casasola & Brian Gardner */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fafaffy.contacts.Controllers.DatabaseController;
import com.fafaffy.contacts.Fragments.DatePickerFragment;
import com.fafaffy.contacts.Models.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    // PHASE 4 Form Field Variables
    private EditText addressLineOneEditText;
    private EditText addressLineTwoEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText zipCodeEditText;


    private List<Contact> listOfContacts;
    private int selectedContactIndex = -1;
    private DatabaseController controller;
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

        // PHASE 4 Form Field Additions
        addressLineOneEditText = (EditText)findViewById(R.id.AddressLine1TextBox);
        addressLineTwoEditText = (EditText)findViewById(R.id.AddressLine2TextBox);
        cityEditText           = (EditText)findViewById(R.id.CityTextBox);
        stateEditText          = (EditText)findViewById(R.id.StateTextBox);
        zipCodeEditText        = (EditText)findViewById(R.id.ZipCodeTextBox);



        controller = new DatabaseController(getApplicationContext());

        // See if we have intents
        Intent curIntent = getIntent();
        if (curIntent == null ) {
            return;
        }

        Bundle extras = curIntent.getExtras();
        if (extras != null) {
            listOfContacts = (ArrayList<Contact>)extras.get("contacts");

            selectedContactIndex = (int)extras.get("index");

            controller.getContactByID(selectedContactIndex);
            Contact curContact = controller.getContactByID(selectedContactIndex);
            if (curContact == null) return;

            firstNameEditText.setText(curContact.getFirstName());
            if (curContact.getMiddleInitial() != null)
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
        if (selectedContactIndex == -1 )
            finish();

        // Triple check that' we're not dumb and the contact exists
        if (controller.getContactByID(selectedContactIndex) != null) {
            controller.delete(selectedContactIndex);
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

            String middleInitial = "";
            // Check middle initial
            if (contact.getMiddleInitial() != null) {
                middleInitial = contact.getMiddleInitial().toString();
            }

            // If we're updating a record
            if (selectedContactIndex != -1) {
                // Insert contact data into SQLite DB, cast date objects to strings and mid initial to string
                myDb.update(selectedContactIndex, contact.getFirstName(), contact.getLastName(), middleInitial,
                        contact.getPhoneNumber(), contact.getBirthday(), contact.getFirstMet());
            } else {
                // If we're inserting a record

                // Insert contact data into SQLite DB, cast date objects to strings and mid initial to string
                myDb.insertData(contact.getFirstName(), contact.getLastName(), middleInitial,
                        contact.getPhoneNumber(), contact.getBirthday(), contact.getFirstMet());

            }

            //display file saved confirmation message
            Toast.makeText(this, contact.getFirstName() + " " + contact.getLastName() + " saved successfully",
                    Toast.LENGTH_SHORT).show();

            finish();
        }
    }

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
