package com.khalej.magsala.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.khalej.magsala.Model.Apiclient_home;
import com.khalej.magsala.Model.apiinterface_home;
import com.khalej.magsala.Model.user_content;
import com.khalej.magsala.R;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginTeacher extends Fragment {

    AppCompatButton btn_login;
    TextInputEditText name,phone;
    private List<user_content> contactList;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ProgressDialog progressDialog;
    EditText inpunt_num,input_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_teacher, container, false);
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "Droid.ttf", true);
        inpunt_num=view.findViewById(R.id.input_num);
        input_password=view.findViewById(R.id.input_password);
        btn_login=view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
            }
        });


        sharedpref = getActivity().getSharedPreferences("magsala", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("remember","").trim().equals("yesM")){

            startActivity(new Intent(getContext(),Main_Mwasel.class));
            getActivity().finish();
        }

        return view;
    }

public void fetchInfo(){
    progressDialog = ProgressDialog.show(getContext(),"جاري تسجيل الدخول","Please wait...",false,false);
    progressDialog.show();

    apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
    Call<List<user_content>> call= apiinterface.getcontacts_login(inpunt_num.getText().toString(),
            input_password.getText().toString());
    call.enqueue(new Callback<List<user_content>>() {
        @Override
        public void onResponse(Call<List<user_content>> call, Response<List<user_content>> response) {
            try{
                if(response.isSuccessful()){

                    contactList = response.body();

                    progressDialog.dismiss();
                    edt.putInt("id",contactList.get(0).getId());
                    edt.putString("name",contactList.get(0).getName());

                    edt.putString("phone",contactList.get(0).getPhone());

                    edt.putString("password",contactList.get(0).getPassword());
                     edt.putString("remember","yesM");
                    edt.apply();
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
                    dlgAlert.setMessage("تم تسجيل الدخول بنجاح");
                    dlgAlert.setTitle("الدفتر الألكترونى");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    getActivity().startActivity(new Intent(getContext(),Main_Mwasel.class));
                    getActivity().finish();
                }}
            catch (Exception e){
                Toast.makeText(getContext(),"هناك خطأ فى الهاتف او الرقم السري  ",Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public void onFailure(Call<List<user_content>> call, Throwable t) {
            Toast.makeText(getContext(),"هناك خطأ فى الهاتف او الرقم السري   ",Toast.LENGTH_LONG).show();

            progressDialog.dismiss();
        }
    });
}}