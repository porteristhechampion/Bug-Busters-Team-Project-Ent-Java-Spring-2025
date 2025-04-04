package com.bugbusters.api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import com.bugbusters.webservice.persistence.GenericDAO;
import com.bugbusters.webservice.entity.Meme;

/**
 * The type Week 9 exercise resource.
 */
@Path("/week9")
public class Week9ExerciseResource {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * The Meme dao.
     */
    GenericDAO memeDao;

    @Context
    private HttpServletRequest request;

    /**
     * Gets memes.
     *
     * @return the memes
     */
    @GET
    @Produces("text/plain")
    public Response getMemes() {

        memeDao = new GenericDAO();

        logger.info("week9 called");

        List<Meme> memes = memeDao.getAll();

        return Response.status(200).entity(memes.toString()).build();
    }
}



