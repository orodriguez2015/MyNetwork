package com.oscar.find.network.connectivity.dto;

import java.io.Serializable;

/**
 * Clase WifiInfo que contiene información de la conexión WIFI del dispositivo
 * Created by oscar on 30/09/16.
 */
public class WifiInfo implements Serializable {

    public static int STATUS = -1;
    private String macAddress    = null;
    private String SSID          = null;
    private int linkSpeed;
    private int idNetwork;
    private String BSSID         = null;
    private String ipAddress     = null;
    private String gateway       = null;
    private String netmask       = null;
    private String dns1          = null;
    private String dns2          = null;
    private String ipAddressDhcpServer = null;
    private boolean wifiEnabled  = false;


    /**
     * Constructor
     */
    public WifiInfo() {

    }


    /**
     * Constructor
     * @param macAddress Dirección map
     * @param SSID SSID de la red WIFI
     * @param linkSpeed Velocidad de la red WIFI
     * @param idNetwork ID de la red
     * @param BSSID BSSID de la red
     * @param ipAddress Dirección ip del dispositivo en la red WIFI
     * @param gateway Dirección IP de la puerta de enlace
     * @param netmask Máscara de red
     * @param dns1 Dirección IP del DNS primario
     * @param dns2 Dirección IP del DNS secundario
     * @param ipAddressDhcpServer Dirección IP del servidor de DHCP
     */
    public WifiInfo(String macAddress, String SSID, int linkSpeed, int idNetwork, String BSSID, String ipAddress, String gateway, String netmask, String dns1, String dns2, String ipAddressDhcpServer, boolean wifiEnabled) {
        this.macAddress = macAddress;
        this.SSID = SSID;
        this.linkSpeed = linkSpeed;
        this.idNetwork = idNetwork;
        this.BSSID = BSSID;
        this.ipAddress = ipAddress;
        this.gateway = gateway;
        this.netmask = netmask;
        this.dns1 = dns1;
        this.dns2 = dns2;
        this.ipAddressDhcpServer = ipAddressDhcpServer;
        this.wifiEnabled = wifiEnabled;
    }

    /**
     * Devuelve la dirección ip de la máscara de red
     * @return String
     */
    public String getNetmask() {
        return netmask;
    }

    /**
     * Establece la dirección ip de la máscara de red
     * @param netmask String
     */
    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    /**
     * Devuelve la dirección IP del servidor de DNS primario
     * @return String
     */
    public String getDns1() {
        return dns1;
    }

    /**
     * Establece la dirección IP del servidor de DNS primario
     * @param dns1 String
     */
    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    /**
     * Devuelve la dirección IP del servidor de DNS secundario
     * @return String
     */
    public String getDns2() {
        return dns2;
    }

    /**
     * Establece la dirección IP del servidor de DNS secundario
     * @return String
     */
    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    /**
     * Devuelve la dirección IP del servidor de DHCP
     * @return String
     */
    public String getIpAddressDhcpServer() {
        return ipAddressDhcpServer;
    }

    /**
     * Devuelve la dirección IP del servidor de DHCP
     * @param ipAddressDhcpServer String
     */
    public void setIpAddressDhcpServer(String ipAddressDhcpServer) {
        this.ipAddressDhcpServer = ipAddressDhcpServer;
    }

    /**
     * Devuelve la dirección mac de la tarjeta wifi
     * @return String
     */
    public String getMacAddress() {
        return macAddress;
    }


    /**
     * Establece la dirección mac de la tarjeta wifi
     * @param macAddress String
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Devuelve el SSID de la red a la que está conectada el dispositivo vía WIFI
     * @return String
     */
    public String getSSID() {
        return SSID;
    }


    /**
     * Establece  el SSID de la red a la que está conectada el dispositivo vía WIFI
     * @param SSID String
     */
    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    /**
     * Devuelve la velocidad de la red WIFI a la que está conectada el dispositivo
     * @return int
     */
    public int getLinkSpeed() {
        return linkSpeed;
    }

    /**
     * Establece la velocidad de la red WIFI a la que está conectada el dispositivo
     * @param linkSpeed int
     */
    public void setLinkSpeed(int linkSpeed) {
        this.linkSpeed = linkSpeed;
    }


    /**
     * Devuelve el id de la red
     * @return int
     */
    public int getIdNetwork() {
        return idNetwork;
    }

    /**
     * Establece el id de la red
     * @param idNetwork int
     */
    public void setIdNetwork(int idNetwork) {
        this.idNetwork = idNetwork;
    }

    /**
     * Devuelve el BSSID de la red
     * @return String
     */
    public String getBSSID() {
        return BSSID;
    }

    /**
     * Establece el BSSID de la red
     * @param BSSID String
     */
    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    /**
     * Devuelve la dirección IP que tiene asignada el dispositivo vía WIFI
     * @return String
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Establece la dirección IP que tiene asignada el dispositivo vía WIFI
     * @param ipAddress String
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Devuelve la dirección ip de la puerta de enlace de la red WIFI
     * @return String
     */
    public String getGateway() {
        return gateway;
    }

    /**
     * Establece la dirección ip de la puerta de enlace de la red WIFI
     * @param gateway String
     */
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    /**
     * Devuelve true si la conexión wifi está habilitada y false en caso contrario
     * @return boolean
     */
    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    /**
     * Establece si la conexión wifi está habilitada o no
     * @param wifiEnabled boolean
     */
    public void setWifiEnabled(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }


    @Override
    public String toString() {
        return "WifiInfo{" +
                "macAddress='" + macAddress + '\'' +
                ", SSID='" + SSID + '\'' +
                ", linkSpeed=" + linkSpeed +
                ", idNetwork=" + idNetwork +
                ", BSSID='" + BSSID + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", gateway='" + gateway + '\'' +
                ", netmask='" + netmask + '\'' +
                ", dns1='" + dns1 + '\'' +
                ", dns2='" + dns2 + '\'' +
                ", ipAddressDhcpServer='" + ipAddressDhcpServer + '\'' +
                '}';
    }

}
