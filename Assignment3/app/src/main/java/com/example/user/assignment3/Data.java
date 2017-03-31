package com.example.user.assignment3;

import android.graphics.Bitmap;

/**
 * Created by user on 3/30/2017.
 */

public class Data {

    private Bitmap imageID;

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    private String dateofBirth;
    public Bitmap getImageID() {
        return imageID;
    }

    public void setImageID(Bitmap imageID) {
        this.imageID = imageID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String lastName;
    public Data( Bitmap img,String first,String last,String dob){
        this.imageID=img;
        this.dateofBirth=dob;
        this.firstName=first;
        this.lastName=last;


    }



}
