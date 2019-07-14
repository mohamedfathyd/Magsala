package com.khalej.magsala.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.R;

import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order_detail extends AppCompatActivity {
       TextView name,phone,address,details,getfinal;
       Intent intent;
       Toolbar toolbar;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    int id,idd;
    ProgressDialog progressDialog;

    String provider_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        sharedpref = getSharedPreferences("magsala", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        id=sharedpref.getInt("id",0);
        provider_name=sharedpref.getString("name",null);

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        getfinal=findViewById(R.id.getfinal);
        details=findViewById(R.id.details);
        intent=getIntent();
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        address.setText(intent.getStringExtra("address"));
        details.setText(intent.getStringExtra("details"));
        idd=intent.getIntExtra("id",0);
        getfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
            }
        });
    }
    public void fetchInfo() {
        progressDialog = ProgressDialog.show(Order_detail.this, "جارى توجيه هذا الطلب اليك لتنفيذه", "Please wait...", false, false);
        progressDialog.show();


        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = null;
        call=apiinterface.update_status(id,provider_name,idd);



        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Order_detail.this);
                dlgAlert.setMessage("تم ربط هذا الطلب بك عليك التواصل مع الزبون لتنفيذه ");
                dlgAlert.setTitle("نظرة النظافة");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
               startActivity(new Intent(Order_detail.this, Main_Mwasel.class));
                      finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }
}
