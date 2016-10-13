package com.oscar.find.network.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.oscar.find.network.activity.R;
import com.oscar.find.network.connectivity.dto.Host;
import com.oscar.find.network.listener.OnItemClickListener;
import com.oscar.find.network.listener.OnSwitchItemClickCallback;

import java.util.List;


/**
 * Adapter para mostrar los hosts.
 * Implementa la interfaz View.OnClickListener para detectar el evento de selección de uno
 * de los elementos del adaptador
 * Created by oscar on 11/06/16.
 */
public class HostAdapter extends RecyclerView.Adapter<HostAdapter.HostViewHolder> implements View.OnClickListener {

    private List<Host> items = null;
    private View.OnClickListener listener = null;


    /**
     * Clase NoticiaViewHolder que contiene los componentes que forman
     * parte de la vista a renderizar para cada componente
     *
     */
    public static class HostViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView txtDireccionIp;
        public TextView txtDireccionMac;
        public Switch switchDireccionIp;

        /**
         * Constructor
         * @param v: View
         */
        public HostViewHolder(View v) {
            super(v);
            txtDireccionIp    = (TextView) v.findViewById(R.id.direccionIp);
            txtDireccionMac   = (TextView) v.findViewById(R.id.direccionMac);
            switchDireccionIp = (Switch)   v.findViewById(R.id.switchDireccionIp);

        }
    }


    /**
     * Constructor
     * @param items List<Host>
     */
    public HostAdapter(List<Host> items) {
        this.items  = items;
    }


    /**
     * Devuelve la colección de host que se muestran en el adapter
     * @return List<Host>
     */
    public List<Host> getHosts() {
        return this.items;
    }

    public void setHosts(List<Host> hosts) {
        this.items = hosts;
    }


    /**
     * Devuelve el número de hosts que se muestran
     * @return int
     */
    @Override
    public int getItemCount() {
        return items.size();
    }


    /**
     * Añade un host
     * @param host Host
     */
    public void addItem(Host host) {
        this.items.add(host);
    }

    /**
     * Elimina un elemento del adapter de noticia
     * @param pos Posición del elemento a borrar
     */
    public void removeItem(int pos) {
        getHosts().remove(pos);
    }


    /**
     * Devuelve una determinada noticia
     * @param pos int que indica una posición válida de la colección de noticias
     * @return Noticia
     */
    public Host getHost(int pos) {
        Host host = null;
        if(pos>=0 && pos<getHosts().size()) {
            host = getHost(pos);
        }
        return host;
    }

    @Override
    public HostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        /** Se carga el layout host_discovery_wifi.xml para mostrar la información de cada noticia **/
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.host_discovery_wifi, viewGroup, false);

        v.setOnClickListener(this);

        return new HostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HostViewHolder viewHolder, int i) {
        viewHolder.txtDireccionMac.setText(items.get(i).getMacAddress());
        viewHolder.txtDireccionIp.setText(items.get(i).getIpAddress());


        viewHolder.switchDireccionIp.setOnClickListener(new OnItemClickListener(i,new OnSwitchItemClickCallback(this.items)));

    }


    /**
     * Establecer el listener de tipo OnClickListener
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Cuando el usuario ejecuta el método onClick sobre la vista
     * @param view View
     */
    public void onClick(View view) {
        if(listener != null) {
            listener.onClick(view);
        }

    }
}
