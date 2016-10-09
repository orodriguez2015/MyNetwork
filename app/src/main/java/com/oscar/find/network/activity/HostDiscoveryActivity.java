package com.oscar.find.network.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oscar.find.network.connectivity.dto.NetworkHostData;

/**
 * Actividad que se encarga de mostrar en un RecyclerView la lista de direcciones IP
 * del resto de dispositivos de la red
 */
public class HostDiscoveryActivity extends AppCompatActivity {


    protected SharedPreferences prefs = null;


    /**
     * Método onCreate que se encarga de crear la interfaz de usuario de la actividad
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_discovery);
        setTitle(getString(R.string.connected_devices));

        // Se recupera los datos de red necesarios para comenzar a comprobar cuales son los
        NetworkHostData data = Preferences.getNetworkHostData(getApplicationContext());

    }
}
