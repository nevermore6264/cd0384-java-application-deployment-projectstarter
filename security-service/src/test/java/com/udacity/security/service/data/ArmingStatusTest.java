package com.udacity.security.service.data;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArmingStatusTest {

    @Test
    void testDisarmed() {
        // Arrange
        ArmingStatus armingStatus = ArmingStatus.DISARMED;

        // Act
        String description = armingStatus.getDescription();
        Color color = armingStatus.getColor();

        // Assert
        assertEquals("Disarmed", description);
        assertEquals(new Color(120, 200, 30), color);
    }

    @Test
    void testArmedHome() {
        // Arrange
        ArmingStatus armingStatus = ArmingStatus.ARMED_HOME;

        // Act
        String description = armingStatus.getDescription();
        Color color = armingStatus.getColor();

        // Assert
        assertEquals("Armed - At Home", description);
        assertEquals(new Color(190, 180, 50), color);
    }

    @Test
    void testArmedAway() {
        // Arrange
        ArmingStatus armingStatus = ArmingStatus.ARMED_AWAY;

        // Act
        String description = armingStatus.getDescription();
        Color color = armingStatus.getColor();

        // Assert
        assertEquals("Armed - Away", description);
        assertEquals(new Color(170, 30, 150), color);
    }
}