package com.oscar.find.network.connectivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.oscar.find.network.activity.HostDiscoveryActivity;
import com.oscar.find.network.activity.R;
import com.oscar.find.network.connectivity.dto.Host;
import com.oscar.find.network.connectivity.dto.ParamsAsyncTask;
import com.oscar.find.network.util.LogCat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by oscar on 08/10/16.
 */

public class HostDiscoveryAsyncTask extends AsyncTask<ParamsAsyncTask,Void,List<Host>> {

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
    protected List<Host> doInBackground(ParamsAsyncTask... params) {

        long ip = 0;
        long size  = NetInfoDevice.length;

        ParamsAsyncTask parametros = (ParamsAsyncTask)params[0];
        long start = parametros.getNetworkHostData().getNetwork_start();
        long end   = parametros.getNetworkHostData().getNetwork_end();

        LogCat.debug("DefaultDiscovery.doInBackgroud ====>");
        LogCat.debug("DefaultDiscovery.doInBackgroud discover!=null");
        LogCat.debug("DefaultDiscovery.doInBackgroud discover start: " + start);
        LogCat.debug("DefaultDiscovery.doInBackgroud discover end: " + end);
        LogCat.debug("DefaultDiscovery.doInBackgroud discover end: " + size);

        LogCat.debug("start=" + NetInfo.getIpFromLongUnsigned(NetInfoDevice.start) + " (" + start
                + "), end=" + NetInfo.getIpFromLongUnsigned(end) + " (" + end
                + "), length=" + size);

                mPool = Executors.newFixedThreadPool(THREADS);
                if (ip <= end && ip >= start) {

                    LogCat.debug("Back and forth scanning");
                    // gateway
                    //launch(start);

                    // hosts
                    long pt_backward = ip;
                    long pt_forward = ip + 1;
                    long size_hosts = size - 1;
                    long pt_move = 0;


                    for (int i = 0; i < size_hosts; i++) {
                        // Set pointer if of limits
                        if (pt_backward <= start) {
                            pt_move = 2;
                        } else if (pt_forward > end) {
                            pt_move = 1;
                        }
                        // Move back and forth
                        if (pt_move == 1) {

                            altaHost(pt_backward);
                            pt_backward--;
                            pt_move = 2;
                        } else if (pt_move == 2) {

                            altaHost(pt_backward);
                            pt_forward++;
                            pt_move = 1;
                        }
                    }
                } else {
                    LogCat.debug("Sequencial scanning");
                    for (long i = start; i <= end; i++) {
                        altaHost(i);
                    }
                }
                mPool.shutdown();
        /**
                try {

                    if(!mPool.awaitTermination(TIMEOUT_SCAN, TimeUnit.SECONDS)){
                        mPool.shutdownNow();
                        LogCat.debug("Shutting down pool");
                        if(!mPool.awaitTermination(TIMEOUT_SHUTDOWN, TimeUnit.SECONDS)){
                            LogCat.debug("Pool did not terminate");
                        }
                    }

                } catch (InterruptedException e){
                    LogCat.debug(e.getMessage());
                    mPool.shutdownNow();
                    Thread.currentThread().interrupt();
                } finally {

                }
         **/
        return hosts;
    }


    /**
     * Alta de host en la colección de hosts
     * @param ip long
     */
    private void altaHost(long ip) {
        Host host = NetInfoDevice.getHost(ip);
        if(host!=null) {
            hosts.add(host);
        }
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