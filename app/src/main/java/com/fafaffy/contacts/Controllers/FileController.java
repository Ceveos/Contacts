package com.fafaffy.contacts.Controllers;
import android.app.Activity;
import android.content.Context;
import com.fafaffy.contacts.Models.Contact;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

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
        try {
            FileOutputStream fileout = context.openFileOutput("contactList.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(contact.getFirstName() + ",");
            outputWriter.write(contact.getMiddleInitial() + ",");
            outputWriter.write(contact.getLastName() + ",");
            outputWriter.write(contact.getPhoneNumber() + ",");

            // Need to write code for getbirthday and firstmet
    //      outputWriter.write(contact.getBirthday());
    //      outputWriter.write(contact.getFirstMet());

            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readContacts(List<Contact> contactList){
        try {
            FileInputStream filein = context.openFileInput("contactList.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0; i<contactList.size(); i++){
            contactList.get(i);
        }
    }

}
