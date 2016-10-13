package com.oscar.find.network.connectivity.dto;

import java.io.Serializable;

/**
 * Clase Host
 * Created by oscar on 11/10/16.
 */
public class Host implements Serializable {

    private String ipAddress  = null;
    private String macAddress = null;


    /**
     * Devuelve la dirección mac de la tarjeta de red
     * @return String
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Establece la dirección mac de la tarjeta de red
     * @param macAddress String
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Devuelve la dirección ip
     * @return String
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Establece la dirección ip
     * @param ipAddress String
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }



}
