package com.udacity.security.service.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PretendDatabaseSecurityRepositoryImplTest {

    private PretendDatabaseSecurityRepositoryImpl securityRepository;

    @BeforeEach
    void setUp() {
        securityRepository = new PretendDatabaseSecurityRepositoryImpl();
    }

    @Test
    void testAddSensor() {
        // Arrange
        Sensor sensor = new Sensor("Sensor 1", SensorType.DOOR);

        // Act
        securityRepository.addSensor(sensor);
        Set<Sensor> sensors = securityRepository.getSensors();

        // Assert
        assertTrue(sensors.contains(sensor));
    }

    @Test
    void testRemoveSensor() {
        // Arrange
        Sensor sensor = new Sensor("Sensor 1", SensorType.DOOR);
        securityRepository.addSensor(sensor);

        // Act
        securityRepository.removeSensor(sensor);
        Set<Sensor> sensors = securityRepository.getSensors();

        // Assert
        assertFalse(sensors.contains(sensor));
    }

    @Test
    void testUpdateSensor() {
        // Arrange
        Sensor sensor = new Sensor("Sensor 1", SensorType.DOOR);
        securityRepository.addSensor(sensor);

        // Act
        sensor.setName("Updated Sensor");
        securityRepository.updateSensor(sensor);
        Set<Sensor> sensors = securityRepository.getSensors();

        // Assert
        assertTrue(sensors.contains(sensor));
    }

    @Test
    void testSetAlarmStatus() {
        // Arrange
        AlarmStatus alarmStatus = AlarmStatus.ALARM;

        // Act
        securityRepository.setAlarmStatus(alarmStatus);
        AlarmStatus retrievedAlarmStatus = securityRepository.getAlarmStatus();

        // Assert
        assertEquals(alarmStatus, retrievedAlarmStatus);
    }

    @Test
    void testSetArmingStatus() {
        // Arrange
        ArmingStatus armingStatus = ArmingStatus.ARMED_AWAY;

        // Act
        securityRepository.setArmingStatus(armingStatus);
        ArmingStatus retrievedArmingStatus = securityRepository.getArmingStatus();

        // Assert
        assertEquals(armingStatus, retrievedArmingStatus);
    }

    @Test
    void testResetAllSensors() {
        // Arrange
        Sensor sensor1 = new Sensor("Sensor 1", SensorType.DOOR);
        Sensor sensor2 = new Sensor("Sensor 2", SensorType.WINDOW);
        securityRepository.addSensor(sensor1);
        securityRepository.addSensor(sensor2);

        // Act
        sensor1.setActive(true);
        sensor2.setActive(true);
        securityRepository.resetAllSensors();
        Set<Sensor> sensors = securityRepository.getSensors();

        // Assert
        assertFalse(sensor1.getActive());
        assertFalse(sensor2.getActive());
    }

    @Test
    void testCatDetected() {
        // Arrange
        boolean catDetected = true;

        // Act
        securityRepository.catDetected(catDetected);
        boolean retrievedCatDetected = securityRepository.isCatDetected();

        // Assert
        assertEquals(catDetected, retrievedCatDetected);
    }

    @Test
    void testAllSensorsInactive() {
        // Arrange
        Sensor sensor1 = new Sensor("Sensor 1", SensorType.DOOR);
        Sensor sensor2 = new Sensor("Sensor 2", SensorType.WINDOW);
        securityRepository.addSensor(sensor1);
        securityRepository.addSensor(sensor2);

        // Act
        boolean allSensorsInactive = securityRepository.allSensorsInactive();

        // Assert
        assertTrue(allSensorsInactive);
    }
}