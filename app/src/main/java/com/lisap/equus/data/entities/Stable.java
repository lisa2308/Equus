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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stable stable = (Stable) o;

        return idStable != null ? idStable.equals(stable.idStable) : stable.idStable == null;
    }

    @Override
    public int hashCode() {
        return idStable != null ? idStable.hashCode() : 0;
    }
}
