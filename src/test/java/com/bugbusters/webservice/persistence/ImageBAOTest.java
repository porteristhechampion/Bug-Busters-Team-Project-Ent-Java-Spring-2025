package com.bugbusters.webservice.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageBAOTest {

    ImageBAO imageBAO = new ImageBAO();

    String filename = "./cat2.jpg";
    @Test
    void uploadImage() {
        String message = imageBAO.uploadImage(filename);
        System.out.println(message);
        assertTrue(message.length() > 0);
    }
}