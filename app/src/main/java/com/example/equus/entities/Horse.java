package com.example.equus.entities;

public class Horse {

        private String photo;
        private String name;
        private String proprietaire;
        private String telProprio;
        private String uid;

        public Horse(String photo, String name, String proprietaire, String telProprio, String uid){
            this.name = name;
            this.photo = photo;
            this.proprietaire = proprietaire;
            this.telProprio = telProprio;
            this.uid = uid;
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

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String getTelProprio() {
        return telProprio;
    }

    public void setTelProprio(String telProprio) {
        this.telProprio = telProprio;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
