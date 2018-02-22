package com.fafaffy.contacts.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by alex on 2/17/18.
 */

public final class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private Date selectedDate;
    private Button bt;

    public Date getSelectedDate() {
        return selectedDate;
    }
    public void setButton(Button bt) {
        this.bt = bt;
    }

    public DatePickerFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        // Do something with the date chosen by the user
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        selectedDate = calendar.getTime();
        bt.setText(dateFormat.format(selectedDate));
    }
}