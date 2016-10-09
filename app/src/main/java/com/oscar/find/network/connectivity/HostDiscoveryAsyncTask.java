package com.oscar.find.network.connectivity;

import android.os.AsyncTask;

import com.oscar.find.network.util.LogCat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by oscar on 08/10/16.
 */

public class HostDiscoveryAsyncTask extends AsyncTask<String,Void,String> {

    private int THREADS = 10;
    private ExecutorService mPool =null;

    @Override
    protected String doInBackground(String... params) {

        String salida = null;
        //String ip = (String)params[0];

        long ip = 0;
        long start = NetInfoDevice.start;
        long end   = NetInfoDevice.end;
        long size  = NetInfoDevice.length;

        LogCat.debug("DefaultDiscovery.doInBackgroud ====>");
        LogCat.debug("DefaultDiscovery.doInBackgroud discover!=null");
        LogCat.debug("DefaultDiscovery.doInBackgroud discover start: " + start);
        LogCat.debug("DefaultDiscovery.doInBackgroud discover end: " + end);
        LogCat.debug("DefaultDiscovery.doInBackgroud discover end: " + size);


        String dato = "192.168.1.1";

        // Se obtiene la dirección MAC
        //salida = NetInfoDevice.getHardwareAddress(dato);
        LogCat.debug("MAC de la IP " + dato + " es " + salida);



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
                            //launch(pt_backward);
                            pt_backward--;
                            pt_move = 2;
                        } else if (pt_move == 2) {
                            //launch(pt_forward);
                            pt_forward++;
                            pt_move = 1;
                        }
                    }
                } else {
                    LogCat.debug("Sequencial scanning");
                    for (long i = start; i <= end; i++) {
                        //launch(i);
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
        return null;


    }

}
