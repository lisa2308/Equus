package com.lisap.equus.data.entities;

import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Owner implements Serializable {
    private String ownerId;
    private String lastname;
    private String firstname;
    private String phone;

    public Owner() {}

    @Exclude
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
