package com.lisap.equus.data.entities;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Horse implements Serializable {
    private String horseId;
    private String name;
    private boolean isStopped;
    private String ownerId;

    public Horse() {}

    @Exclude
    public String getHorseId() {
        return horseId;
    }

    public void setHorseId(String horseId) {
        this.horseId = horseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Horse{" +
                "horseId='" + horseId + '\'' +
                ", name='" + name + '\'' +
                ", isStopped=" + isStopped +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }
}
