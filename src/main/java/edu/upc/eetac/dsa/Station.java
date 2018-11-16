package edu.upc.eetac.dsa;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;

public class Station {
    final static Logger logger = Logger.getLogger(Station.class.getName());
    private String idStation;
    private String description;
    private int max;
    private double lat;
    private double lon;
    private LinkedList<Bike> listBikes;
    public Station (){}

    public Station(String idStation, String description, int max, double lat, double lon) {
        this.idStation = idStation;
        this.description = description;
        this.max = max;
        this.lat = lat;
        this.lon = lon;
        listBikes = new LinkedList<>();
    }

    public String getIdStation() {
        return idStation;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    public void addBike(Bike bike){
        listBikes.add(bike);
        logger.debug("added bike "+bike.getBikeId());
    }

    public LinkedList<Bike> getListBikes() {
        return listBikes;
    }
}
