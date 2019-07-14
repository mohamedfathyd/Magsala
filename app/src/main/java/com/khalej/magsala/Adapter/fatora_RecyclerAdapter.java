package com.khalej.magsala.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalej.magsala.Model.orders_realm;
import com.khalej.magsala.R;

import java.util.List;

public class fatora_RecyclerAdapter extends RecyclerView.Adapter<fatora_RecyclerAdapter.MyViewHolder> {

    private Context context;
    List<orders_realm> contactslist;
    private int i=0;
    Dialog dialog;
    public fatora_RecyclerAdapter(Context context, List<orders_realm> contactslist){
        this.contactslist=contactslist;
        this.context=context;
        this.i=i;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rawlist_fatora,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
     holder.Name.setText(contactslist.get(position).getDetails());


    }

    @Override
    public int getItemCount() {
        return contactslist.size();
    }

public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;

    public MyViewHolder(View itemView) {
        super(itemView);
        Name=(TextView)itemView.findViewById(R.id.name);



    }
}}