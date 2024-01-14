package com.udacity.security.service.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTypeTest {

    @Test
    void testSensorTypes() {
        // Assert
        assertEquals("DOOR", SensorType.DOOR.toString());
        assertEquals("WINDOW", SensorType.WINDOW.toString());
        assertEquals("MOTION", SensorType.MOTION.toString());
    }
}