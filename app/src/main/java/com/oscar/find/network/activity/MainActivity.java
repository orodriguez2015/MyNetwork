package com.oscar.find.network.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oscar.find.network.connectivity.NetInfoDevice;
import com.oscar.find.network.connectivity.dto.DeviceInfo;
import com.oscar.find.network.permissions.PermissionUtil;
import com.oscar.find.network.util.AlertDialogHelper;
import com.oscar.find.network.util.LogCat;

import java.util.Collection;

/**
 * Actividad principal
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView labelRedWifi = null;
    private TextView labelDireccionMac = null;
    private TextView labelDireccionPuertaEnlace  = null;
    private TextView labelDireccionIp  = null;
    private TextView labelMascaraRed  = null;
    private TextView labelDireccionDnsPrimario  = null;
    private TextView labelDireccionDnsSecundario  = null;
    private TextView labelFabricanteDispositivo  = null;
    private TextView labelModeloDispositivo  = null;
    private TextView labelNumeroSerieDispositivo  = null;
    private TextView labelVersionSoftwareDispositivo  = null;
    private TextView labelProcesador  = null;
    private TextView labelVersionApiAndroid  = null;
    private TextView labelWifiDesconectado   = null;
    private Button botonDescubrir            = null;
    private IntentFilter intentFilter = null;
    private WifiP2pManager wifip2pManager = null;
    private WifiP2pManager.Channel channel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Button botonDescubrir = (Button)findViewById(R.id.btnDescubrir);
        botonDescubrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHostConnectWifiActivity();
            }
        });


        if(PermissionUtil.appHasAccessNetworkState(getApplicationContext())) {
            LogCat.info("La app tiene permisos para comprobar las conexiones de red del dispositivo");

            if(!NetInfoDevice.hasDeviceConnectionsNetworkEnabled(this)) {
                // El dispositivo no tiene habilitada ninguna conexión de red
                LogCat.error(getString(R.string.error_connection_network_disable));
                AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.error_connection_network_disable));

            } else
            if(!PermissionUtil.appHasAccessWifiState(getApplicationContext())) {
                // La app no tiene permiso para acceder al estado de la red wifi
                LogCat.error(getString(R.string.error_wifi_access_status));
                AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.error_wifi_access_status));
            } else {

                // Se carga la información del dispositivo y de la red wifi en el activity
                this.loadNetInfoDevice();
                this.loadWifiManagerDevice();

            }

        } else {
            LogCat.error(getString(R.string.error_network_access_status));
            AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.error_network_access_status));
        }

    }


    /**
     * Muestra la actividad con los direcciones ip de los hosts conectados a la misma red WIFI
     * que el dispositivo del usuario
     */
    private void showHostConnectWifiActivity() {
        Intent intent = new Intent(MainActivity.this,HostDiscoveryActivity.class);
        startActivity(intent);
    }


    /**
     * Carga la información de la red WIFI del dispositivo
     */
    private void loadNetInfoDevice() {
        labelDireccionIp                = (TextView)findViewById(R.id.labelDireccionIp);
        labelDireccionMac               = (TextView)findViewById(R.id.labelDireccionMac);
        labelDireccionPuertaEnlace      = (TextView)findViewById(R.id.labelDireccionPuertaEnlace);
        labelDireccionDnsPrimario       = (TextView)findViewById(R.id.labelDnsPrimario);
        labelDireccionDnsSecundario     = (TextView)findViewById(R.id.labelDnsSecundario);
        labelMascaraRed                 = (TextView)findViewById(R.id.labelMascaraRed);
        labelRedWifi                    = (TextView)findViewById(R.id.labelRedWifi);
        labelFabricanteDispositivo      = (TextView)findViewById(R.id.labelFabricante);
        labelModeloDispositivo          = (TextView)findViewById(R.id.labelModelo);
        labelNumeroSerieDispositivo     = (TextView)findViewById(R.id.labelNumeroSerie);
        labelVersionSoftwareDispositivo = (TextView)findViewById(R.id.labelNumeroVersion);
        labelProcesador                 = (TextView)findViewById(R.id.labelProcesador);
        labelVersionApiAndroid          = (TextView)findViewById(R.id.labelApiLevel);
        labelWifiDesconectado           = (TextView)findViewById(R.id.labelWifiDesconectado);


        com.oscar.find.network.connectivity.dto.WifiInfo wifiInfo = NetInfoDevice.getWifiInfo(getApplicationContext());
        DeviceInfo deviceInfo = NetInfoDevice.getMobileInfo(getApplicationContext());

        labelDireccionIp.setText(wifiInfo.getIpAddress());
        labelDireccionMac.setText(wifiInfo.getMacAddress());
        labelDireccionPuertaEnlace.setText(wifiInfo.getIpAddressDhcpServer());
        labelDireccionDnsPrimario.setText(wifiInfo.getDns1());
        labelDireccionDnsSecundario.setText(wifiInfo.getDns2());
        labelRedWifi.setText(wifiInfo.getSSID());
        labelMascaraRed.setText(wifiInfo.getNetmask());
        labelFabricanteDispositivo.setText(deviceInfo.getManufacturer());
        labelModeloDispositivo.setText(deviceInfo.getModel());
        labelNumeroSerieDispositivo.setText(deviceInfo.getSerialNumber());
        labelVersionSoftwareDispositivo.setText(deviceInfo.getSoftwareVersion());
        labelProcesador.setText(deviceInfo.getProcessor());
        labelVersionApiAndroid.setText(deviceInfo.getApiNumber().toString());

        if(wifiInfo.isWifiEnabled()) {
            labelWifiDesconectado.setVisibility(View.INVISIBLE);
        }

    }


    private void loadWifiManagerDevice() {

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        wifip2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifip2pManager.initialize(this, getMainLooper(), null);

        wifip2pManager.discoverPeers(channel,new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank.  Code for peer discovery goes in the
                // onReceive method, detailed below.

                LogCat.debug("onSuccess =====>");



            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                LogCat.debug("onFailure =====>");
            }
        });



        wifip2pManager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {

                LogCat.debug("onPeersAvailable ======>");
                Collection<WifiP2pDevice> devices = (Collection<WifiP2pDevice>)peers.getDeviceList();
                if(devices==null) {
                    LogCat.debug("devices nulos");
                } else {
                    LogCat.debug("Dispositivos recuperados: " + devices.size());
                }


            }
        });
    }


    private void startDiscovering() {
        LogCat.debug("MainActivity.startDiscovering ====>");
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}