package com.bugbusters.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.bugbusters.dto.MemeUploadRequest;
import com.bugbusters.entity.Meme;
import com.bugbusters.persistence.GenericDAO;
import com.bugbusters.service.S3ImageService;
import com.bugbusters.util.ImageOverlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import software.amazon.awssdk.regions.Region;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * This class is a RESTful API controller that handles HTTP requests related to
 * uploading, retrieving, and listing cat memes.
 * <p>
 * Base URI: <code>/api/cat-memes</code>
 * </p>
 */
@Path("cat-memes")
@Produces(MediaType.APPLICATION_JSON)
public class CatMemeResource {

    private static final Logger logger = LogManager.getLogger(CatMemeResource.class);

    private final GenericDAO<Meme> memeDao = new GenericDAO<>(Meme.class);

    private final S3ImageService s3Service =
            new S3ImageService("bug-busters-cat-meme", Region.US_EAST_2);

    private final ImageOverlay imageOverlay = new ImageOverlay();

    /**
     * Retrieves all memes from the database.
     *
     * @return JSON array of Meme objects
     */
    @Operation(summary = "Get all memes", description = "Retrieves a list of all cat memes stored in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the memes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Meme.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GET
    public Response getAll() {
        List<Meme> memes = memeDao.getAll();
        return Response.ok(memes).build();
    }

    /**
     * Retrieves a single meme by its ID.
     *
     * @param id the primary key
     * @return ok status with meme || not found status
     */
    @Operation(summary = "Get a meme by ID", description = "Retrieves a single cat meme by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the meme",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Meme.class))),
            @ApiResponse(responseCode = "404", description = "Meme not found")
    })
    @GET
    @Path("{id}")
    public Response getById(@Parameter(description = "ID of the meme to retrieve", required = true)
                            @PathParam("id") int id) {
        Meme meme = memeDao.getById(id);
        if (meme == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Meme not found for ID " + id)
                    .build();
        }
        return Response.ok(meme).build();
    }

    /**
     * Creates a new meme by uploading an image, overlaying text, saving it to S3,
     * and persisting a record in the database.
     *
     * @param imageStream InputStream of the uploaded image
     * @param fileDetail  Metadata about the uploaded file
     * @param topText     Text to overlay at the top of the image.
     * @param bottomText  Text to overlay at the bottom of the image.
     * @param uriInfo     Context for building the Location header URI.
     * @return created status with new meme || error status code
     */
    @Operation(
            summary = "Create a new meme",
            description = "Upload an image with overlay text to create a meme and save it.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA,
                            schema = @Schema(implementation = MemeUploadRequest.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meme created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Meme.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, missing or invalid input"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type, invalid image format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
            @FormDataParam("image") InputStream imageStream,
            @FormDataParam("image") FormDataContentDisposition fileDetail,
            @FormDataParam("topText") String topText,
            @FormDataParam("bottomText") String bottomText,
            @Context UriInfo uriInfo
    ) {
        if (imageStream == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No image uploaded.")
                    .build();
        }

        try {
            BufferedImage src = ImageIO.read(imageStream);
            if (src == null) {
                return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
                        .entity("Uploaded file is not a valid image.")
                        .build();
            }

            // Overlay text and upload to S3
            BufferedImage memeImg = imageOverlay.overlayText(src, topText, bottomText);

            // build a safe filename
            String keyName = String.format("memes/pepe-%d.png", System.currentTimeMillis());

            // upload & assemble URL
            s3Service.uploadImage(keyName, memeImg);
            String publicUrl = "https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/"
                    + keyName;

            // Persist in database
            Meme newMeme = new Meme(publicUrl, topText, bottomText);
            Meme inserted = memeDao.insert(newMeme);

            // Build URI for Location header: /api/cat-memes/{id}
            URI createdUri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(inserted.getId()))
                    .build();
            return Response.created(createdUri)
                    .entity(inserted)
                    .build();

        } catch (Exception e) {
            logger.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal error occurred during upload.")
                    .build();
        }
    }
}
