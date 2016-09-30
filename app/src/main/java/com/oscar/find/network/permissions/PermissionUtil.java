package com.oscar.find.network.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 *
 * Clase con operaciones que permite comprobar si la aplicación tiene un determinado permiso sobre
 * el dispositivo
 * Created by oscar on 29/09/16.
 */
public class PermissionUtil {

    /**
     * Operación que comprueba si una actividad tiene acceso a un determinado permiso.
     * Los permisos se encuentra definidos en Manifest.permission.*
     * @param context Contexto de la aplicación
     * @param permiso Permiso a comprobar
     */
    public static boolean hasPermission(Context context, String permiso) {
        boolean exito = false;

        try {
            int permissionCheck = ContextCompat.checkSelfPermission(context,permiso);
            if(permissionCheck== PackageManager.PERMISSION_GRANTED) {
                exito = true;
            }
        }catch(Exception e) {
            e.printStackTrace();
            exito = false;
        }

        return exito;
    }


    /**
     * Operación que comprueba si la app tiene acceso al estado de red del dispositivo
     * @param context Context
     * @return boolean
     */
    public static boolean appHasAccessNetworkState(Context context) {
        return hasPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
    }


    /**
     * Operación que comprueba si la app tiene acceso al estado de la wifi
     * @param context Context
     * @return boolean
     */
    public static boolean appHasAccessWifiState(Context context) {
        return hasPermission(context, Manifest.permission.ACCESS_WIFI_STATE);
    }
}



