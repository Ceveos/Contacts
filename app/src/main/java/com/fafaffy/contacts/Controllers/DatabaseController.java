package com.fafaffy.contacts.Controllers;

// Created by Brian on 3/21/18.

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.fafaffy.contacts.DetailContact;
import com.fafaffy.contacts.MainContactActivity;
import com.fafaffy.contacts.Models.Contact;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseController extends SQLiteOpenHelper{
    public Context curContext;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public static final String DATABASE_NAME = "contacts.db";
    public static final String TABLE_NAME = "contacts_table";
    public static final String COL_1  = "ID";
    public static final String COL_2  = "FIRST_NAME";
    public static final String COL_3  = "LAST_NAME";
    public static final String COL_4  = "MIDDLE_INITIAL";
    public static final String COL_5  = "PHONE_NUMBER";
    public static final String COL_6  = "BIRTHDATE";
    public static final String COL_7  = "FIRST_CONTACT_DATE";

    // PHASE 4 Variable Additions:
    public static final String COL_8  = "ADDRESS_LINE_ONE";
    public static final String COL_9  = "ADDRESS_LINE_TWO";
    public static final String COL_10 = "CITY";
    public static final String COL_11 = "STATE";
    public static final String COL_12 = "ZIP";




    // Database Constructor only creates the Database file
    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
        curContext = context;
    }


    // On create actually creates the TABLE itself
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Whatever query you wanna run goes below as a string
        db.execSQL("create table " + TABLE_NAME + " ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FIRST_NAME TEXT, " +
                "LAST_NAME TEXT, " +
                "MIDDLE_INITIAL TEXT, " +
                "PHONE_NUMBER TEXT, " +
                "BIRTHDATE TEXT, " +
                "FIRST_CONTACT_DATE TEXT, " +
                "ADDRESS_LINE_ONE TEXT, " +
                "ADDRESS_LINE_TWO TEXT, " +
                "CITY TEXT, " +
                "STATE TEXT, " +
                "ZIP TEXT) " +
                "");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // If the table is already there, drop this new instance
        db.execSQL("DROP TABLE IF EXISTS " + db );
        onCreate(db);
    }


    // Method used to INSERT Data
    public boolean insertData(String firstName, String lastName, String middleInitial,
                              String phoneNumber, Object birthdate, Date firstContactDate){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        SQLiteDatabase db = this.getWritableDatabase();

        if (middleInitial == null)
            middleInitial = "";
        if (phoneNumber == null)
            phoneNumber = "";


        // Assign each value to a 'contentValues' object
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, middleInitial);
        contentValues.put(COL_5, phoneNumber);


        if (birthdate == null) {
            contentValues.put(COL_6, "");

        } else {
            contentValues.put(COL_6, dateFormat.format(birthdate).toString());
        }

        if (firstContactDate == null) {
            contentValues.put(COL_7, "");
        } else {
            contentValues.put(COL_7, dateFormat.format(firstContactDate).toString());
        }

        // Insert values into the DB thru the contentValues object
        long result = db.insert(TABLE_NAME, null, contentValues);

        // db.insert returns -1 if the operation failed, else its good to go
        if(result == -1){
            return false;
        }
        else return true;
    }

    // Created by the non-lazy Alex
    public Contact getContactByID(int id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date birthdate = null;

        // Get db instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Create cursor object to hold result set
        Cursor cursorResultSet = db.rawQuery(String.format("select * from %s WHERE id = ?", TABLE_NAME), new String[] {Integer.toString(id)});

        // Process the result set
        cursorResultSet.moveToFirst();
        // Add result set data into arraylist
        while(!cursorResultSet.isAfterLast()) {

            if (!cursorResultSet.getString(5).isEmpty() && !cursorResultSet.getString(5).equalsIgnoreCase("N/A")) {
                try {
                    birthdate = dateFormat.parse(cursorResultSet.getString(5));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            try {
                return new Contact(
                        cursorResultSet.getString(1),                       // Firstname
                        cursorResultSet.getString(3).length() <= 0
                                ? null : cursorResultSet.getString(3).charAt(0),             // Middle Initial
                        cursorResultSet.getString(2),                       // Lastname
                        cursorResultSet.getString(4),                       // Phone number
                        birthdate,                                             // Birthdate
                        dateFormat.parse(cursorResultSet.getString(6)),      // First contact date
                        Integer.parseInt(cursorResultSet.getString(0))
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cursorResultSet.moveToNext();
        }
        return null;
    }

    // Method to get all Database data and return it as an arraylist of contact objects
    public ArrayList<Contact> getAllData(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date birthdate = null;

        //Create arraylist to hold result set
        ArrayList<Contact> listofContacts = new ArrayList<>();

        // Get db instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Create cursor object to hold result set
        Cursor cursorResultSet = db.rawQuery("select * from " + TABLE_NAME, null);

        // Process the result set
        cursorResultSet.moveToFirst();

        // Add result set data into arraylist
        while(!cursorResultSet.isAfterLast()) {
            if (!cursorResultSet.getString(5).isEmpty() && !cursorResultSet.getString(5).equalsIgnoreCase("N/A")) {
                try {
                    birthdate = dateFormat.parse(cursorResultSet.getString(5));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            try {
                listofContacts.add(new Contact(
                        cursorResultSet.getString(1),                       // Firstname
                        cursorResultSet.getString(3).length() <= 0
                                ? null : cursorResultSet.getString(3).charAt(0),             // Middle Initial
                        cursorResultSet.getString(2),                       // Lastname
                        cursorResultSet.getString(4),                       // Phone number
                        birthdate,                                             // Birthdate
                        dateFormat.parse(cursorResultSet.getString(6)),      // First contact date
                        Integer.parseInt(cursorResultSet.getString(0))
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cursorResultSet.moveToNext();
        }
        return listofContacts;
    }


    // UPDATE FUNCTION NEEDS THE ID PASSED AS WELL
    public boolean update(int id, String firstName, String lastName, String middleInitial,
                          String phoneNumber, Object birthdate, Date firstContactDate){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        // Get db instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Assign each value to a 'contentValues' object
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, middleInitial);
        contentValues.put(COL_5, phoneNumber);

        if (birthdate == null) {
            contentValues.put(COL_6, "N/A");

        } else {
            contentValues.put(COL_6, dateFormat.format(birthdate).toString());
        }
        contentValues.put(COL_7, dateFormat.format(firstContactDate).toString());

        // call update function where id equals whatever
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{Integer.toString(id)});
        return true;


    }


    public Integer delete(int id){
        // Get db instance
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{Integer.toString(id)} );
    }

    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null );
    }

    public void loadContactsFromFile(Uri filepath) {
        try {
            // Try to read the file
            InputStream fileStream = curContext.getContentResolver().openInputStream(filepath);
            DataInputStream in = new DataInputStream(fileStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";

            // For each line...
            while((line = br.readLine()) != null) {
                // Attempt to parse this as a contact
                String[] data = line.split("\t");

                try
                {
                    // First Name
                    // Middle Initial
                    // Last Name
                    // Phone Number
                    // Birthday
                    // First Met
                    // If less than 6, not a valid line to parse
                    if (data.length < 6 ) continue;
                    if (data[0] == ""  || data[2] == "") continue;
                    this.insertData(data[0], data[1], data[2],data[3],convertToDateObject(data[4]),convertToDateObject(data[5]));

                } catch(Exception e) {
                    // Could not load this line of data
                }


            }
        } catch (Exception e) {
            // File does not exist
            int i = 1;
        }
    }

    public void saveContactsToFile(String filepath) {
        try {
            if (ContextCompat.checkSelfPermission(curContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted

                ActivityCompat.requestPermissions((Activity) curContext, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filepath);
            FileWriter writer = new FileWriter(file, false);

            ArrayList<Contact> contacts = this.getAllData();
            for (Contact c : contacts)
            {
                writer.write(c.getFirstName() + "\t");
                writer.write(c.getMiddleInitial() + "\t");
                writer.write(c.getLastName() + "\t");
                writer.write(c.getPhoneNumber() + "\t");
                writer.write(sdf.format(c.getBirthday()) + "\t");
                writer.write(sdf.format(c.getFirstMet()) + "\n");
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            int i =1;
        }
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
