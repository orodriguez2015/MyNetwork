package com.oscar.find.network.connectivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.oscar.find.network.activity.HostDiscoveryActivity;
import com.oscar.find.network.activity.R;
import com.oscar.find.network.connectivity.dto.Host;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by oscar on 08/10/16.
 */

public class HostDiscoveryAsyncTask extends AsyncTask<Void,Void,List<Host>> {

    private int THREADS = 10;
    private ExecutorService mPool =null;
    private List<Host> hosts = new ArrayList<Host>();
    private ProgressDialog pg = null;
    private HostDiscoveryActivity actividad = null;

    /**
     * Constructor
     * @param actividad Actividad desde la que se invoca a la tarea asíncrona
     */
    public HostDiscoveryAsyncTask(HostDiscoveryActivity actividad) {
        this.actividad = actividad;
    }

    @Override
    //protected List<Host> doInBackground(ParamsAsyncTask... params) {
    protected List<Host> doInBackground(Void... params) {
        return hosts = NetInfoDevice.getHostConnected();
    }

    /**
     * Método onPostExecute que se ejecuta después de finalizar la tarea asíncrona
     * @param hosts List<Host>
     */
    @Override
    protected void onPostExecute(List<Host> hosts) {
        if (pg.isShowing()) {
            this.actividad.mostrarHosts(hosts);
            pg.dismiss();
        }
    }

    /**
     * Método onPreExecute que se ejecuta antes de iniciar la tarea asíncrona
     */
    @Override
    protected void onPreExecute() {
        this.pg = ProgressDialog.show(this.actividad, this.actividad.getString(R.string.procesando),this.actividad.getString(R.string.espere), true, false);
    }

}