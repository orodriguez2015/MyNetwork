package com.oscar.find.network.connectivity.dto;

import java.io.Serializable;

/**
 * Clase VO que aloja información del dispositivo del usuario
 * Created by oscar on 30/09/16.
 */
public class DeviceInfo implements Serializable {

    private String operatorName = null;
    private String idDevice     = null;
    private String simOperatorName = null;
    private String softwareVersion = null;

    /**
     * Constructor
     */
    public DeviceInfo() {

    }

    /**
     * Constructor
     * @param operatorName Nombre del operador
     * @param idDevice Id del dispositivo
     * @param simOperatorName Nombre del operador de la sim
     * @param softwareVersion Versión del software del dispositivo
     */
    public DeviceInfo(String operatorName, String idDevice, String simOperatorName, String softwareVersion) {
        this.operatorName = operatorName;
        this.idDevice = idDevice;
        this.simOperatorName = simOperatorName;
        this.softwareVersion = softwareVersion;
    }

    /**
     * Devuelve el nombre del operador de la sim
     * @return String
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * Devuelve el nombre del operador de la sim
     * @return String
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * Devuelve el id del dispositivo
     * @return String
     */
    public String getIdDevice() {
        return idDevice;
    }

    /**
     * Establece el id del dispositivo
     * @param idDevice String
     */
    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    /**
     * Devuelve el nombre del operador al que pertenece la sim
     * @return String
     */
    public String getSimOperatorName() {
        return simOperatorName;
    }

    /**
     * Devuelve el nombre del operador al que pertenece la sim
     * @param simOperatorName String
     */
    public void setSimOperatorName(String simOperatorName) {
        this.simOperatorName = simOperatorName;
    }

    /**
     * Devuelve la versión de software del dispositivo
     * @return String
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * Permite establecer la versión de software del dispositivo
     * @param softwareVersion String
     */
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }
}
