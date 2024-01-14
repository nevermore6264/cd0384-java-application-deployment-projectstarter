package com.udacity.security.service.data;

import java.util.Set;

/**
 * Interface showing the methods our security repository will need to support
 */
public interface SecurityRepository {
    void addSensor(Sensor sensor);

    void removeSensor(Sensor sensor);

    void updateSensor(Sensor sensor);

    void setAlarmStatus(AlarmStatus alarmStatus);

    void setArmingStatus(ArmingStatus armingStatus);

    void resetAllSensors();

    void catDetected(boolean cat);

    boolean allSensorsInactive();

    boolean isCatDetected();

    Set<Sensor> getSensors();

    AlarmStatus getAlarmStatus();

    ArmingStatus getArmingStatus();
}
