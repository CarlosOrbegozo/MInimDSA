package edu.upc.eetac.dsa;

public class Bike {
    private String bikeId;
    private String description;
    private double kms;

    public Bike(){}

    public Bike(String idBike, String description, double kms) {
        this.bikeId = idBike;
        this.description = description;
        this.kms = kms;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String idBike) {
        this.bikeId = idBike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getKms() {
        return kms;
    }

    public void setKms(double kms) {
        this.kms = kms;
    }


}
