package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * This class tests the image overlay class.
 *
 * @author ptaylor
 */
public class ImageOverlayTest {

    private static final Logger logger = LogManager.getLogger(ImageOverlay.class);

    /**
     * This method tests the overlayText method in the image
     * overlay class.
     */
    @Test
    public void testImageOverlay() {

        ImageOverlay imageOverlay = new ImageOverlay();

        try {
            BufferedImage image1 = ImageIO.read(new File("src/test/resources/images/test.png"));
            BufferedImage image2 = ImageIO.read(new File("src/test/resources/images/test.png"));

            assertNotNull(image1);
            assertNotNull(image2);

            imageOverlay.overlayText(image1, "NOBODY KNOWS", "I'M BARELY HOLDING IT TOGETHER");

            ImageIO.write(image1, "png", new File("src/test/resources/images/testOutput.png"));

            imageOverlay.overlayText(image2, "ONE SPACEBAR CAN CHANGE THE WORD \"NOWHERE\" TO \"NOW HERE\"", "UNTIL WE MEET AGAIN");

            ImageIO.write(image2, "png", new File("src/test/resources/images/testOutput2.png"));

        } catch (IOException io) {

            logger.error(io);

        }
    }

}
