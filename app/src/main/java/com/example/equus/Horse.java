package com.example.equus;

public class Horse {

        private String photo;
        private String name;
        private String proprietaire;
        private String telProprio;

        public Horse(String photo, String name, String proprietaire, String telProprio){
            this.name = name;
            this.photo = photo;
            this.proprietaire = proprietaire;
            this.telProprio = telProprio;
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
}
