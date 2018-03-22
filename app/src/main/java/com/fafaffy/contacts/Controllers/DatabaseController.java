package com.fafaffy.contacts.Controllers;

// Created by Brian on 3/21/18.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fafaffy.contacts.Models.Contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseController extends SQLiteOpenHelper{

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public static final String DATABASE_NAME = "contacts.db";
    public static final String TABLE_NAME = "contacts_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRST_NAME";
    public static final String COL_3 = "LAST_NAME";
    public static final String COL_4  = "MIDDLE_INITIAL";
    public static final String COL_5  = "PHONE_NUMBER";
    public static final String COL_6  = "BIRTHDATE";
    public static final String COL_7  = "FIRST_CONTACT_DATE";


    // Database Constructor only creates the Database file
    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
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
                "FIRST_CONTACT_DATE TEXT) " +
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


        // Assign each value to a 'contentValues' object
        ContentValues contentValues= new ContentValues();
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

        // Insert values into the DB thru the contentValues object
        long result = db.insert(TABLE_NAME, null, contentValues);

        // db.insert returns -1 if the operation failed, else its good to go
        if(result == -1){
            return false;
        }
        else return true;
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
                        cursorResultSet.getString(3).charAt(0),             // Middle Initial
                        cursorResultSet.getString(2),                       // Lastname
                        cursorResultSet.getString(4),                       // Phone number
                        birthdate,                                             // Birthdate
                        dateFormat.parse(cursorResultSet.getString(6))      // First contact date
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cursorResultSet.moveToNext();
        }
        return listofContacts;
    }


    // UPDATE FUNCTION NEEDS THE ID PASSED AS WELL
    public boolean update(String id, String firstName, String lastName, String middleInitial,
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
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return true;

    }


    // DELETE FUNCTION
    public Integer delete(String id, String firstName, String lastName, String middleInitial,
                          String phoneNumber, Object birthdate, Date firstContactDate){

        // Get db instance
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String[]{id} );

    }



}
