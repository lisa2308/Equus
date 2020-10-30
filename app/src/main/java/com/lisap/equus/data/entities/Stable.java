package com.lisap.equus.data.entities;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Stable {
    @Exclude
    private String idStable;
    private String name;
    private String accessCode;

    public Stable() {}

    public String getIdStable() {
        return idStable;
    }

    public void setIdStable(String idStable) {
        this.idStable = idStable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    @Override
    public String toString() {
        return "Stable{" +
                "idStable='" + idStable + '\'' +
                ", name='" + name + '\'' +
                ", accessCode='" + accessCode + '\'' +
                '}';
    }
}
