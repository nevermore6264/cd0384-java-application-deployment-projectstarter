package com.udacity.security.service.service;

import com.udacity.image.service.ImageService;
import com.udacity.security.service.application.StatusListener;
import com.udacity.security.service.data.AlarmStatus;
import com.udacity.security.service.data.ArmingStatus;
import com.udacity.security.service.data.SecurityRepository;
import com.udacity.security.service.data.Sensor;
import com.udacity.security.service.data.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SecurityServiceTest {

    private SecurityService securityService;

    @Mock
    private ImageService imageService;

    @Mock
    private SecurityRepository securityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityService = new SecurityService(securityRepository, imageService);
    }

    @Test
    void testSetArmingStatus_Disarmed() {
        // Act
        securityService.setArmingStatus(ArmingStatus.DISARMED);

        // Assert
        verify(securityRepository).setArmingStatus(ArmingStatus.DISARMED);
        verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
        verify(securityRepository, never()).isCatDetected();
        verify(securityRepository, never()).resetAllSensors();
    }

    @Test
    void testSetArmingStatus_ArmedHome_WithCatDetected() {
        // Arrange
        when(securityRepository.isCatDetected()).thenReturn(true);

        // Act
        securityService.setArmingStatus(ArmingStatus.ARMED_HOME);

        // Assert
        verify(securityRepository).setArmingStatus(ArmingStatus.ARMED_HOME);
        verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
        verify(securityRepository).resetAllSensors();
        verify(securityRepository).isCatDetected();
    }

    @Test
    void testSetArmingStatus_ArmedHome_WithoutCatDetected() {
        // Arrange
        when(securityRepository.isCatDetected()).thenReturn(false);

        // Act
        securityService.setArmingStatus(ArmingStatus.ARMED_HOME);

        // Assert
        verify(securityRepository).setArmingStatus(ArmingStatus.ARMED_HOME);
        verify(securityRepository).resetAllSensors();
        verify(securityRepository).isCatDetected();
    }

    @Test
    void testSetArmingStatus_Other() {
        // Act
        securityService.setArmingStatus(ArmingStatus.ARMED_AWAY);

        // Assert
        verify(securityRepository).setArmingStatus(ArmingStatus.ARMED_AWAY);
        verify(securityRepository).resetAllSensors();
    }

    @Test
    void testCatDetected_WithArmedHome() {
        // Arrange
        when(securityRepository.allSensorsInactive()).thenReturn(false);
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);

        // Act
        securityService.catDetected(true);

        // Assert
        verify(securityRepository).catDetected(true);
        verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
        verify(securityRepository).getArmingStatus();
    }

    @Test
    void testCatDetected_WithArmedHome_AllSensorsInactive() {
        // Arrange
        when(securityRepository.allSensorsInactive()).thenReturn(true);
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);

        // Act
        securityService.catDetected(true);

        // Assert
        verify(securityRepository).catDetected(true);
        verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
        verify(securityRepository).getArmingStatus();
    }

    @Test
    void testCatDetected_WithoutArmedHome() {
        // Arrange
        when(securityRepository.allSensorsInactive()).thenReturn(true);

        // Act
        securityService.catDetected(false);

        // Assert
        verify(securityRepository).catDetected(false);
        verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    @Test
    void testProcessImage_CatDetected() {
        // Arrange
        BufferedImage image = mock(BufferedImage.class);
        when(imageService.imageContainsCat(image, 50.0f)).thenReturn(true);

        // Act
        securityService.processImage(image);

        // Assert
        verify(imageService).imageContainsCat(image, 50.0f);
    }

    @Test
    void testProcessImage_NoCatDetected() {
        // Arrange
        BufferedImage image = mock(BufferedImage.class);
        when(imageService.imageContainsCat(image, 50.0f)).thenReturn(false);

        // Act
        securityService.processImage(image);

        // Assert
        verify(imageService).imageContainsCat(image, 50.0f);
    }

    @Test
    void testGetAlarmStatus() {
        // Arrange
        AlarmStatus expectedStatus = AlarmStatus.ALARM;
        when(securityRepository.getAlarmStatus()).thenReturn(expectedStatus);

        // Act
        AlarmStatus actualStatus = securityService.getAlarmStatus();

        // Assert
        assertEquals(expectedStatus, actualStatus);
        verify(securityRepository).getAlarmStatus();
    }

    @Test
    void testGetSensors() {
        // Arrange
        Set<Sensor> expectedSensors = new HashSet<>();
        when(securityRepository.getSensors()).thenReturn(expectedSensors);

        // Act
        Set<Sensor> actualSensors = securityService.getSensors();

        // Assert
        assertEquals(expectedSensors, actualSensors);
        verify(securityRepository).getSensors();
    }

    @Test
    void testAddSensor() {
        // Arrange
        Sensor sensor = new Sensor("Sensor 1", SensorType.DOOR);

        // Act
        securityService.addSensor(sensor);

        // Assert
        verify(securityRepository).addSensor(sensor);
    }

    @Test
    void testRemoveSensor() {
        // Arrange
        Sensor sensor = new Sensor("Sensor 1", SensorType.DOOR);

        // Act
        securityService.removeSensor(sensor);

        // Assert
        verify(securityRepository).removeSensor(sensor);
    }

    @Test
    void testAddStatusListener() {
        // Arrange
        StatusListener statusListener = mock(StatusListener.class);
        Set<StatusListener> statusListeners = new HashSet<>();
        statusListeners.add(statusListener);

        // Act
        securityService.addStatusListener(statusListener);

        // Assert
        assertContains(statusListeners, statusListener);
    }

    @Test
    void testRemoveStatusListener() {
        // Arrange
        StatusListener statusListener = mock(StatusListener.class);
        Set<StatusListener> statusListeners = new HashSet<>();

        // Act
        securityService.removeStatusListener(statusListener);

        // Assert
        assertNotContains(statusListeners, statusListener);
    }

    private <T> void assertContains(Set<T> set, T element) {
        assertTrue(set.contains(element), "Expected set to contain element, but it did not.");
    }

    private <T> void assertNotContains(Set<T> set, T element) {
        assertFalse(set.contains(element), "Expected set to not contain element, but it did.");
    }

}

