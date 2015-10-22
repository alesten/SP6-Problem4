/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Car;
import facade.CarFacade;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Alexander
 */
@Path("car")
public class CarResource {

    @Context
    private UriInfo context;
    private Gson gson;
    private CarFacade carFacade;
    /**
     * Creates a new instance of CarResource
     */
    
    
    public CarResource() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
        carFacade = new CarFacade(Persistence.createEntityManagerFactory("SP6-Problem4PU"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar() {
        return Response.ok(gson.toJson(carFacade.GetCars()), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editCar(Car car) {
        carFacade.EditCar(car);
        return Response.status(Response.Status.OK).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteCar(@PathParam("id") String id){
        carFacade.DeleteCar(new Long(id));
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(Car car){
        carFacade.AddCar(car);
        return Response.status(Response.Status.CREATED).build();
    }
}
