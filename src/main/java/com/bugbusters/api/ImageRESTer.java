package com.bugbusters.api;


import com.bugbusters.webservice.persistence.ImageBAO;

import javax.ws.rs.*;

@Path("/cat-memes")
public class ImageRESTer {

    ImageBAO imageBAO = new ImageBAO();

    @PUT
    @Path("/images/{filename}")
    public void putImage(@PathParam("filename") String filename) {

        // Have Image Bucket Access Object upload it.
        String output = imageBAO.uploadImage(filename);

        System.out.println("Filename Received: " + filename);
        System.out.println("Upload Result: " + output);

        // Not doing anything with return code, probably should.
    }
}