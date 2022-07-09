package com.example.index.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.index.R;

import java.util.List;

public class AdaptadorEventosAdm extends RecyclerView.Adapter<AdaptadorEventosAdm.ViewHolder>
        implements View.OnClickListener {

    private List<ClassEventos> bdata;
    private LayoutInflater binflater;
    private Context bcontext;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    public AdaptadorEventosAdm(List<com.example.index.Clases.ClassEventos> itemlist, Context context){
        this.binflater=LayoutInflater.from(context);
        this.bcontext=context;
        this.bdata=itemlist;
    }

    @Override
    public int getItemCount(){
        return bdata.size();
    }
    @Override
    public AdaptadorEventosAdm.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = binflater.inflate(R.layout.modelcard_eventos,null);
        view.setOnClickListener(this);//
        return new AdaptadorEventosAdm.ViewHolder(view);
    }
    @Override //Esto se repetir segun el nro de cards
    public void onBindViewHolder(final AdaptadorEventosAdm.ViewHolder holder, final int position){
        com.example.index.Clases.ClassEventos item = bdata.get(position);

        //Estos son los datos especificos que extrae
        holder.editname.setText(item.getName());
        holder.editfecha.setText(item.getFecha());
        holder.edithora.setText(item.getHora());
    }

    public void setIteams(List<com.example.index.Clases.ClassEventos> items){
        bdata=items;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView editname,editfecha,edithora;

        ViewHolder(View iteamView){
            super(iteamView);
            editname=iteamView.findViewById(R.id.showeventname);
            editfecha = iteamView.findViewById(R.id.showeventofecha);
            edithora = iteamView.findViewById(R.id.showeventohora);
        }
    }


}
