package network.oscar.com.mynetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

import com.oscar.find.network.connectivity.ConnectivityUtils;
import com.oscar.find.network.permissions.PermissionUtil;
import com.oscar.find.network.util.AlertDialogHelper;
import com.oscar.find.network.util.LogCat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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



        if(PermissionUtil.appHasAccessNetworkState(getApplicationContext())) {
            LogCat.info("La app tiene permisos para comprobar las conexiones de red del dispositivo");

            if(!ConnectivityUtils.hasDeviceConnectionsNetworkEnabled(this)) {
                // El dispositivo no tiene habilitada ninguna conexión de red
                LogCat.error(getString(R.string.error_connection_network_disable));
                AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.error_connection_network_disable));

            } else
            if(!PermissionUtil.appHasAccessWifiState(getApplicationContext())) {
                // La app no tiene permiso para acceder al estado de la red wifi
                LogCat.error(getString(R.string.error_wifi_access_status));
                AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.error_wifi_access_status));
            } else {

                // Se obtiene el estado de la wifi

                WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);

                boolean wifiEnabled = wifiManager.isWifiEnabled();

                if(wifiEnabled) {
                    LogCat.info("La conexión wifi está habilitada");
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    LogCat.debug("SSID: " + wifiInfo.getSSID());
                    LogCat.debug("getLinkSpeed: " + wifiInfo.getLinkSpeed());
                    LogCat.debug("getNetWorkId: " + wifiInfo.getNetworkId());
                    LogCat.debug("mac address: " + wifiInfo.getMacAddress());
                    LogCat.debug("getBSSID: " + wifiInfo.getBSSID());
                    LogCat.debug("getIpAddres: " + wifiInfo.getIpAddress());

                    LogCat.debug("serverAddress: " + wifiManager.getDhcpInfo().serverAddress);
                    LogCat.debug("serverAddress: " + wifiManager.getDhcpInfo().toString());

                    ConnectivityUtils.getMobileInfo(getApplicationContext());


                } else
                    LogCat.info("La conexión wifi está deshabilitada");



                LogCat.debug("WIFI_INFO: " + ConnectivityUtils.getWifiInfo(getApplicationContext()).toString());



            }

        } else {
            LogCat.error(getString(R.string.error_network_access_status));
            AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.error_network_access_status));
        }

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        LogCat.debug("tipo: " + netInfo.getType());
        LogCat.debug("extraInfo: " + netInfo.getExtraInfo());
        LogCat.debug("reason: " + netInfo.getReason());
        LogCat.debug("subtypeName: " + netInfo.getSubtypeName());
        LogCat.debug("state: " + netInfo.getState());


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
