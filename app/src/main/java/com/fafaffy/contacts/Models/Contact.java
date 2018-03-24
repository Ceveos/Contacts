package com.fafaffy.contacts.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alex on 2/16/18.
 */

public class Contact implements Serializable, Comparable<Contact> {

    // Empty constructor
    public Contact() {

    }

    // Full constructor plus id
    public Contact (String firstName, Character middleInitial, String lastName, String phoneNumber, Object birthday, Date firstMet, int id) {
        this(firstName, middleInitial, lastName, phoneNumber, birthday, firstMet);
        this.id = id;
    }

    // Full constructor
    public Contact (String firstName, Character middleInitial, String lastName, String phoneNumber, Object birthday, Date firstMet) {
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

    public void setID(int id) { this.id = id; }
    public int getID() { return this.id; }

    private String firstName;
    private Character middleInitial;
    private String lastName;
    private Date birthday;
    private Date firstMet;
    private String phoneNumber;
    private int id;

}
