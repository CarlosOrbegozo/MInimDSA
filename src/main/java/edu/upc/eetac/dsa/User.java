package edu.upc.eetac.dsa;

import java.util.LinkedList;

public class User {
    private String idUser;
    private String name;
    private String surname;
    private LinkedList<Bike> bikesUserList;

    public User(){}
    public User(String userid){
        idUser = userid;
        this.name = null;
        this.surname = null;
        this.bikesUserList = new LinkedList<>();
    }
    public User(String idUser, String name, String surname) {
        this.idUser = idUser;
        this.name = name;
        this.surname = surname;
        this.bikesUserList = new LinkedList<>();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LinkedList<Bike> getBikesUserList() {
        return bikesUserList;
    }

    public void setBikesUserList(LinkedList<Bike> bikesUserList) {
        bikesUserList = bikesUserList;
    }
    public void addBike(Bike b){
        bikesUserList.add(b);
    }
}
