package com.fafaffy.contacts.Controllers;

// Created by Brian on 3/21/18.

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseController extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "contacts.db";
    public static final String TABLE_NAME = "contacts_table";
    public static final String ID = "ID";
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

    }
    

}