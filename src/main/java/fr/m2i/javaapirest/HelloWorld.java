package fr.m2i.javaapirest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello")           // dans webxml on a definit l uri du servlet /api/*
public class HelloWorld {

    @GET
    @Produces("text/plain")  // type de la réponse
    // @Path("/world")  : api/hello/world  -- sans ce patch par defaut api/hello
    public String getHelloWorld() {
        return "Hello World from text/plain";
    }

}
