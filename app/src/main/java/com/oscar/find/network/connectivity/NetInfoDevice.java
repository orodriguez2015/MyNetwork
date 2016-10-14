package com.oscar.find.network.connectivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.oscar.find.network.connectivity.dto.DeviceInfo;
import com.oscar.find.network.connectivity.dto.Host;
import com.oscar.find.network.connectivity.dto.NetworkHostData;
import com.oscar.find.network.connectivity.dto.WifiInfo;
import com.oscar.find.network.util.LogCat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
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
    public static final String SSID_NO_EXISTS     = "0x";
    public static final String BSSID_NO_EXISTS    = "00:00:00:00:00:00";

    /** INFORMACIÓN DE RED WIFI RECUPERADA CORRECTAMENTE **/
    public static final int WIFI_INFO_OK          = 0;
    /** CONEXION WIFI DESHABILITADA **/
    public static final int WIFI_INFO_NETWORK_DISABLED = -1;
    /** CONEXION WIFI HABILITADA PERO NO CONECTADA A UNA RED **/
    public static final int WIFI_INFO_NETWORK_ENABLED_NO_CONNECTED = -2;

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
     * @return WifiInfo que contiene los datos de conexión a la red wifi. Tener en cuenta que:
     *          WifiInfo.STATUS = 0  --> OK
     *          WifiInfo.STATUS = -1 --> La conexión WIFI está deshabilitada
     *          WifiInfo.STATUS = -2 --> La conexión WIFI está habilitada pero no está conectada a ninguna red
     */
    public static WifiInfo getWifiInfo(Context context) {
        WifiInfo wifiInfo = new WifiInfo();
        wifiInfo.STATUS = -2; // El dispositivo WIFI está habilitado pero no está conectado a ninguna red wifi
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        boolean wifiEnabled =  wifiManager.isWifiEnabled();
        wifiInfo.setWifiEnabled(wifiEnabled);

        if(wifiEnabled) {
            LogCat.info("La conexión wifi está habilitada");
            String SSID   = wifiManager.getConnectionInfo().getSSID();
            String BSSID = wifiManager.getConnectionInfo().getBSSID();

            if(!SSID.equals(SSID_NO_EXISTS) && !BSSID.equals(BSSID_NO_EXISTS)) {
                wifiInfo.setSSID(SSID);
                wifiInfo.setBSSID(BSSID);
                wifiInfo.setDns1(getIpFromIntSigned(wifiManager.getDhcpInfo().dns1));
                wifiInfo.setDns2(getIpFromIntSigned(wifiManager.getDhcpInfo().dns2));
                wifiInfo.setGateway(getIpFromIntSigned(wifiManager.getDhcpInfo().gateway));
                wifiInfo.setIdNetwork(wifiManager.getConnectionInfo().getNetworkId());
                wifiInfo.setIpAddress(getIpFromIntSigned(wifiManager.getDhcpInfo().ipAddress));
                wifiInfo.setIpAddressDhcpServer(getIpFromIntSigned(wifiManager.getDhcpInfo().serverAddress));
                wifiInfo.setMacAddress(wifiManager.getConnectionInfo().getMacAddress());
                wifiInfo.setNetmask(getIpFromIntSigned(wifiManager.getDhcpInfo().netmask));
                wifiInfo.setLinkSpeed(wifiManager.getConnectionInfo().getLinkSpeed());
                wifiInfo.STATUS = 0;
            }



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

        try {
            if (ip != null) {
                String ptrn = String.format(MAC_RE, ip.replace(".", "\\."));
                Pattern pattern = Pattern.compile(ptrn);
                bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"), BUF);
                String line;
                Matcher matcher;
                while ((line = bufferedReader.readLine()) != null) {
                   // LogCat.debug("linea salida comando arp: " + line);
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
            LogCat.error("No se puede leer la cache ARP: " + e.getMessage());
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
     * Devuelve la lista de hosts conectados
     * @return List<Host>
     */
    public static List<Host> getHostConnected() {
        List<Host> hosts = new ArrayList<Host>();
        BufferedReader bufferedReader = null;

        try {
            int linea = 0;
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/net/arp"), Charset.forName("UTF-8")));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                String[] splitted = line.split(" +");

                if (line != null && line.length() >= 4 && linea>0) {
                    String ip = splitted[0];
                    String mac = splitted[3];
                    if (!mac.equals(NOMAC)) {
                        Host host = new Host();
                        host.setIpAddress(ip);
                        host.setMacAddress(mac);
                        hosts.add(host);
                    }
                }
                linea++;
            }

        } catch (IOException e) {
            LogCat.error("No se puede leer la cache ARP: " + e.getMessage());
        } finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LogCat.error("Error al cerrar BufferedReader: " + e.getMessage());
            }
        }
        return hosts;
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

        // Get ip information


        String ipAddress = getWifiInfo(context).getIpAddress();
        if(ipAddress!=null) {
            network_ip = NetInfoDevice.getUnsignedLongFromIp(ipAddress);

            // Se recuperan las preferencias de la aplicación
            SharedPreferences prefs = context.getSharedPreferences("preferenciasMyNetwork", Context.MODE_PRIVATE);


            if (prefs.contains(NetInfoDevice.KEY_IP_START)) {
                // Custom IP
                network_start = NetInfo.getUnsignedLongFromIp(prefs.getString(NetInfoDevice.KEY_IP_START, NetInfoDevice.DEFAULT_IP_START));
                network_end = NetInfo.getUnsignedLongFromIp(prefs.getString(NetInfoDevice.KEY_IP_END, NetInfoDevice.DEFAULT_IP_END));
            } else {
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

            salida.setNetwork_ip(network_ip);
            salida.setNetwork_end(network_end);
            salida.setNetwork_start(network_start);
        } else salida = null;

        return salida;
    }


    /**
     * Recupera un objeto de la clase Host con la dirección IP y dirección mac de un
     * determinado host, con una determinada dirección IP
     * @param ipAddress long
     * @return Host
     */
    public static Host getHost(long ipAddress) {
        String ip  = NetInfoDevice.getIpFromLongUnsigned(ipAddress);
        String mac = null;
        Host host  = null;

        if(!TextUtils.isEmpty(ip)) {
            mac = NetInfoDevice.getHardwareAddress(ip);
            if(!mac.equals(NetInfoDevice.NOMAC)) {
                host = new Host();
                host.setMacAddress(mac);
                host.setIpAddress(ip);
            }
        }
        return host;
    }

}