package com.udacity.image.service;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FakeImageServiceTest
 *
 * @author HieuTT121
 * @since 15/01/2024
 */
public class FakeImageServiceTest {

    /**
     * Test when image contains cat with confidence greater than or equal to 0.5
     */
    @Test
    public void testImageContainsCat_case1() {
        FakeImageService fakeImageService = new FakeImageService();

        BufferedImage highConfidenceCatImage = createCatImageWithConfidence(0.8f);
        assertTrue(fakeImageService.imageContainsCat(highConfidenceCatImage, 0.8f));
    }
    /**
     * createCatImageWithConfidence
     *
     * @param confidence
     * @return
     */
    private BufferedImage createCatImageWithConfidence(float confidence) {
        // Implement this method to create a BufferedImage with the specified confidence
        // For simplicity, you can use a library like BufferedImage or create a simple mock
        // In a real scenario, you might want to load an actual cat image
        return new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    }
}
