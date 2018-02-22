package com.fafaffy.contacts.Controllers;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fafaffy.contacts.Models.Contact;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

//Created by Brian on 2/18/18.

public class FileController extends Activity {

    // variable to hold context
    private Context context;

    // Constructor -- needs Context from whoever calls it
    public FileController(Context context){
        this.context = context;
    }

    // Function takes a Contact arg and writes every attribute of it as a comma delimited string
    public void saveContact(Contact contact) {
        // Write to file using comma delimited structure for each field
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            FileOutputStream fileout = context.openFileOutput("contacts.txt", Context.MODE_PRIVATE | Context.MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(contact.getFirstName() + ",");
            outputWriter.write(contact.getMiddleInitial() + ",");
            outputWriter.write(contact.getLastName() + ",");
            outputWriter.write(contact.getPhoneNumber() + ",");
            outputWriter.write(dateFormat.format(contact.getBirthday()) + ",");
            outputWriter.write(dateFormat.format(contact.getFirstMet()) + "\n");

            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Created by Alex
    // Overwrites the contacts file with the list of contacts
    public void saveAllContacts(List<Contact> listOfContacts) {
    // Write to file using comma delimited structure for each field
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            FileOutputStream fileout = context.openFileOutput("contacts.txt", Context.MODE_PRIVATE );
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            for(Contact contact : listOfContacts) {
                outputWriter.write(contact.getFirstName() + ",");
                outputWriter.write(contact.getMiddleInitial() + ",");
                outputWriter.write(contact.getLastName() + ",");
                outputWriter.write(contact.getPhoneNumber() + ",");
                outputWriter.write(dateFormat.format(contact.getBirthday()) + ",");
                outputWriter.write(dateFormat.format(contact.getFirstMet()) + "\n");
            }

            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Created by Alex
    // Reads all the contacts
    public List<Contact> readContacts(){
        List<Contact> listOfContacts = new LinkedList<Contact>();
        try {
            FileInputStream filein = context.openFileInput("contacts.txt");
            InputStreamReader streamReader = new InputStreamReader(filein);
            BufferedReader reader = new BufferedReader(streamReader);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            while (reader.ready()) {
                String[] line = reader.readLine().split(",");
                listOfContacts.add(new Contact(
                        line[0],                      // First name
                        line[1].charAt(0),            // Middle initial
                        line[2],                      // Last name
                        line[3],                      // Phone number
                        dateFormat.parse(line[4]),    // Birthday
                        dateFormat.parse(line[5])) ); // First met
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return listOfContacts;
    }

}
