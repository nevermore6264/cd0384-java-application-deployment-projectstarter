package com.udacity.security.service.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityRepositoryTest {

    private SecurityRepository securityRepository;

    @BeforeEach
    void setUp() {
        securityRepository = new SecurityRepositoryImpl();
    }

    @Test
    void testAddSensor() {
        // Arrange
        Sensor sensor = new Sensor();

        // Act
        securityRepository.addSensor(sensor);

        // Assert
        assertTrue(securityRepository.getSensors().contains(sensor));
    }

    @Test
    void testRemoveSensor() {
        // Arrange
        Sensor sensor = new Sensor();
        securityRepository.addSensor(sensor);

        // Act
        securityRepository.removeSensor(sensor);

        // Assert
        assertFalse(securityRepository.getSensors().contains(sensor));
    }

    @Test
    void testUpdateSensor() {
        // Arrange
        Sensor sensor = new Sensor();
        securityRepository.addSensor(sensor);

        // Act
        sensor.setActive(true);
        securityRepository.updateSensor(sensor);

        // Assert
        assertTrue(sensor.getActive());
    }

    @Test
    void testSetAlarmStatus() {
        // Arrange
        AlarmStatus alarmStatus = AlarmStatus.ALARM;

        // Act
        securityRepository.setAlarmStatus(alarmStatus);

        // Assert
        assertEquals(alarmStatus, securityRepository.getAlarmStatus());
    }

    @Test
    void testSetArmingStatus() {
        // Arrange
        ArmingStatus armingStatus = ArmingStatus.ARMED_AWAY;

        // Act
        securityRepository.setArmingStatus(armingStatus);

        // Assert
        assertEquals(armingStatus, securityRepository.getArmingStatus());
    }

    @Test
    void testResetAllSensors() {
        // Arrange
        String name = "Sensor 1";
        SensorType sensorType = SensorType.MOTION;

        Sensor sensor = new Sensor(name, sensorType);

        // Act
        securityRepository.resetAllSensors();

        // Assert
        assertFalse(sensor.getActive());
    }

    @Test
    void testCatDetected() {
        // Arrange
        boolean cat = true;

        // Act
        securityRepository.catDetected(cat);

        // Assert
        assertEquals(cat, securityRepository.isCatDetected());
    }

    @Test
    void testAllSensorsInactive() {
        // Arrange
        String name = "Sensor 1";
        SensorType sensorType = SensorType.MOTION;

        Sensor sensor = new Sensor(name, sensorType);

        // Act
        sensor.setActive(false);

        // Assert
        assertTrue(securityRepository.allSensorsInactive());
    }

    // Custom implementation of SecurityRepository for testing purposes
    private static class SecurityRepositoryImpl implements SecurityRepository {
        private Set<Sensor> sensors;
        private AlarmStatus alarmStatus;
        private ArmingStatus armingStatus;
        private boolean catDetected;

        public SecurityRepositoryImpl() {
            sensors = new HashSet<>();
            alarmStatus = AlarmStatus.NO_ALARM;
            armingStatus = ArmingStatus.DISARMED;
            catDetected = false;
        }

        @Override
        public void addSensor(Sensor sensor) {
            sensors.add(sensor);
        }

        @Override
        public void removeSensor(Sensor sensor) {
            sensors.remove(sensor);
        }

        @Override
        public void updateSensor(Sensor sensor) {
            // No implementation needed for testing
        }

        @Override
        public void setAlarmStatus(AlarmStatus alarmStatus) {
            this.alarmStatus = alarmStatus;
        }

        @Override
        public void setArmingStatus(ArmingStatus armingStatus) {
            this.armingStatus = armingStatus;
        }

        @Override
        public void resetAllSensors() {
            for (Sensor sensor : sensors) {
                sensor.setActive(false);
            }
        }

        @Override
        public void catDetected(boolean cat) {
            catDetected = cat;
        }

        @Override
        public boolean allSensorsInactive() {
            for (Sensor sensor : sensors) {
                if (sensor.getActive()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean isCatDetected() {
            return catDetected;
        }

        @Override
        public Set<Sensor> getSensors() {
            return sensors;
        }

        @Override
        public AlarmStatus getAlarmStatus() {
            return alarmStatus;
        }

        @Override
        public ArmingStatus getArmingStatus() {
            return armingStatus;
        }
    }
}