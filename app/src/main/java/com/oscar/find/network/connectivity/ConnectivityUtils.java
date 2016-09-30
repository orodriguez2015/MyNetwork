package com.oscar.find.network.connectivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.oscar.find.network.connectivity.dto.DeviceInfo;
import com.oscar.find.network.connectivity.dto.WifiInfo;
import com.oscar.find.network.util.LogCat;

/**
 * Created by oscar on 29/09/16.
 */

public class ConnectivityUtils {

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

                if (tm.getAllCellInfo() != null && tm.getAllCellInfo().size() > 0) {
                    LogCat.debug("allCellInfo: " + tm.getAllCellInfo().get(0).toString());
                }

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

        boolean wifiEnabled = wifiManager.isWifiEnabled();

        if(wifiEnabled) {
            LogCat.info("La conexión wifi está habilitada");
            //android.net.wifi.WifiInfo wifiInfo = wifiManager.getConnectionInfo();


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



            /**
            LogCat.debug("SSID: " + wifiInfo.getSSID());
            LogCat.debug("getLinkSpeed: " + wifiInfo.getLinkSpeed());
            LogCat.debug("getNetWorkId: " + wifiInfo.getNetworkId());
            LogCat.debug("mac address: " + wifiInfo.getMacAddress());
            LogCat.debug("getBSSID: " + wifiInfo.getBSSID());
            LogCat.debug("getIpAddres: " + wifiInfo.getIpAddress());

            LogCat.debug("serverAddress: " + wifiManager.getDhcpInfo().serverAddress);
            LogCat.debug("serverAddress: " + wifiManager.getDhcpInfo().toString());
            **/

        } else
            LogCat.info("La conexión wifi está deshabilitada");

        return wifiInfo;
    }





}
