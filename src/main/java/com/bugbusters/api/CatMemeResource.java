package com.bugbusters.api;

import com.bugbusters.entity.Meme;
import com.bugbusters.persistence.GenericDAO;
import com.bugbusters.service.S3ImageService;
import com.bugbusters.util.ImageOverlay;
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
 * JAX‑RS resource exposing RESTful endpoints for creating and retrieving cat memes.
 * <p>
 * Base URI: <code>/api/cat-memes</code>
 * </p>
 */
@Path("/cat-memes")
@Produces(MediaType.APPLICATION_JSON)
public class CatMemeResource {

    /** DAO for persisting and querying Meme entities. */
    private final GenericDAO<Meme> memeDao = new GenericDAO<>(Meme.class);

    /** Service for uploading images to AWS S3. */
    private final S3ImageService s3Service =
            new S3ImageService("bug-busters-cat-meme", Region.US_EAST_2);

    /** Utility for overlaying text onto images. */
    private final ImageOverlay imageOverlay = new ImageOverlay();

    /**
     * Retrieves all memes.
     *
     * @return HTTP 200 with a JSON array of all {@link Meme} objects.
     */
    @GET
    public Response getAll() {
        List<Meme> memes = memeDao.getAll();
        return Response.ok(memes).build();
    }

    /**
     * Retrieves a single meme by its ID.
     *
     * @param id the primary key of the meme to fetch.
     * @return HTTP 200 with the {@link Meme} if found;
     *         HTTP 404 with an error message if not found.
     */
    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
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
     * @param imageStream  InputStream of the uploaded image; must not be {@code null}.
     * @param fileDetail   Metadata about the uploaded file (e.g., original filename).
     * @param topText      Text to overlay at the top of the image.
     * @param bottomText   Text to overlay at the bottom of the image.
     * @param uriInfo      Context for building the Location header URI.
     * @return HTTP 201 with Location header pointing to the new resource and
     *         a JSON body of the created {@link Meme}; or an appropriate error status
     *         if validation or processing fails.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
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
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal error occurred during upload.")
                    .build();
        }
    }
}
