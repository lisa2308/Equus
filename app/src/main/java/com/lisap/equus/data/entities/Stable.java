package com.lisap.equus.data.entities;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Stable {
    private String idStable;
    private String stableName;

    public Stable() {}

    @Exclude
    public String getIdStable() {
        return idStable;
    }

    public void setIdStable(String idStable) {
        this.idStable = idStable;
    }

    public String getStableName() {
        return stableName;
    }

    public void setStableName(String stableName) {
        this.stableName = stableName;
    }

    @Override
    public String toString() {
        return "Stable{" +
                "idStable='" + idStable + '\'' +
                ", stableName='" + stableName + '\'' +
                '}';
    }
}
