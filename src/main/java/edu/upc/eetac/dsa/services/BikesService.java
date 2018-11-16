package edu.upc.eetac.dsa.services;
import edu.upc.eetac.dsa.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Api(value="/BikesService", description = "Endpoint to Bike Service")
@Path("/BikeService")
public class BikesService {
    final static Logger log = Logger.getLogger(BikesService.class);
    private MyBike mb;

    public BikesService() throws StationFullException, StationNotFoundException {

        this.mb = MyBikeImpl.getInstance();
        this.mb.addUser("user1", "Juan", "Lopex");


        this.mb.addStation("Station1","description:: station1", 10, 3, 3);
        this.mb.addStation("Station2","description:: station2", 10, 3, 3);


        this.mb.addBike("bike101", "descripton", 25.45, "Station1");
        this.mb.addBike("bike102", "descripton", 70.3, "Station1");
        this.mb.addBike("bike103", "descripton", 10.2, "Station1");


        this.mb.addBike("bike201", "descripton", 1325.45, "Station2");
        this.mb.addBike("bike202", "descripton", 74430.3, "Station2");
        this.mb.addBike("bike203", "descripton", 1320.2, "Station2");

    }

    @GET
    @ApiOperation(value = "get bikes of a user", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer = "List of Orders"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("user") String user) {
        List<Bike> listOrders;//pedidos
        try {
            listOrders = this.mb.bikesByUser(user);
            for(Bike o:listOrders) {
                log.info("Bike: " + o.toString());
            }
            GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listOrders){};
            return Response.status(201).entity(entity).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }


    @GET
    @ApiOperation(value = "get bikes sorted by kms", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer = "List of Products")
    })
    @Path("/sortedBikes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBikesSortedBykMS(@PathParam("stationid") String station) throws StationNotFoundException {
        List<Bike> listproducts  = this.mb.bikesByStationOrderByKms(station);

        GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listproducts){};
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "add User", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful")
    })
    @Path("/adduser/{user}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u){
        mb.addUser(u.getIdUser(),u.getName(),u.getSurname());

        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "add Bike", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful")
    })
    @Path("/addBike")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBike(Bike p,String stationId) throws StationFullException, StationNotFoundException {
        mb.addBike(p.getBikeId(),p.getDescription(),p.getKms(),stationId);

        return Response.status(201).build();
    }


    @POST
    @ApiOperation(value = "add station", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful")
    })
    @Path("/addStation")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStation(Station p){
        mb.addStation(p.getIdStation(),p.getDescription(),p.getMax(),p.getLat(),p.getLon());

        return Response.status(201).build();
    }


    @DELETE
    @ApiOperation(value = "get a bike", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer = "Order")
    })
    @Path("/serveanorder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBike(String userID, String stationId) throws UserNotFoundException, StationNotFoundException {
        Bike order = mb.getBike(userID,stationId);

        return Response.status(201).entity(order).build();
    }

}
