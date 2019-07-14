package com.khalej.magsala.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.magsala.Adapter.fatora_RecyclerAdapter;
import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.Model.orders_realm;
import com.khalej.magsala.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shopping_car extends AppCompatActivity {
    Toolbar toolbar;
    Realm realm;

    EditText name,address,phone;
    fatora_RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    Typeface myTypeface;
    RecyclerView.LayoutManager layoutManager;
    String detail="";
    TextView getfinal,delete;
    String Date;
    private SharedPreferences sharedpref;
    Call<ResponseBody> call = null;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        realm=Realm.getDefaultInstance();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        realm=Realm.getDefaultInstance();
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.review);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.title);
        phone=(EditText)findViewById(R.id.phonee);
        getfinal=(TextView)findViewById(R.id.getfinal);
        delete=(TextView)findViewById(R.id.delete);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        Date =formattedDate;
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        myTypeface = Typeface.createFromAsset(getAssets(), "Droid.ttf");
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
        showdata();
        sharedpref= getSharedPreferences("magsala", Context.MODE_PRIVATE);
        name.setTypeface(myTypeface);
        name.setText(sharedpref.getString("name","").trim());
        address.setText(sharedpref.getString("address","").trim());
        address.setTypeface(myTypeface);
        phone.setTypeface(myTypeface);
        phone.setText(sharedpref.getString("phone","").trim());
        phone.setEnabled(false);
        name.setEnabled(false);
        delete.setTypeface(myTypeface);
        getfinal.setTypeface(myTypeface);
        getfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           fetchdata();
                deletedata();
                getfinal.setClickable(false);
            }

        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedata();
                Toast.makeText(Shopping_car.this, "تم المسح", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Shopping_car.this,MainActivity.class));
                finish();
            }
        });
    }
    public void showdata(){
        realm.beginTransaction();
        RealmResults<orders_realm> content_realms = realm.where(orders_realm.class).findAll();
        if (content_realms.isEmpty() || content_realms.equals(null)) {
            //  Toast.makeText(this, "empty", Toast.LENGTH_LONG).show();
        } else {    // realm.beginTransaction();
            List<orders_realm> result = content_realms;

            recyclerAdapter = new fatora_RecyclerAdapter(Shopping_car.this, result);
            recyclerView.setAdapter(recyclerAdapter);
            for (int i=0;i<result.size();i++){
                detail+=result.get(i).getName()+":"+result.get(i).getDetails()+"\n\n";

            }
        }
        realm.commitTransaction();
    }

    public void deletedata(){
        realm.beginTransaction();
        RealmResults<orders_realm> content_realms=realm.where(orders_realm.class).findAll();
        content_realms.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public void fetchdata(){
        progressDialog = ProgressDialog.show(Shopping_car.this, "جاري تسجيل طلبك", "Please wait...", false, false);
        progressDialog.show();

        Toast.makeText(Shopping_car.this,detail,Toast.LENGTH_LONG).show();
        //  String currentTime = Calendar.getInstance().getTime().toString();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_order(name.getText().toString(),
              address.getText().toString(),phone.getText().toString()
                ,detail,Date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
             // Toast.makeText(Shopping_car.this,details,Toast.LENGTH_LONG).show();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Shopping_car.this);
                dlgAlert.setMessage("تم تسجيل تسجيل طلبك بنجاح ");
                dlgAlert.setTitle("نظرة النظافة");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                //Toast.makeText(get_order.this,"d",Toast.LENGTH_LONG).show();
                getfinal.setClickable(true);
                  finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //   Toast.makeText(get_order.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
}
}
