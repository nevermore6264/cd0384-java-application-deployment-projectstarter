package com.udacity.image.service;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Service that tries to guess if an image displays a cat.
 *
 * @author HieuTT121
 * @since 15/01/2024
 */
public class FakeImageService implements ImageService {
    private final Random r = new Random();

    /**
     * func imageContainsCat
     *
     * @param image               BufferedImage
     * @param confidenceThreshold float
     * @return boolean
     */
    public boolean imageContainsCat(BufferedImage image, float confidenceThreshold) {
        return r.nextBoolean();
    }
}
