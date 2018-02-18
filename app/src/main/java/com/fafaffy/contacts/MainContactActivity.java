package com.fafaffy.contacts;

/* Created by Alex Casasola & Brian Gardner */


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.fafaffy.contacts.Adapters.ContactRecyclerAdapter;
import com.fafaffy.contacts.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainContactActivity extends AppCompatActivity {





    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private List<Contact> mData;
    ContactRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);








        fab = (FloatingActionButton) findViewById(R.id.addContactButton);
        recyclerView = (RecyclerView) findViewById(R.id.mainPageRecyclerView);

        // Create an empty array list in order to define our recycler view adapter
        mData = new ArrayList<>();
        recyclerAdapter = new ContactRecyclerAdapter(mData);


        // Set the properties of the recyclerview (the layout, and animations)
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Logic on when to hide or show the FAB button
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 && fab.isShown())
                    fab.hide();
                if (dy < 0 && !fab.isShown())
                    fab.show();
            }
        });

        // Set our listview's adapter
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    public void launchDetailContactActivity(View view){
        Intent myIntent = new Intent(this,
                DetailContact.class);
        startActivity(myIntent);
    }

    public void testClick(View view){
        Log.v("myTag","FAB Clicked");
    }




}
