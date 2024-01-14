package com.udacity.image.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.Label;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AwsImageServiceTest {

    @Mock
    private RekognitionClient rekognitionClient;

    private AwsImageService imageService;
    private BufferedImage catImage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService = new AwsImageService();
        AwsImageService.setRekognitionClient(rekognitionClient);

        // Initialize test images (replace with actual image loading code)
        catImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    void testImageContainsCat() throws IOException {
        // Mock the detectLabels response
        DetectLabelsResponse detectLabelsResponse = DetectLabelsResponse.builder()
                .labels(Label.builder().name("Cat").confidence(0.9f).build())
                .build();
        when(rekognitionClient.detectLabels(any(DetectLabelsRequest.class))).thenReturn(detectLabelsResponse);

        // Convert the cat image to AWS Image object
        Image awsImage = null;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(catImage, "jpg", os);
            awsImage = Image.builder().bytes(SdkBytes.fromByteArray(os.toByteArray())).build();
        }

        // Call the method under test
        boolean result = imageService.imageContainsCat(catImage, 0.8f);

        // Verify the detectLabels method was called with the correct parameters
        // Define a custom matcher for the DetectLabelsRequest
        Image finalAwsImage = awsImage;
        ArgumentMatcher<DetectLabelsRequest> detectLabelsRequestMatcher = argument -> {
            Image requestImage = argument.image();
            Float requestMinConfidence = argument.minConfidence();

            return requestImage.equals(finalAwsImage) && requestMinConfidence.equals(0.8f);
        };

        // Verify the detectLabels method was called with the correct parameters
        verify(rekognitionClient).detectLabels(argThat(detectLabelsRequestMatcher));

        // Verify the expected result
        assertTrue(result, "Cat image should be recognized as containing a cat");
    }
}
