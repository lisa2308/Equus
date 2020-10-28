package com.lisap.equus.data.entities;

public class Horse {
    private String photo;
    private String name;
    private String owner;
    private String ownerPhone;

    public Horse() {}

    public Horse(String photo, String name, String owner, String ownerPhone){
        this.name = name;
        this.photo = photo;
        this.owner = owner;
        this.ownerPhone = ownerPhone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    @Override
    public String toString() {
        return "Horse{" +
                "photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                '}';
    }
}
