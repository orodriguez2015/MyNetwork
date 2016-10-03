package com.oscar.find.network.connectivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.oscar.find.network.connectivity.dto.DeviceInfo;
import com.oscar.find.network.connectivity.dto.WifiInfo;
import com.oscar.find.network.util.LogCat;

/**
 * Created by oscar on 29/09/16.
 */

public class NetInfoDevice {

    /**
     * Comprueba si el dispositivo tiene habilitado alguna conexión de red, sean datos
     * móviles, wifi, ...
     * @param application: Activity desde la que se hace la comprobación
     * @return True si hay conexión y false en caso contrario
     */
    public static boolean hasDeviceConnectionsNetworkEnabled(Activity application){
        boolean exito = false;

        try {
            ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                exito = true;
            }

        }catch(Exception e) {
            exito = false;
            e.printStackTrace();
        }
        return exito;
    }


    /**
     * Devuelve información sobre el dispositivo como el nombre del operador, id del dispositivo
     * @param ctxt Context
     * @return DeviceInfo
     */
    public static DeviceInfo getMobileInfo(Context ctxt) {

        DeviceInfo info = null;

        try {
            TelephonyManager tm = (TelephonyManager) ctxt.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {

                info = new DeviceInfo(tm.getNetworkOperatorName(),tm.getDeviceId(),tm.getSimOperatorName(),tm.getDeviceSoftwareVersion());
                LogCat.debug("operatorName: " + tm.getNetworkOperatorName());

                info.setManufacturer(Build.MANUFACTURER);
                info.setModel(Build.MODEL);
                info.setSoftwareVersion(Build.VERSION.RELEASE);
                info.setSerialNumber(Build.SERIAL);
                info.setApiNumber(Build.VERSION.SDK_INT);
                info.setProcessor(Build.HARDWARE);
                info.setApiNumber(Build.VERSION.SDK_INT);

                LogCat.debug("Build.device: " + Build.DEVICE);
                LogCat.debug("Build.display: " + Build.DISPLAY);
                LogCat.debug("Build.hardware: " + Build.HARDWARE);
                LogCat.debug("Build.fingerprint: " + Build.FINGERPRINT);
                LogCat.debug("Build.host: " + Build.HOST);
                LogCat.debug("Build.id: " + Build.ID);
                LogCat.debug("Build.MANUFACTURER: " + Build.MANUFACTURER);
                LogCat.debug("Build.model: " + Build.MODEL);
                LogCat.debug("Build.product: " + Build.PRODUCT);
                LogCat.debug("Build.SERIAL: " + Build.SERIAL);
                LogCat.debug("Build.user: " + Build.USER);
                LogCat.debug("Build.type: " + Build.TYPE);
                LogCat.debug("Build.BRAND: " + Build.BRAND);
                LogCat.debug("Build.getRadioVersion: " + Build.getRadioVersion());


                LogCat.debug("Build.VERSION.BASE_OS: " + Build.VERSION.BASE_OS);
                LogCat.debug("Build.VERSION.CODENAME: " + Build.VERSION.CODENAME);
                LogCat.debug("Build.VERSION.RELEASE: " + Build.VERSION.RELEASE);
                LogCat.debug("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT);



                LogCat.debug("simOperatorName: " + tm.getSimOperatorName());
                LogCat.debug("deviceId: " + tm.getDeviceId());
                LogCat.debug("networkOperator: " + tm.getNetworkOperator());
                LogCat.debug("networkCountryIso: " + tm.getNetworkCountryIso());

                //LogCat.debug("getDeviceSoftwareVersion: " + tm.getNeighboringCellInfo().get(0).);
                LogCat.debug("getDeviceSoftwareVersion: " + tm.getDeviceSoftwareVersion());
                LogCat.debug("voiceMailNumber: " + tm.getVoiceMailNumber());
                LogCat.debug("networkType: " + tm.getNetworkType());
                LogCat.debug("simState: " + tm.getSimState());
                LogCat.debug("subscribeID: " + tm.getSubscriberId());


                LogCat.debug("tmManager: " + tm);

            }

        }catch(Exception e) {

        }

        return info;
    }


    /**
     * Convierte una dirección IP en formato int en una dirección IP en una InetAddress
     * @param ip_int int
     * @return String
     */
    public static String getIpFromIntSigned(int ip_int) {
        String ip = "";
        for (int k = 0; k < 4; k++) {
            ip = ip + ((ip_int >> k * 8) & 0xFF) + ".";
        }
        return ip.substring(0, ip.length() - 1);
    }



    /**
     * Devuelve información de la conexión WIFI del dispositivo
     * @param context Context
     * @return WifiInfo
     */
    public static WifiInfo getWifiInfo(Context context) {
        WifiInfo wifiInfo = new WifiInfo();
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        boolean wifiEnabled =  wifiManager.isWifiEnabled();
        wifiInfo.setWifiEnabled(wifiEnabled);

        if(wifiEnabled) {
            LogCat.info("La conexión wifi está habilitada");
            wifiInfo.setBSSID(wifiManager.getConnectionInfo().getBSSID());
            wifiInfo.setDns1(getIpFromIntSigned(wifiManager.getDhcpInfo().dns1));
            wifiInfo.setDns2(getIpFromIntSigned(wifiManager.getDhcpInfo().dns2));
            wifiInfo.setGateway(getIpFromIntSigned(wifiManager.getDhcpInfo().gateway));
            wifiInfo.setIdNetwork(wifiManager.getConnectionInfo().getNetworkId());
            wifiInfo.setIpAddress(getIpFromIntSigned(wifiManager.getDhcpInfo().ipAddress));
            wifiInfo.setIpAddressDhcpServer(getIpFromIntSigned(wifiManager.getDhcpInfo().serverAddress));
            wifiInfo.setMacAddress(wifiManager.getConnectionInfo().getMacAddress());
            wifiInfo.setNetmask(getIpFromIntSigned(wifiManager.getDhcpInfo().netmask));
            wifiInfo.setLinkSpeed(wifiManager.getConnectionInfo().getLinkSpeed());
            wifiInfo.setSSID(wifiManager.getConnectionInfo().getSSID());
            wifiInfo.STATUS = 0;

        } else {
            LogCat.info("La conexión wifi está deshabilitada");
            wifiInfo.STATUS = -1;
        }

        return wifiInfo;
    }





}
