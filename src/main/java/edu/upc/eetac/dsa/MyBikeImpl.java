package edu.upc.eetac.dsa;

import org.apache.log4j.Logger;

import java.util.*;

public class MyBikeImpl implements MyBike {
    // singleton
    final static Logger logger = Logger.getLogger(MyBikeImpl.class.getName());
    private static MyBike instance;
    private HashMap<String,User> userList;
    private Station [] stationList;
    public static final int S = 10;

    private MyBikeImpl(){
        userList = new HashMap<>();
        stationList = new Station[S];
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
        int count = 0;
        for(Station s:stationList){
            if(s!=null)count++;
        }
        if(count<=S){
            stationList[count]=new Station(idStation,description,max,lat,lon);
        }

        logger.info("added station: "+ idStation);
    }

    @Override
    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException {
        logger.info("adding bike...");
        boolean b = false;
        int count = 0;
        for(Station s:stationList){
            if(s!=null)count++;
        }
        for(int i =0;i<count;i++){
            if(stationList[i].getIdStation().equals(idStation)){
                b=true;
                if(stationList[i].getListBikes().size()==stationList[i].getMax()){
                    throw new StationFullException();
                }
                stationList[i].addBike(new Bike(idBike,description,kms));

            }
        }
        if(!b)throw new StationNotFoundException();
        logger.info("bike "+idBike+" added in station " + idStation);
    }

    @Override
    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {
        logger.info("sorting bikes from station: "+idStation);
        LinkedList<Bike> listBikesSorted = new LinkedList<>();
        boolean bb = false;
        int count = 0;
        for(Station s:stationList){
            if(s!=null)count++;
        }
        for(int i =0;i<count;i++){
            if(stationList[i].getIdStation().equals(idStation)){
                bb=true;
                List<Bike> bikes = stationList[i].getListBikes();
                for(Bike b : bikes){
                    listBikesSorted.add(b);
                }
                Collections.sort(listBikesSorted,(b1,b2)->(int)(100*b1.getKms()-100*b2.getKms()));
            }
        }

        if(!bb)throw new StationNotFoundException();
        logger.info("Bikes sorted by Km of Station: "+idStation );
        return listBikesSorted;
    }

    @Override
    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {
        logger.info("getting a bike of User: "+userId+"from station: "+ stationId);
        Bike result = null;
        int count = 0;
        for(Station s:stationList){
            if(s!=null)count++;
        }
        for(int i =0;i<count;i++){
           if(stationList[i].getIdStation().equals(stationId)){
               result = stationList[i].getListBikes().pop();
           }
        }
        //
        if (result==null) throw new StationNotFoundException();
        boolean b= false;
        for(User u : getUserList2()){
            if(u.getIdUser().equals(userId)){
                u.addBike(result);
                b=true;
            }
        }
        if(!b)throw new UserNotFoundException();
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
        //
        if(listBikesUser.isEmpty()){
            throw new UserNotFoundException();
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
        int count = 0;
        for(int i =0;i<S;i++){
            if(stationList[i]!=null){
                count++;
            }
        }
        return count;
    }

    @Override
    public int numBikes(String idStation) throws StationNotFoundException {
        int result=0;
        int count = 0;
        boolean b=false;
        logger.info("getting number of bikes from station: "+idStation);

        for(Station s:stationList){
            if(s!=null)count++;
        }

        for(int i =0;i<count;i++){
            if(stationList[i].getIdStation().equals(idStation)){
                result =stationList[i].getListBikes().size();
                b=true;
            }
        }
        //
        if(!b){
            throw new StationNotFoundException();
        }

        logger.info("done");


        return result;
    }

    @Override
    public void clear() {
        Arrays.fill(stationList,null);
        this.userList.clear();
        logger.info("DB cleared");

    }
}
