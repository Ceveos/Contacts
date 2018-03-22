package com.fafaffy.contacts.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alex on 2/16/18.
 * Update by Brian on 3/22/18 to include id field with inclusion of SQL
 * id field added to getter and setter, but NOT included as part of the constructor.
 * We only need the id field after the contact has been added to the db, and then we can query
 * the db to attach an id to it for update purposes.
 */

public class Contact implements Serializable, Comparable<Contact> {

    // Empty constructor
    public Contact() {

    }

    // Full constructor
    public Contact ( String firstName, Character middleInitial, String lastName, String phoneNumber, Object birthday, Date firstMet) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        if (birthday != null) {

            this.birthday = (Date)birthday;
        }
        this.firstMet = firstMet;
    }


    /// Created by Alex 03/04/2018
    @Override
    public int compareTo(@NonNull Contact contact) {
        return this.firstName.compareTo(contact.getFirstName());
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Character getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(Character middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getFirstMet() {
        return firstMet;
    }

    public void setFirstMet(Date firstMet) {
        this.firstMet = firstMet;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private int id;
    private String firstName;
    private Character middleInitial;
    private String lastName;
    private Date birthday;
    private Date firstMet;
    private String phoneNumber;

}
