package com.udacity.security.service.data;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlarmStatusTest {

    @Test
    void testNoAlarm() {
        // Arrange
        AlarmStatus alarmStatus = AlarmStatus.NO_ALARM;

        // Act
        String description = alarmStatus.getDescription();
        Color color = alarmStatus.getColor();

        // Assert
        assertEquals("Cool and Good", description);
        assertEquals(new Color(120, 200, 30), color);
    }

    @Test
    void testPendingAlarm() {
        // Arrange
        AlarmStatus alarmStatus = AlarmStatus.PENDING_ALARM;

        // Act
        String description = alarmStatus.getDescription();
        Color color = alarmStatus.getColor();

        // Assert
        assertEquals("I'm in Danger...", description);
        assertEquals(new Color(200, 150, 20), color);
    }

    @Test
    void testAlarm() {
        // Arrange
        AlarmStatus alarmStatus = AlarmStatus.ALARM;

        // Act
        String description = alarmStatus.getDescription();
        Color color = alarmStatus.getColor();

        // Assert
        assertEquals("Awooga!", description);
        assertEquals(new Color(250, 80, 50), color);
    }
}