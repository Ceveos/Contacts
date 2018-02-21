package com.fafaffy.contacts.Models;

import java.util.Date;

/**
 * Created by alex on 2/16/18.
 */

public class Contact {

    // Empty constructor
    public Contact() {

    }

    // Full constructor
    public Contact (String firstName, Character middleInitial, String lastName, String phoneNumber, Date birthday, Date firstMet) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.firstMet = firstMet;
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

    private String firstName;
    private Character middleInitial;
    private String lastName;
    private Date birthday;
    private Date firstMet;
    private String phoneNumber;

}
