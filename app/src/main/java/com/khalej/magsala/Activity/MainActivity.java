package com.khalej.magsala.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

public class MainActivity extends AppCompatActivity {
    private RecyclerAdapter_first_annonce recyclerAdapter_annonce;
    private List<content_category> contactList;
    private List<contact_annonce> contactList_annonce;
    ProgressBar progressBar;
    private apiinterface_home apiinterface;
    int x=0;
    Intent intent;
    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager1,layoutManager2;
    CountDownTimer countDownTimer;
ImageView car;
    int y=0;
    private RecyclerAdapter_first recyclerAdapter;


    private static final String EXTRA_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        intent=getIntent();
        car=findViewById(R.id.car);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Shopping_car.class));

            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager1 = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager1);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setHasFixedSize(true);

        layoutManager2 = new GridLayoutManager(this, 2);

        fetchInfo();

        try {


            final int counter=100*5000;

            countDownTimer=   new CountDownTimer(counter, 5000) {

                public void onTick(long millisUntilFinished) {
                    // Toast.makeText(MainActivity.this , ""+(millisUntilFinished / 1000),Toast.LENGTH_LONG).show();
//                    recyclerView2.smoothScrollToPosition(y);
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
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<content_category>> call = apiinterface.getcontacts_allfirst(intent.getStringExtra("type"));
        call.enqueue(new Callback<List<content_category>>() {
            @Override
            public void onResponse(Call<List<content_category>> call, Response<List<content_category>> response) {
                try {
                    contactList = response.body();
                    progressBar.setVisibility(View.GONE);
                    if (!contactList.isEmpty() || contactList.equals(null)) {
                        recyclerAdapter = new RecyclerAdapter_first(MainActivity.this, contactList);
                        recyclerView.setAdapter(recyclerAdapter);
                    }
                }
                catch (Exception e){}

            }

            @Override
            public void onFailure(Call<List<content_category>> call, Throwable t) {

            }
        });
    }

}
