package com.oscar.find.network.listener;


//import com.oscar.find.

import android.view.View;

import com.oscar.find.network.connectivity.dto.Host;
import com.oscar.find.network.util.LogCat;

import java.util.List;

/**
 * Clase OnSwitchItemClickCallback que permite obtener el Host seleccionado por el usuario
 * Created by oscar on 13/10/16.
 */
public class OnSwitchItemClickCallback implements OnItemClickListener.OnItemClickCallback {

    private List<Host> hosts = null;

    /**
     * Constructor
     * @param hosts
     */
    public OnSwitchItemClickCallback(List<Host> hosts) {
        this.hosts = hosts;
    }

    /**
     * Interface OnItemClickCallback
     */
    public void onItemClicked(View view, int position){
        LogCat.debug(" ====> switch onItemClicked position: " + position);
        LogCat.debug(" ====> Dirección ip seleccionada: " + hosts.get(position).getIpAddress());
    }

}
