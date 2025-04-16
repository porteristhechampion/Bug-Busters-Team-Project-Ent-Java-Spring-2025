package com.bugbusters.api;

import com.bugbusters.service.S3ImageService;
import com.bugbusters.util.ImageOverlay;
import com.bugbusters.entity.Meme;
import com.bugbusters.persistence.GenericDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.regions.Region;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/api/cat-meme")
@MultipartConfig
public class UploadImageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UploadImageServlet.class);

    private final S3ImageService s3Service = new S3ImageService("bug-busters-cat-meme", Region.US_EAST_2);
    private final ImageOverlay imageOverlay = new ImageOverlay();
    private GenericDAO<Meme> memeDao;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part imagePart = request.getPart("image");
        String topText = request.getParameter("topText");
        String bottomText = request.getParameter("bottomText");

        //If the image size is 0 then that means that nothing was uploaded
        //Sends back a response of "No Image uploaded"
        //TODO send back proper error codes?
        if (imagePart == null || imagePart.getSize() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No image uploaded.");
            return;
        }

        try (InputStream inputStream = imagePart.getInputStream()) {
            memeDao = new GenericDAO<>(Meme.class);

            //Reads the image
            BufferedImage image = ImageIO.read(inputStream);
            //Checks to see if it's a supported image file
            if (image == null) {
                response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                response.getWriter().write("Uploaded file is not a valid image.");
                return;
            }

            //Apply ImageOverlay
            BufferedImage memeImage = imageOverlay.overlayText(image, topText, bottomText);
            //Create S3 Key and make it public URL?
            String fileName = "memes/" + "pepe" + System.currentTimeMillis() + ".png";
            String publicUrl = "https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/" + fileName;

            //Upload final meme image to s3
            s3Service.uploadImage(fileName, memeImage);

            //Save to DB
            Meme meme = new Meme(publicUrl, topText, bottomText);
            Meme insertedMeme = memeDao.insert(meme);

            //Return result to client
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write(insertedMeme.toString());

        } catch (Exception e) {
            logger.error("Failed to upload image", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal error occurred during upload.");
        }
    }
}

