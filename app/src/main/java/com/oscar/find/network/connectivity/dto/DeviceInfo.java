package com.oscar.find.network.connectivity.dto;

import java.io.Serializable;

/**
 * Clase VO que aloja información del dispositivo del usuario
 * Created by oscar on 30/09/16.
 */
public class DeviceInfo implements Serializable {

    /** Nombre del operador */
    private String operatorName = null;
    /** Id del dispositivo */
    private String idDevice     = null;
    /** Nombre del operador de la sim */
    private String simOperatorName = null;

    /** Nombre del modelo del dispositivo */
    private String model = null;
    /** Número de serie del dispositivo */
    private String serialNumber = null;
    /** Procesador */
    private String processor   = null;
    /** Fabricante **/
    private String manufacturer = null;
    /** Versión del software **/
    private String softwareVersion = null;
    /** Número del api de android **/
    private Integer apiNumber = null;
    /** Nombre del producto **/
    private String product = null;


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

    /**
     * Devuelve el nombre del producto
     * @return String
     */
    public String getProduct() {
        return product;
    }

    /**
     * Establece el nombre del producto
     * @param product String
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Devuelve el número del api de android
     * @return Integer
     */
    public Integer getApiNumber() {
        return apiNumber;
    }

    /**
     * Establece el número del api de android
     * @param apiNumber Integer
     */
    public void setApiNumber(Integer apiNumber) {
        this.apiNumber = apiNumber;
    }


    /**
     * Devuelve el nombre del fabricante del dispositivo
     * @return String
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Establece el nombre del fabricante del dispositivo
     * @param manufacturer String
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Devuelve el nombre del procesador
     * @return String
     */
    public String getProcessor() {
        return processor;
    }


    /**
     * Establece el nombre del procesador
     * @param processor String
     */
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /**
     * Devuelve el número de serie del dispositivo
     * @return String
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Devuelve el número de serie del dispositivo
     * @param serialNumber String
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    /**
     * Devuelve el nombre del modelo del dispositivo
     * @return String
     */
    public String getModel() {
        return model;
    }

    /**
     * Establece el nombre del modelo del dispositivo
     * @param model String
     */
    public void setModel(String model) {
        this.model = model;
    }
}
