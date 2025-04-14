package com.bugbusters.util;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class ImageOverlayTest {

    @Test
    public void testImageOverlay() {
        ImageOverlay imageOverlay = new ImageOverlay();

        URL imageURL = getClass().getClassLoader().getResource("images/test.png");

        assertNotNull(imageURL);

        imageOverlay.overlayText("src/test/resources/images/test.png", "src/test/resources/images/hibernateMakesMeSad.png", "When you get a", "HIBERNATE error");
    }

}
