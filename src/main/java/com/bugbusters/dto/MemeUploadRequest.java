package com.bugbusters.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class represents the body of a request sent to the api.
 * It sets the blueprint for the uploaded image, and the top
 * and bottom text.
 *
 * @author Alison Fait
 */
public class MemeUploadRequest {

    @Schema(type = "string", format = "binary", description = "Image file to upload")
    public org.glassfish.jersey.media.multipart.FormDataBodyPart image;

    @Schema(description = "Top text for the meme")
    public String topText;

    @Schema(description = "Bottom text for the meme")
    public String bottomText;

}
