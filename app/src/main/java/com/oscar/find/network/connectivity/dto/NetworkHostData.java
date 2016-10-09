package com.oscar.find.network.connectivity.dto;

import java.io.Serializable;

/**
 * Clase NetworkHostData
 * Created by oscar on 09/10/16.
 */
public class NetworkHostData implements Serializable {

    private long network_ip    = 0;
    private long network_start = 0;
    private long network_end   = 0;

    /**
     * Devuelve dirección ip
     * @return long
     */
    public long getNetwork_ip() {
        return network_ip;
    }

    /**
     * Establece dirección ip
     * @param network_ip long
     */
    public void setNetwork_ip(long network_ip) {
        this.network_ip = network_ip;
    }


    /**
     * Devuelve dirección ip de inicio
     * @return long
     */
    public long getNetwork_start() {
        return network_start;
    }

    /**
     * Establece la dirección ip de inicio
     * @param network_start long
     */
    public void setNetwork_start(long network_start) {
        this.network_start = network_start;
    }

    /**
     * Devuelve la dirección ip de fin
     * @return long
     */
    public long getNetwork_end() {
        return network_end;
    }

    /**
     * Permite establecer la dirección ip de fin
     * @return long
     */
    public void setNetwork_end(long network_end) {
        this.network_end = network_end;
    }

}