package com.fafaffy.contacts.Controllers;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fafaffy.contacts.MainContactActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

//Created by Brian on 2/18/18.


public class FileController {

    // Internal Storage Filename
    private final static String INTERNAL_STORAGE_FILE="storage.txt";

    // Empty Constructor
    public FileController(){
    }


    public void saveContact(String data) {
        Log.v("FileController: ", "saveContactMethod Reached" );
        try {
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + "ContactData", "ContactList");
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, INTERNAL_STORAGE_FILE);

            FileWriter writer = new FileWriter(file, true);
            writer.append(data + "\n\n");
            writer.flush();
            writer.close();
            Log.v("FileController: ", "SAVED FILE" );

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("FileController: ", "FAILED TO SAVE FILE" );
        }
    }


    /*
    public void saveClicked(View v) {
        try {
            outputStream.
            outputStream = openFileOutput(MainContactActivity.INTERNALSTORAGEFILE, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            Log.v("FileController: ", "SAVED" );
        }
        catch (Throwable t) {
            Log.v("FileController: ", "FAILED" );
        }

    }
*/



}
