package com.oscar.find.network.util;

import java.io.File;

/**
 * Clase RootUtil con operaciones necesarias
 * Created by oscar on 22/10/16.
 */
public class RootUtil {

    private static final String SU_COMMAND = "su";

    /**
     * Encuentra un determinado binario en el sistema de archivos. Se comprueba simplemente
     * si existe el archivo
     * @param binaryName String
     * @return True si existe y false en caso contrario
     */
    public static boolean findBinary(String binaryName) {
        boolean found = false;
        if (!found) {
            String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
                    "/data/local/xbin/", "/data/local/bin/",
                    "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
            for (String where : places) {
                if (new File(where + binaryName).exists()) {
                    found = true;

                    break;
                }
            }
        }
        return found;
    }


    /**
     * Comprueba si el dispositivo está rooteado
     * @return boolean
     */
    public static boolean isDeviceRooted() {
        return findBinary(SU_COMMAND);
    }
}
