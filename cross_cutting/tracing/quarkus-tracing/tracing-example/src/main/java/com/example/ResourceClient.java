package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public interface ResourceClient {

    @GET
    @Path("/service1")
    @Produces(MediaType.TEXT_PLAIN)
    String service1();

    @GET
    @Path("/service1/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    String service1(@PathParam("name") String test);

    @GET
    @Path("/customers")
    @Produces(MediaType.TEXT_PLAIN)
    String customerService();
}
