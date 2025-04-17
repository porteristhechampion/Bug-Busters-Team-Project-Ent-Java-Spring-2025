package com.bugbusters.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MemeUploadRequest {

    @Schema(type = "string", format = "binary", description = "Image file to upload")
    public org.glassfish.jersey.media.multipart.FormDataBodyPart image;

    @Schema(description = "Top text for the meme")
    public String topText;

    @Schema(description = "Bottom text for the meme")
    public String bottomText;
}
