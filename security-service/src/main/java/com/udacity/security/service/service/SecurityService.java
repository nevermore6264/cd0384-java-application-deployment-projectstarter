package com.udacity.security.service.service;

import com.udacity.image.service.ImageService;
import com.udacity.security.service.application.StatusListener;
import com.udacity.security.service.data.AlarmStatus;
import com.udacity.security.service.data.ArmingStatus;
import com.udacity.security.service.data.SecurityRepository;
import com.udacity.security.service.data.Sensor;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * Service that receives information about changes to the security system. Responsible for
 * forwarding updates to the repository and making any decisions about changing the system state.
 * <p>
 * This is the class that should contain most of the business logic for our system, and it is the
 * class you will be writing unit tests for.
 */
public class SecurityService {

    private ImageService imageService;
    private SecurityRepository securityRepository;
    private Set<StatusListener> statusListeners = new HashSet<>();

    public SecurityService(SecurityRepository securityRepository, ImageService imageService) {
        this.securityRepository = securityRepository;
        this.imageService = imageService;
    }

    public void setArmingStatus(ArmingStatus armingStatus) {
        if (armingStatus == ArmingStatus.DISARMED) {
            setAlarmStatus(AlarmStatus.NO_ALARM);
        } else if (armingStatus == ArmingStatus.ARMED_HOME) {
            if (securityRepository.isCatDetected()) {
                setAlarmStatus(AlarmStatus.ALARM);
            }
            securityRepository.resetAllSensors();
        } else {
            securityRepository.resetAllSensors();
        }
        securityRepository.setArmingStatus(armingStatus);

        statusListeners.forEach(StatusListener::sensorStatusChanged);
    }

    public void catDetected(Boolean cat) {
        if (cat) {
            securityRepository.catDetected(true);
            if (getArmingStatus() == ArmingStatus.ARMED_HOME) {
                setAlarmStatus(AlarmStatus.ALARM);
            }
        } else {
            securityRepository.catDetected(false);
            if (securityRepository.allSensorsInactive()) {
                setAlarmStatus(AlarmStatus.NO_ALARM);
            }
        }
        for (StatusListener sl : statusListeners) {
            sl.catDetected(cat);
        }
    }

    public void addStatusListener(StatusListener statusListener) {
        statusListeners.add(statusListener);
    }

    public void removeStatusListener(StatusListener statusListener) {
        statusListeners.remove(statusListener);
    }

    public void setAlarmStatus(AlarmStatus status) {
        securityRepository.setAlarmStatus(status);
        statusListeners.forEach(sl -> sl.notify(status));
    }

    public void handleSensorActivated() {
        if (getArmingStatus() == ArmingStatus.DISARMED) {
            return; //no problem if the system is disarmed
        }
        switch (getAlarmStatus()) {
            case NO_ALARM:
                setAlarmStatus(AlarmStatus.PENDING_ALARM);
                break;
            case PENDING_ALARM:
                setAlarmStatus(AlarmStatus.ALARM);
                break;
            default:
        }
    }

    public void handleSensorDeactivated() {
        if (getAlarmStatus() == AlarmStatus.PENDING_ALARM && securityRepository.allSensorsInactive()) {
            setAlarmStatus(AlarmStatus.NO_ALARM);
        }
    }

    public void changeSensorActivationStatus(Sensor sensor, Boolean active) {
        if (active) {
            sensor.setActive(true);
            handleSensorActivated();
        } else if (sensor.getActive()) {
            sensor.setActive(false);
            handleSensorDeactivated();
        }
        securityRepository.updateSensor(sensor);
    }

    public void processImage(BufferedImage currentCameraImage) {
        catDetected(imageService.imageContainsCat(currentCameraImage, 50.0f));
    }

    public AlarmStatus getAlarmStatus() {
        return securityRepository.getAlarmStatus();
    }

    public Set<Sensor> getSensors() {
        return securityRepository.getSensors();
    }

    public void addSensor(Sensor sensor) {
        securityRepository.addSensor(sensor);
    }

    public void removeSensor(Sensor sensor) {
        securityRepository.removeSensor(sensor);
    }

    public ArmingStatus getArmingStatus() {
        return securityRepository.getArmingStatus();
    }
}
