package com.lisap.equus.data.entities;

import java.util.List;

public class Stable {
    private String uid;
    private List<Horse> horses;
    private String stableName;
    private String password;

    public Stable() {}

    public Stable(String uid, List<Horse> horses, String stableName, String password) {
        this.uid = uid;
        this.horses = horses;
        this.stableName = stableName;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void setHorses(List<Horse> horses) {
        this.horses = horses;
    }

    public String getStableName() {
        return stableName;
    }

    public void setStableName(String stableName) {
        this.stableName = stableName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Stable{" +
                "uid='" + uid + '\'' +
                ", horses=" + horses +
                ", stableName='" + stableName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
