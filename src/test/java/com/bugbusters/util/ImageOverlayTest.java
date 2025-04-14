package com.bugbusters.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ImageOverlayTest {

    @Test
    public void testImageOverlay() {
        ImageOverlay imageOverlay = new ImageOverlay();

        URL imageURL = getClass().getClassLoader().getResource("images/test.png");

        assertNotNull(imageURL);

        File imageFile = new File(imageURL.getPath());

        imageOverlay.overlayText(imageFile.getPath(), "hibernateMakesMeSad.png", "When you get a", "hibernate error");
    }

}
