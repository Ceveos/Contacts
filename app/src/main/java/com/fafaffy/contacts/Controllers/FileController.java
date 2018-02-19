package com.fafaffy.contacts.Controllers;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


//Created by Brian on 2/18/18.


public class FileController extends Activity {

    // Internal Storage Filename
    private final static String INTERNAL_STORAGE_FILE="contacts.txt";


    // Empty Constructor
    public FileController(){

    }


//    public void saveContact(View v, String textmsg) {
//        // add-write text into file
//        try {
//
//
//
//            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
//            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
//            outputWriter.write(textmsg);
//            outputWriter.close();
//
//            //display file saved message
//            Toast.makeText(getBaseContext(), "File saved successfully!",
//                    Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




    public void saveContact(String data) {
        Log.v("FileController: ", "saveContactMethod Reached" );


        try {
            File file = new File(Environment.DIRECTORY_DOCUMENTS, INTERNAL_STORAGE_FILE);
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            FileWriter writer = new FileWriter(file, true);
            if(!file.exists()){
                Log.v("FileController: ", "File does NOT exist. " );
                //create file and write.
                //file.createNewFile();
                writer.append(data);
                writer.flush();
                writer.close();
            }
            else {
                Log.v("FileController: ", "File exists. Appending" );
                writer.append(data);
                writer.flush();
                writer.close();
            }
            Log.v("FileController: ", "SAVED FILE" );

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("FileController: ", "FAILED TO SAVE FILE" );
        }
    }
    
}
