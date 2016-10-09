package com.oscar.find.network.connectivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.oscar.find.network.connectivity.dto.DeviceInfo;
import com.oscar.find.network.connectivity.dto.NetworkHostData;
import com.oscar.find.network.connectivity.dto.WifiInfo;
import com.oscar.find.network.util.LogCat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oscar on 29/09/16.
 */

public class NetInfoDevice {

    private final static String MAC_RE = "^%s\\s+0x1\\s+0x2\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$";
    private final static int BUF       = 8 * 1024;
    public static final String NOMAC   = "00:00:00:00:00:00";
    public static long start           = -1062731519;
    public static long end             = -1062731266;
    public static long length          = 254;
    public static final String NOIP    = "0.0.0.0";
    public static int cidr              = 24;
    public static final String KEY_CIDR_CUSTOM      = "cidr_custom";
    public static final boolean DEFAULT_CIDR_CUSTOM = false;
    public static final String KEY_CIDR             = "cidr";
    public static final String DEFAULT_CIDR         = "24";

    public static final String KEY_IP_START       = "ip_start";
    public static final String DEFAULT_IP_START   = "0.0.0.0";
    public static final String KEY_IP_CUSTOM      = "ip_custom";
    public static final boolean DEFAULT_IP_CUSTOM = false;
    public static final String KEY_IP_END         = "ip_end";
    public static final String DEFAULT_IP_END     = "0.0.0.0";

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
                info.setManufacturer(Build.MANUFACTURER);
                info.setModel(Build.MODEL);
                info.setSoftwareVersion(Build.VERSION.RELEASE);
                info.setSerialNumber(Build.SERIAL);
                info.setApiNumber(Build.VERSION.SDK_INT);
                info.setProcessor(Build.HARDWARE);
                info.setApiNumber(Build.VERSION.SDK_INT);
            }

        }catch(Exception e) {
            e.printStackTrace();
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
     * Devuelve una ip en formato long a partir de una dirección IP en formato String
     * @param ip_addr String
     * @return long
     */
    public static long getUnsignedLongFromIp(String ip_addr) {
        String[] a = ip_addr.split("\\.");
        return (Integer.parseInt(a[0]) * 16777216 + Integer.parseInt(a[1]) * 65536
                + Integer.parseInt(a[2]) * 256 + Integer.parseInt(a[3]));
    }


    public static String getIpFromLongUnsigned(long ip_long) {
        String ip = "";
        for (int k = 3; k > -1; k--) {
            ip = ip + ((ip_long >> k * 8) & 0xFF) + ".";
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


    /**
     * Devuelve la dirección MAC de una determinada IP. Para ello se ejecuta el comando arp y
     * se comprueba si la dirección IP que se pasa como argumento existe en la salida del comando.
     * Sino existiese la IP, entonces de devuelve NetInfoDevice.NOMAC
     * @param ip String
     * @return String
     */
    public static String getHardwareAddress(String ip) {
        String hw = NOMAC;
        BufferedReader bufferedReader = null;

        LogCat.debug("NetInfoDevice.getHardwareAddress ip " + ip + " ===>");
        try {
            if (ip != null) {
                String ptrn = String.format(MAC_RE, ip.replace(".", "\\."));
                Pattern pattern = Pattern.compile(ptrn);
                bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"), BUF);
                String line;
                Matcher matcher;
                while ((line = bufferedReader.readLine()) != null) {
                    LogCat.debug("linea salida comando arp: " + line);
                    matcher = pattern.matcher(line);
                    if (matcher.matches()) {
                        hw = matcher.group(1);
                        break;
                    }
                }

            } else {
                LogCat.debug("ip is null");
            }
        } catch (IOException e) {
            LogCat.error("Can't open/read file ARP: " + e.getMessage());
            return hw;
        } finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LogCat.error("Error al cerrar BufferedReader: " + e.getMessage());
            }
        }
        return hw;
    }


    /**
     * Obtiene los datos de la dirección ip de inicio y de fin, que se utilizarán para
     * escanear la red wifi en busca de dispositivos conectados a la misma
     * @param context Context
     * @return NetworkHostData
     */
    public static NetworkHostData getNetworkHostData(Context context) {
        NetworkHostData salida = new NetworkHostData();
        long network_ip = 0;
        long network_start = 0;
        long network_end = 0;
        LogCat.debug("ANTES ActivityDiscovery.setInfo network_ip: " + network_ip);
        LogCat.debug("ANTES ActivityDiscovery.setInfo network_start: " + network_start);
        LogCat.debug("ANTES ActivityDiscovery.setInfo network_end: " + network_end);

        // Get ip information
        network_ip = NetInfoDevice.getUnsignedLongFromIp(NetInfoDevice.NOIP);

        // Se recuperan las preferencias de la aplicación
        SharedPreferences prefs = context.getSharedPreferences("preferenciasMyNetwork", Context.MODE_PRIVATE);

        if (prefs.contains(NetInfoDevice.KEY_IP_START)) {
            LogCat.debug("ActivityDiscovery.setInfo network_ip 1");
            // Custom IP
            network_start = NetInfo.getUnsignedLongFromIp(prefs.getString(NetInfoDevice.KEY_IP_START, NetInfoDevice.DEFAULT_IP_START));
            network_end = NetInfo.getUnsignedLongFromIp(prefs.getString(NetInfoDevice.KEY_IP_END, NetInfoDevice.DEFAULT_IP_END));
        } else {

            LogCat.debug("ActivityDiscovery.setInfo network_ip cidr");

            // Custom CIDR
            if (prefs.getBoolean(NetInfoDevice.KEY_CIDR_CUSTOM, NetInfoDevice.DEFAULT_CIDR_CUSTOM)) {
                NetInfoDevice.cidr = Integer.parseInt(prefs.getString(NetInfoDevice.KEY_CIDR, NetInfoDevice.DEFAULT_CIDR));
            }
            // Detected IP
            int shift = (32 - NetInfoDevice.cidr);
            if (NetInfoDevice.cidr < 31) {
                network_start = (network_ip >> shift << shift) + 1;
                network_end = (network_start | ((1 << shift) - 1)) - 1;
            } else {
                network_start = (network_ip >> shift << shift);
                network_end = (network_start | ((1 << shift) - 1));
            }

            // Reset ip start-end (is it really convenient ?)
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(NetInfoDevice.KEY_IP_START, NetInfoDevice.getIpFromLongUnsigned(network_start));
            edit.putString(NetInfoDevice.KEY_IP_END, NetInfoDevice.getIpFromLongUnsigned(network_end));
            edit.commit();
        }


        LogCat.debug("DESPUES ActivityDiscovery.setInfo network_ip: " + network_ip);
        LogCat.debug("DESPUES ActivityDiscovery.setInfo network_start: " + network_start);
        LogCat.debug("DESPUES ActivityDiscovery.setInfo network_end: " + network_end);

        salida.setNetwork_ip(network_ip);
        salida.setNetwork_end(network_end);
        salida.setNetwork_start(network_start);


        return salida;
    }

}