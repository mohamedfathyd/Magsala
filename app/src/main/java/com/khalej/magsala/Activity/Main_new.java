package com.khalej.magsala.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.khalej.magsala.Adapter.RecyclerAdapter_first;
import com.khalej.magsala.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.Model.contact_annonce;
import com.khalej.magsala.Model.content_category;
import com.khalej.magsala.R;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_new extends AppCompatActivity {
    private RecyclerAdapter_first_annonce recyclerAdapter_annonce;
    private List<content_category> contactList;
    private List<contact_annonce> contactList_annonce;
    ProgressBar progressBar;
    private apiinterface_home apiinterface;
    int x=0;
    ImageView segad,malabs,mafroshat,fanadek,manzel,sayarat,etasel,an;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager1,layoutManager2;
    CountDownTimer countDownTimer;
    ImageView car;
    int y=0;
    private RecyclerAdapter_first recyclerAdapter;
    private static final String EXTRA_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        car=findViewById(R.id.car);
        segad=findViewById(R.id.segad);
        malabs=findViewById(R.id.malabs);
        mafroshat=findViewById(R.id.mafroshat);
        fanadek=findViewById(R.id.fanadek);
        manzel=findViewById(R.id.manzel);
        sayarat=findViewById(R.id.sayarat);
        etasel=findViewById(R.id.etasel);
        an=findViewById(R.id.an);
        etasel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "tel:" + "0568639395";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });
        an.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog settingsDialog = new Dialog(Main_new.this);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(R.layout.image_show);
                ImageView img = (ImageView) settingsDialog.findViewById(R.id.img);
                Glide.with(Main_new.this).load(R.drawable.down).error(R.drawable.down).into(img);
                settingsDialog.show();
            }
        });
        segad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main_new.this,MainActivity.class);
                intent.putExtra("type","سجاد");
                startActivity(intent);
            }
        });
        malabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main_new.this,MainActivity.class);
                intent.putExtra("type","ملابس");
                startActivity(intent);
            }
        });
        mafroshat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main_new.this,MainActivity.class);
                intent.putExtra("type","مفروشات");
                startActivity(intent);
            }
        });
        fanadek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main_new.this,MainActivity.class);
                intent.putExtra("type","فنادق");
                startActivity(intent);
            }
        });
        manzel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main_new.this,MainActivity.class);
                intent.putExtra("type","منزل");
                startActivity(intent);
            }
        });
        sayarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main_new.this,MainActivity.class);
                intent.putExtra("type","سيارات");
                startActivity(intent);
            }
        });
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_new.this,Shopping_car.class));

            }
        });

        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager1 = new GridLayoutManager(this, 3);

        recyclerView2=(RecyclerView)findViewById(R.id.recyclerview2);
        layoutManager2 = new GridLayoutManager(this, 3);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        fetchInfo_annonce();


        try {


            final int counter=100*5000;

            countDownTimer=   new CountDownTimer(counter, 5000) {

                public void onTick(long millisUntilFinished) {
                    // Toast.makeText(MainActivity.this , ""+(millisUntilFinished / 1000),Toast.LENGTH_LONG).show();
                    recyclerView2.smoothScrollToPosition(y);
                    y++;
                    if(y>x){
                        y=0;
                    }
                    //here you can have your logic to set text to edittext

                }

                public void onFinish() {

                }

            }.start();}
        catch (Exception e){}

    }

    public void fetchInfo_annonce(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_annonce>> call = apiinterface.getcontacts_annonce();
        call.enqueue(new Callback<List<contact_annonce>>() {
            @Override
            public void onResponse(Call<List<contact_annonce>> call, Response<List<contact_annonce>> response) {
                try{
                    contactList_annonce = response.body();
                    if(!contactList_annonce.isEmpty()|| contactList_annonce.equals(null)){
                        progressBar.setVisibility(View.GONE);
                        x=contactList_annonce.size();
                        recyclerAdapter_annonce=new RecyclerAdapter_first_annonce(Main_new.this,contactList_annonce,recyclerView2);
                        recyclerView2.setAdapter(recyclerAdapter_annonce);}
                    progressBar.setVisibility(View.GONE);}
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<List<contact_annonce>> call, Throwable t) {

            }
        });
    }
}
