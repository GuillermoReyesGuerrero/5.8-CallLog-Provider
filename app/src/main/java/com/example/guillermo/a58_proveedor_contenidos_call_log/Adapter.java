package com.example.guillermo.a58_proveedor_contenidos_call_log;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guillermo on 20/05/18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.CallViewHolder> {

    List<ItemLog> datos;

    public Adapter(List<ItemLog> datos) {
        this.datos = datos;
    }

    @Override
    public CallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlog_layout,parent,false);
        CallViewHolder callViewHolder=new CallViewHolder(v);
        return callViewHolder;
    }

    @Override
    public void onBindViewHolder(CallViewHolder holder, int position) {
        holder.data.setText(datos.get(position).getDatos());

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public class CallViewHolder extends RecyclerView.ViewHolder{
        TextView data;

        public CallViewHolder(View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.txtcalllog);
        }
    }
}
