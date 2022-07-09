package com.example.index.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.index.R;
import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder>
implements View.OnClickListener {


    private List<com.example.index.Clases.Usuarios> bdata;
    private LayoutInflater binflater;
    private Context bcontext;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    public AdaptadorUsuarios(List<com.example.index.Clases.Usuarios> itemlist, Context context){
        this.binflater=LayoutInflater.from(context);
        this.bcontext=context;
        this.bdata=itemlist;
    }

    @Override
    public int getItemCount(){
        return bdata.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = binflater.inflate(R.layout.modelcard_usuarios,null);
        view.setOnClickListener(this);//
        return new ViewHolder(view);
    }
    @Override //Esto se repetir segun el nro de cards
    public void onBindViewHolder(final ViewHolder holder,final int position){
        com.example.index.Clases.Usuarios item = bdata.get(position);

        //holder.foto.setImageResource(item.getFoto());
        holder.name.setText(item.getName());
        holder.gmail.setText(item.getGmail());
    }

    public void setIteams(List<com.example.index.Clases.Usuarios> items){
        bdata=items;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,gmail;

        ViewHolder(View iteamView){
            super(iteamView);
            //foto=iteamView.findViewById(R.id.imgItem);
            name=iteamView.findViewById(R.id.txtnames);
            gmail=iteamView.findViewById(R.id.txtdesc);
        }
    }

}
