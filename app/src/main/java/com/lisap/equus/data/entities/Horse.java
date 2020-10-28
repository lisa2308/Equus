package com.lisap.equus.data.entities;

public class Horse {
    private String name;
    private String imageUrl;
    private boolean isStopped;

    public Horse() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    @Override
    public String toString() {
        return "Horse{" +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isStopped=" + isStopped +
                '}';
    }
}
