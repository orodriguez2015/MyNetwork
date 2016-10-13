package com.oscar.find.network.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.oscar.find.network.adapter.HostAdapter;
import com.oscar.find.network.connectivity.HostDiscoveryAsyncTask;
import com.oscar.find.network.connectivity.NetInfoDevice;
import com.oscar.find.network.connectivity.dto.Host;
import com.oscar.find.network.connectivity.dto.NetworkHostData;
import com.oscar.find.network.connectivity.dto.ParamsAsyncTask;
import com.oscar.find.network.util.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad que se encarga de mostrar en un RecyclerView la lista de direcciones IP
 * del resto de dispositivos de la red
 */
public class HostDiscoveryActivity extends AppCompatActivity {

    private LinearLayoutManager linearLayoutManager = null;
    protected SharedPreferences prefs = null;
    private RecyclerView recycler = null;
    private HostAdapter hostAdapter = null;

    /**
     * Método onCreate que se encarga de crear la interfaz de usuario de la actividad
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_discovery);
        setTitle(getString(R.string.connected_devices));

        // Se muestra el botón de atrás en la barra de título
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Obtener el RecyclerView que contendrá la lista de orígenes/fuentes de datos rss
        recycler = (RecyclerView) findViewById(R.id.recicladorHostDiscovery);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);


        // Se recupera los datos de red necesarios para comenzar a escanear las direcciones ip
        // de los dispositivos conectados a la misma WIFI que el dispositivo actual
        NetworkHostData data = NetInfoDevice.getNetworkHostData(getApplicationContext());

        try {
            // Se crea el adapter para renderizar la vista
            List<Host> hosts = new ArrayList<Host>();
            hostAdapter = new HostAdapter(hosts);
            hostAdapter.notifyDataSetChanged();
            recycler.setAdapter(hostAdapter);

            // Se recuperan las lista de hosts/dispositivos que están conectados a la misma red wifi
            // que el dispositivo del usuario
            ParamsAsyncTask params = new ParamsAsyncTask();
            params.setNetworkHostData(data);
            HostDiscoveryAsyncTask task = new HostDiscoveryAsyncTask(this);
            task.execute(params);

            LogCat.debug("Hosts recuperados: " + hosts);

            for(Host h: hosts) {
                LogCat.debug(" IP ADDRESS: " + h.getIpAddress());
                LogCat.debug(" MAC ADDRESS: " + h.getMacAddress());
                LogCat.debug("");
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que pasa la colección
     * @param hosts
     */
    public void mostrarHosts(List<Host> hosts) {
        this.hostAdapter.setHosts(hosts);
        this.hostAdapter.notifyDataSetChanged();
    }

    /**
     * Se sobreescribe el método onKeyDown para detectar que tecla ha pulsado el usuario, y
     * ejecutar la acción que corresponda. En este caso, si pulsa el botón atrás, se devuelve un intent
     * a la actividad MainActivity para que esta ejecute la acción que sea conveniente
     * @param keyCode int
     * @param event KeyEvent
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Método onOptionsItemSelected. Tiene que implementarse por ejemplo, para poder captura el evento producido al
     * pulsar sobre la fecha que permite volver hacia atrás
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respuesta al pulsar el botón de atrás
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
