package com.lisap.equus.data.entities;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class HealthCare implements Serializable {

    private String healthCareId;
    private String horseId;
    private String problem;
    private String specialist;

    public HealthCare() {}

    @Exclude
    public String getHealthCareId() {
        return healthCareId;
    }

    public void setHealthCareId(String healthCareId) {
        this.healthCareId = healthCareId;
    }

    public String getHorseId() {
        return horseId;
    }

    public void setHorseId(String horseId) {
        this.horseId = horseId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }
}
