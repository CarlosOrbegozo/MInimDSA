package edu.upc.eetac.dsa;

import org.apache.log4j.Logger;

import java.util.*;

public class MyBikeImpl implements MyBike {
    // singleton
    final static Logger logger = Logger.getLogger(MyBikeImpl.class.getName());
    private static MyBike instance;
    private HashMap<String,User> userList;
    private List<Station> stationList;

    private MyBikeImpl(){
        userList = new HashMap<>();
        stationList= new ArrayList<>();
    }
    public static MyBike getInstance(){
        if(instance==null){
            instance = new MyBikeImpl();
        }
        return instance;
    }

    public List<User> getUserList2() {
        List<User> aux = new ArrayList<>();
        aux.addAll(userList.values());
        return aux;
    }

    @Override
    public void addUser(String idUser, String name, String surname) {
        logger.info("adding user...");
        userList.put(idUser,new User(idUser,name,surname));
        logger.info("created user: "+name);
    }

    @Override
    public void addStation(String idStation, String description, int max, double lat, double lon) {
        logger.info("adding Station...");
        stationList.add(new Station(idStation,description,max,lat,lon));
        logger.info("added station: "+ idStation);
    }

    @Override
    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException {
        logger.info("adding bike...");
        for(Station s:stationList){
            if(s.getIdStation().equals(idStation)){
                s.addBike(new Bike(idBike,description,kms));
            }
        }
        logger.info("bike "+idBike+" added in station " + idStation);
    }

    @Override
    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {
        logger.info("sorting bikes from station: "+idStation);
        LinkedList<Bike> listBikesSorted = new LinkedList<>();
        for(Station s: stationList){
            if(s.getIdStation().equals(idStation)){
                List<Bike> bikes = s.getListBikes();
                for(Bike b : bikes){
                    listBikesSorted.add(b);
                }
                Collections.sort(listBikesSorted,(b1,b2)->(int)(100*b1.getKms()-100*b2.getKms()));
            }
        }
        logger.info("Bikes sorted by Km of Station: "+idStation );
        return listBikesSorted;
    }

    @Override
    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {
        logger.info("getting a bike of User: "+userId+"from station: "+ stationId);
        Bike result = null;
        for(Station s: stationList){
           if(s.getIdStation().equals(stationId)){
               result = s.getListBikes().pop();
           }
        }
        for(User u : getUserList2()){
            if(u.getIdUser().equals(userId)){
                u.addBike(result);
            }
        }
        logger.info("got it");
        return result;

    }

    @Override
    public List<Bike> bikesByUser(String userId) throws UserNotFoundException {
        logger.info("getting bikes from user: "+userId);
        LinkedList<Bike> listBikesUser = new LinkedList<>();
        List<User> aux = new ArrayList<>();
        aux.addAll(userList.values());
        for(User u: aux){
            if(u.getIdUser().equals(userId)){
                listBikesUser = u.getBikesUserList();
            }
        }
        logger.info("done");
        return listBikesUser;
    }

    @Override
    public int numUsers() {
        logger.info("getting numberUser");
        return userList.size();
    }

    @Override
    public int numStations() {
        logger.info("getting numStation");
        return stationList.size();
    }

    @Override
    public int numBikes(String idStation) throws StationNotFoundException {
        logger.info("getting number of bikes from station: "+idStation);
        int result=0;
        for(Station s: stationList){
            if(s.getIdStation().equals(idStation)){
                result =s.getListBikes().size();
            }
        }
        logger.info("done");
        return result;
    }

    @Override
    public void clear() {
        this.stationList.clear();
        this.userList.clear();
        logger.info("DB cleared");

    }
}
