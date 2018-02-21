package com.fafaffy.contacts.Controllers;
import android.app.Activity;
import android.content.Context;
import com.fafaffy.contacts.Models.Contact;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

//Created by Brian on 2/18/18.

public class FileController extends Activity {

    // variable to hold context
    private Context context;

    // Empty Constructor
    public FileController(Context context){
        this.context = context;
    }

    // FUNCTION IS BROKE!
    public void saveContact(Contact contact) {
        // Write to file using comma delimited structure for each field
        try {
            FileOutputStream fileout = context.openFileOutput("mytextfile.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(contact.getFirstName() + ",");
            outputWriter.write(contact.getMiddleInitial() + ",");
            outputWriter.write(contact.getLastName() + ",");
            outputWriter.write(contact.getPhoneNumber() + ",");
    //      outputWriter.write(contact.getBirthday());
    //      outputWriter.write(contact.getFirstMet());
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
