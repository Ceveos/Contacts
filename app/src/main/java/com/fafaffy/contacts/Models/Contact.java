package com.fafaffy.contacts.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import javax.crypto.Cipher;

/**
 * Created by alex on 2/16/18.
 */

public class Contact implements Serializable, Comparable<Contact> {

    // Empty constructor
    public Contact() {

    }

    // Full constructor plus id
    public Contact (String firstName, Character middleInitial, String lastName,
                    String phoneNumber, Object birthday, Date firstMet, int id,
                    String addressLineOne, String addressLineTwo, String city,
                    String state, String zipCode) {
        this(firstName, middleInitial, lastName, phoneNumber, birthday, firstMet, addressLineOne, addressLineTwo, city, state, zipCode);
        this.id = id;
    }



    // Full constructor
    public Contact (String firstName, Character middleInitial, String lastName,
                    String phoneNumber, Object birthday, Date firstMet,
                    String addressLineOne, String addressLineTwo, String city,
                    String state, String zipCode) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
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

    //Phase 4
    public String getAddressLineOne(){return addressLineOne;}
    public String getAddressLineTwo(){return addressLineTwo;}
    public String getCity(){return city;}
    public String getState(){return state;}
    public String getZipCode(){return zipCode;}

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    private String firstName;
    private Character middleInitial;
    private String lastName;
    private Date birthday;
    private Date firstMet;
    private String phoneNumber;
    private int id;

    //PHASE 4 Attribute Additions:
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String state;
    private String zipCode;;

}
