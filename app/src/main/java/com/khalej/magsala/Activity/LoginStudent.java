package com.khalej.magsala.Activity;

import android.Manifest;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginStudent extends Fragment {
ProgressDialog progressDialog;
    EditText input_name,input_phone,input_address;
     GPsTracker gpsTracker;
    Call<ResponseBody> call = null;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private static final int REQUEST_LOCATION = 1;
    AppCompatButton btn_login;
    Button button;
    TextView textView,textView1;
    LocationManager locationManager;
    String lattitude,longitude;
    String address,city,country,area;
    Geocoder geocoder;
    Handler mHandler;
    List<Address> addresses;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_student, container, false);
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "Droid.ttf", true);
        input_name=view.findViewById(R.id.input_name);
        input_phone=view.findViewById(R.id.input_phone);
        input_address=view.findViewById(R.id.input_address);
        btn_login=view.findViewById(R.id.btn_login);
        sharedpref = getActivity().getSharedPreferences("magsala", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("remember","").trim().equals("yes")){

            startActivity(new Intent(getContext(),Main_new.class));
            getActivity().finish();
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
            }
        });
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


       /* mHandler = new Handler();
            mHandler.postDelayed(m_Runnable,25000);*/

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }


return view;
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                geocoder=new Geocoder(getContext(), Locale.getDefault());
                try {
                    addresses=geocoder.getFromLocation(Double.parseDouble(lattitude),Double.parseDouble(longitude),1);
                    String address = addresses.get(0).getAddressLine(0);
                    String area= addresses.get(0).getLocality();
                    String city = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postal = addresses.get(0).getPostalCode();
                    input_address.setText(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }

             /*   textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);*/
            }else{
                Toast.makeText(getContext(),"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
            startActivity(new Intent( getContext(),LoginActivity.class));
         //   Toast.makeText(getContext(),"in runnable",Toast.LENGTH_SHORT).show();

         mHandler.postDelayed(m_Runnable, 25000);
        }

    };//runnable
    public void fetchInfo() {
        progressDialog = ProgressDialog.show(getContext(), "جاري تسجيل الدخول", "Please wait...", false, false);
        progressDialog.show();




        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontactsadd(input_name.getText().toString(),
                input_phone.getText().toString(),input_address.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
                dlgAlert.setMessage("تم تسجيل الدخول بنجاح ");
                dlgAlert.setTitle("نظرة النظافة");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                edt.putString("name",input_name.getText().toString());
                edt.putString("phone", input_phone.getText().toString());
                edt.putString("address",input_address.getText().toString());

                edt.putString("remember","yes");
                edt.apply();
                getActivity().startActivity(new Intent(getContext(),Main_new.class));
               getActivity().finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}