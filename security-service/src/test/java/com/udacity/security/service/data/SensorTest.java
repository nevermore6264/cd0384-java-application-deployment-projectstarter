package com.udacity.security.service.data;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SensorTest {

    @Test
    void testSensorConstructor() {
        // Arrange
        String name = "Sensor 1";
        SensorType sensorType = SensorType.MOTION;

        // Act
        Sensor sensor = new Sensor(name, sensorType);

        // Assert
        assertNotNull(sensor.getSensorId());
        assertEquals(name, sensor.getName());
        assertFalse(sensor.getActive());
        assertEquals(sensorType, sensor.getSensorType());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UUID sensorId1 = UUID.randomUUID();
        UUID sensorId2 = UUID.randomUUID();

        Sensor sensor1 = new Sensor();
        sensor1.setSensorId(sensorId1);

        Sensor sensor2 = new Sensor();
        sensor2.setSensorId(sensorId1);

        Sensor sensor3 = new Sensor();
        sensor3.setSensorId(sensorId2);

        // Assert
        assertEquals(sensor1, sensor2);
        assertNotEquals(sensor1, sensor3);
        assertEquals(sensor1.hashCode(), sensor2.hashCode());
        assertNotEquals(sensor1.hashCode(), sensor3.hashCode());
    }

    @Test
    void testCompareTo() {
        // Arrange
        Sensor sensor1 = new Sensor("Sensor A", SensorType.MOTION);
        Sensor sensor2 = new Sensor("Sensor B", SensorType.DOOR);
        Sensor sensor3 = new Sensor("Sensor A", SensorType.MOTION);

        // Assert
        assertTrue(sensor1.compareTo(sensor2) < 0);
        assertTrue(sensor2.compareTo(sensor1) > 0);
    }
}