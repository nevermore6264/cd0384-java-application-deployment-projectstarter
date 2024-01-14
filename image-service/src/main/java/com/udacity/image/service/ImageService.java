package com.udacity.image.service;

import java.awt.image.BufferedImage;

public interface ImageService {
    /**
     * imageContainsCat
     *
     * @param image
     * @param confidenceThreshold
     * @return
     */
    boolean imageContainsCat(BufferedImage image, float confidenceThreshold);
}
