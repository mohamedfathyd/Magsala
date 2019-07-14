package com.khalej.magsala.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.magsala.Activity.Main_Mwasel;
import com.khalej.magsala.Activity.Order_detail;
import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.Model.contact_order;
import com.khalej.magsala.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapter_recervations_ extends RecyclerView.Adapter<RecyclerAdapter_recervations_.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_order> contactslist;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    int id,idd,price;
    ProgressDialog progressDialog;
    String provider_name;
    public RecyclerAdapter_recervations_(Context context, List<contact_order> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recervation_,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("magsala", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        id=sharedpref.getInt("id",0);
        provider_name=sharedpref.getString("name",null);


    holder.Name.setText("اسم صاحب الطلب :"+contactslist.get(position).getName());
   holder.finish_date.setText("تاريخ :" +contactslist.get(position).getDate());
        holder.price.setText("العنوان :" +contactslist.get(position).getAddress());


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
               LayoutInflater inflater = dialogBuilder.getLayoutInflater();
               View dialogView = inflater.inflate(R.layout.dialog_edit, null);

               dialogBuilder.setTitle("أدخل السعر الذى حصلت عليه");

               dialogBuilder.setCancelable(false);


               final EditText etComments = (EditText) dialogView.findViewById(R.id.etComments);
               dialogBuilder.setView(dialogView);
               dialogBuilder.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       price = Integer.parseInt(etComments.getText().toString());
                       fetchInfo(contactslist.get(position).getId(),price);
                   }
               });


               dialogBuilder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialogBuilder.dismiss();
                   }
               });

               dialogBuilder.show();

           }
       });
    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,finish_date,price;

    public MyViewHolder(View itemView) {
        super(itemView);

        Name=(TextView)itemView.findViewById(R.id.txt_fish_title);
        finish_date=(TextView)itemView.findViewById(R.id.txt_title);
        price=(TextView)itemView.findViewById(R.id.txt_);



    }
}
    public void fetchInfo(int idd,int price) {
        progressDialog = ProgressDialog.show(context, "جارى التسجيل", "Please wait...", false, false);
        progressDialog.show();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = null;
        call=apiinterface.finish_order(id,provider_name,idd,price);



        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم تسجيل انه طلب منفذ ");
                dlgAlert.setTitle("نظرة النظافة");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                  context.startActivity(new Intent(context, Main_Mwasel.class));
                ((Activity)context).finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }
}