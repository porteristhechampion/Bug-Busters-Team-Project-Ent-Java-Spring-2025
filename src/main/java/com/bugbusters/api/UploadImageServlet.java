package com.bugbusters.api;

import com.bugbusters.service.S3ImageService;
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

@WebServlet("/api/upload-image")
@MultipartConfig
public class UploadImageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UploadImageServlet.class);

    private final S3ImageService s3Service = new S3ImageService("bug-busters-cat-meme", Region.US_EAST_2);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part imagePart = request.getPart("image");
        if (imagePart == null || imagePart.getSize() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No image uploaded.");
            return;
        }

        try (InputStream inputStream = imagePart.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                response.getWriter().write("Uploaded file is not a valid image.");
                return;
            }

            String fileName = "uploads/" + System.currentTimeMillis() + ".png";
            s3Service.uploadImage(fileName, image);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Image uploaded successfully to S3 as: " + fileName);
        } catch (Exception e) {
            logger.error("Failed to upload image", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal error occurred during upload.");
        }
    }
}

