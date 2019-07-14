package com.khalej.magsala.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.khalej.magsala.Adapter.RecyclerAdapter_first;
import com.khalej.magsala.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.Model.contact_annonce;
import com.khalej.magsala.Model.content_category;
import com.khalej.magsala.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Mwasel extends AppCompatActivity {
    private RecyclerAdapter_first_annonce recyclerAdapter_annonce;
    private List<content_category> contactList;
    private List<contact_annonce> contactList_annonce;
    ProgressBar progressBar;
    Button new_order,cuurent_order,old_order;
    private static final int REQUEST_LOCATION = 1;

    private apiinterface_home apiinterface;
    int x=0;
    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager1,layoutManager2;
    CountDownTimer countDownTimer;
    ImageView car;
    int y=0;
    private RecyclerAdapter_first recyclerAdapter;

    LocationManager locationManager;
    String lattitude="",longitude="";
    String address,city,country,area;
    Geocoder geocoder;
    Handler mHandler;
    List<Address> addresses;
    private static final String EXTRA_TEXT = "text";
String Date;
int id;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mwasel);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        sharedpref = getSharedPreferences("magsala", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        id=sharedpref.getInt("id",0);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        Date =formattedDate;
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);

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
        new_order=findViewById(R.id.new_order);
        cuurent_order=findViewById(R.id.current_order);
        old_order=findViewById(R.id.old_order);

        new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(Main_Mwasel.this,New_Orders.class));
            }
        });
        cuurent_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Main_Mwasel.this,My_current_order.class));
            }
        });
        old_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Main_Mwasel.this,My_oldorder.class));
            }
        });
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
                        recyclerAdapter_annonce=new RecyclerAdapter_first_annonce(Main_Mwasel.this,contactList_annonce,recyclerView2);
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
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Main_Mwasel.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Main_Mwasel.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Main_Mwasel.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                geocoder=new Geocoder(Main_Mwasel.this, Locale.getDefault());

                try {
                    addresses=geocoder.getFromLocation(Double.parseDouble(lattitude),Double.parseDouble(longitude),1);
                    String address = addresses.get(0).getAddressLine(0);
                    String area= addresses.get(0).getLocality();
                    String city = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postal = addresses.get(0).getPostalCode();
                    fetchInfo();

                } catch (IOException e) {
                    e.printStackTrace();
                }

             /*   textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);*/
            }else{
                Toast.makeText(Main_Mwasel.this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Mwasel.this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Adapter a = null;
            startActivity(new Intent( Main_Mwasel.this,LoginActivity.class));
            //   Toast.makeText(getContext(),"in runnable",Toast.LENGTH_SHORT).show();

            mHandler.postDelayed(m_Runnable, 25000);
        }

    };//runnable
    public void fetchInfo() {
         apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = null;
        call=apiinterface.update_location(id,longitude,lattitude,Date);



        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }
}
