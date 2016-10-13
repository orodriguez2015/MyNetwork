package com.oscar.find.network.connectivity.dto;

import java.io.Serializable;

/**
 * Clase ParamsAsyncTask
 * Created by oscar on 10/10/16.
 */
public class ParamsAsyncTask implements Serializable {

    private NetworkHostData networkHostData = null;

    /**
     * Devuelve los datos de la red para obtener los diferentes dispositivos
     * conectados a la red wifi
     * @return NetworkHostData
     */
    public NetworkHostData getNetworkHostData() {
        return networkHostData;
    }


    /**
     * Establece los datos de la red para obtener los diferentes dispositivos
     * conectados a la red wifi
     * @param networkHostData NetworkHostData
     */
    public void setNetworkHostData(NetworkHostData networkHostData) {
        this.networkHostData = networkHostData;
    }
}
