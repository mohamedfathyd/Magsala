package com.khalej.magsala.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.Model.contact_order;
import com.khalej.magsala.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapter_recervations_a extends RecyclerView.Adapter<RecyclerAdapter_recervations_a.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_order> contactslist;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    int id, idd, price;
    String provider_name;

    public RecyclerAdapter_recervations_a(Context context, List<contact_order> contactslist) {
        this.contactslist = contactslist;
        this.context = context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recervation_a, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("magsala", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        id = sharedpref.getInt("id", 0);
        provider_name = sharedpref.getString("name", null);


        holder.Name.setText("اسم صاحب الطلب :" + contactslist.get(position).getName());
        holder.finish_date.setText("تاريخ :" + contactslist.get(position).getDate());
        holder.price.setText("العنوان :" + contactslist.get(position).getAddress());
        holder.money.setText("السعر :" + contactslist.get(position).getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name, finish_date, price;
        TextView money;

        public MyViewHolder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.txt_fish_title);
            finish_date = (TextView) itemView.findViewById(R.id.txt_title);
            price = (TextView) itemView.findViewById(R.id.txt_);
            money = (TextView) itemView.findViewById(R.id.price);


        }
    }
}