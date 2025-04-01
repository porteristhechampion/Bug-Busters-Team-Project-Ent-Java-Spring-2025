package com.bugbusters.api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Path("/week9")
public class Week9ExerciseResource {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Context
    private HttpServletRequest request;

    @GET
    @Produces("text/plain")
    public Response getMeals() {
        logger.info("week9 called");

        List<String> cats = new ArrayList<>();
        cats.add("Bob");
        cats.add("Spot");
        cats.add("Violet");
        cats.add("Josie");
        cats.add("June");
        cats.add("April");
        cats.add("Ana");
        cats.add("Janis");
        cats.add("Aggie");
        cats.add("Amilia");
        cats.add("Alex");
        cats.add("Whiskey");
        cats.add("Zero");
        cats.add("Solar");
        cats.add("Ivanhoe");
        cats.add("Domino");
        cats.add("Cosmo");
        cats.add("Amber");
        cats.add("Larry");
        cats.add("Curley");
        cats.add("Moe");



        return Response.status(200).entity(cats.toString()).build();
    }
}



