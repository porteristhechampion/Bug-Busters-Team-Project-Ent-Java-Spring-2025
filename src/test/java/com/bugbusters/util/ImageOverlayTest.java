package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ImageOverlayTest {

    private static final Logger logger = LogManager.getLogger(ImageOverlay.class);

    @Test
    public void testImageOverlay() {

        ImageOverlay imageOverlay = new ImageOverlay();

        try {
            BufferedImage image = ImageIO.read(new File("src/test/resources/images/test.png"));

            assertNotNull(image);

            imageOverlay.overlayText(image, "When you get a", "HIBERNATE error");

            ImageIO.write(image, "png", new File("src/test/resources/images/testOutput.png"));

        } catch (IOException io) {

            logger.error(io);

        }
    }

}
