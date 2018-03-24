package com.fafaffy.contacts.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fafaffy.contacts.DetailContact;
import com.fafaffy.contacts.Models.Contact;
import com.fafaffy.contacts.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2/17/18.
 */

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder> {

    public ArrayList<Contact> mDataset;

    // Inner class that provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        public TextView name;

        // Constructor that takes in a view and sets the textview references to the class
        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            name = v.findViewById(R.id.name);
        }

    }

    // Constructor for our class. Sets the dataset (list of stock data) to our list
    public ContactRecyclerAdapter(ArrayList<Contact> myDataset)
    {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact_row, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Contact dataModel = mDataset.get(position);
        holder.name.setText(dataModel.getFirstName());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent;
                intent =  new Intent(holder.context, DetailContact.class);
                intent.putExtra("contacts", mDataset);
                intent.putExtra("index", dataModel.getID());
                ((Activity)holder.context).startActivityForResult(intent, 1);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
