package com.fafaffy.contacts;

/* Created by Alex Casasola & Brian Gardner */

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fafaffy.contacts.Controllers.DatabaseController;
import com.fafaffy.contacts.Fragments.DatePickerFragment;
import com.fafaffy.contacts.Models.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
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

            // PHASE 4 Additions
            addressLineOneEditText.setText(curContact.getAddressLineOne());
            addressLineTwoEditText.setText(curContact.getAddressLineTwo());
            cityEditText.setText(curContact.getCity());
            stateEditText.setText(curContact.getState());
            zipCodeEditText.setText(curContact.getZipCode());
        }

        else {
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
                        contact.getPhoneNumber(), contact.getBirthday(), contact.getFirstMet(), contact.getAddressLineOne(),
                        contact.getAddressLineTwo(), contact.getCity(), contact.getState(), contact.getZipCode());
            } else {
                // If we're inserting a record

                // Insert contact data into SQLite DB, cast date objects to strings and mid initial to string
                myDb.insertData(contact.getFirstName(), contact.getLastName(), middleInitial,
                        contact.getPhoneNumber(), contact.getBirthday(), contact.getFirstMet(),
                        contact.getAddressLineOne(), contact.getAddressLineTwo(), contact.getCity(),
                        contact.getState(), contact.getZipCode());
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

        // Phase 4 Updates
        c.setAddressLineOne(addressLineOneEditText.getText().toString());
        c.setAddressLineTwo(addressLineTwoEditText.getText().toString());
        c.setCity(cityEditText.getText().toString());
        c.setState(stateEditText.getText().toString());
        c.setZipCode(zipCodeEditText.getText().toString());

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




    //PHASE 4 MAP FUNCTIONS -----------------------------------------------------

    //PHASE 4 - on 'map address' button, start map activity
    public void launchMapAddressActivity(View view){

        // 1 get the address from form
        String contactAddress = getAddress();

        // 2 call the Google API website to get the JSON object from the contact address
        new DownloadJSONFile().execute(contactAddress);

        // 3 parse JSON to get lat and longitude
        //parseAddress(contactAddress);

        // 4 Create intent and PASS LATITUDE AND LONGITUDE to map activity
        // Moved this to the POST execute function...where it belongs

    }

    public String getAddress(){
        return "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                addressLineOneEditText.getText().toString().replace(' ', '+') + ",+" +
                cityEditText.getText().toString() + ",+" +
                stateEditText.getText().toString() + "&key=AIzaSyAaNqdT1pOfE57-E-rfvQXWsSx4QVz7stw";
    }



    // ASYNC TASK TO DOWNLOAD THE JSON DATA FROM GOOGLE-----------------------------------
    private class DownloadJSONFile extends AsyncTask< String, Void, JSONObject > { //3 things are: Parameter, Progress, Result

        JSONObject jsonReturnObject = new JSONObject();

        // Nothing needed
        protected void onPreExecute() {

        }

        @Override
        protected JSONObject doInBackground(String... urlString) {
            //FILL OUT URL CODE AND GET DATA
            try {
                jsonReturnObject = readJsonFromUrl(urlString[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonReturnObject;
        }

        @Override
        protected void onPostExecute(JSONObject fetchedDataList) {
            Log.v("onPostExecute", "");

            // Create Double array to hold 2 values - latitude and longitude
            Double[] latLongValues = {};

            //Call parseAddress func and get those two double values, assign them into array
            latLongValues = parseAddress(fetchedDataList);


            // Now pass those two values from Double array as intents to the maps activity class

            Intent mapAddressIntent = new Intent(getApplicationContext(), MapsActivity.class);
            //mapAddressIntent.putExtra(contactAddress, contactAddress)
            startActivity(mapAddressIntent);
        }
    }


    /////////////////////////////////////////////////////////
    // Json Object read functions
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    ///////////////////////////////////////////////////////



    public List<String> getJsonFile(String stringInput) throws IOException{

        List<String> jsonObjectData = new ArrayList<>();

        // CREATE URL from user symbol
        URL url = null;
        try {
            url = new URL(stringInput);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Attempt to open connection and read data
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{
            InputStream responseInputStream = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(responseInputStream, "UTF-8");

            // If there is an error, report it
            if(connection.getResponseCode() != 200){
                throw new IOException(connection.getResponseMessage() +": with " + stringInput);
            }
            // If no error, continue and read in data
            else {
                BufferedReader reader = new BufferedReader(isr);

                for (String line = null; (line = reader.readLine()) != null;) {
                        jsonObjectData.add(line);
                }
                return jsonObjectData;
            }
        }finally {
            connection.disconnect();
        }
    }




    public Double[] parseAddress(JSONObject input){
        Double[] latLongValues = {0.0,0.0};

        // Get JSON Array node and funnel down to the one we need for lat and long - located in 'location' node
        try {
            JSONArray results = input.getJSONArray("results");
            JSONObject resultsJSONObject = results.getJSONObject(0);
            JSONObject geometry = resultsJSONObject.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");

            // Save lat and long into double array
            latLongValues[0] = Double.parseDouble(location.getString("lat"));
            latLongValues[1] = Double.parseDouble(location.getString("lng"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return array with lat and long saved
        return latLongValues;
    }

}
