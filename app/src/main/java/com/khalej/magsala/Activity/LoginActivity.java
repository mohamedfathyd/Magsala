package com.khalej.magsala.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.khalej.magsala.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LoginActivity extends AppCompatActivity {
    Button student,teacher;
    TextView tit;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        sharedpref = getSharedPreferences("magsala", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("remember","").trim().equals("yesM")){

            startActivity(new Intent(LoginActivity.this,Main_Mwasel.class));
            finish();
        }
        if(sharedpref.getString("remember","").trim().equals("yes")){

            startActivity(new Intent(LoginActivity.this,Main_new.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        tit=(TextView)findViewById(R.id.tit);
        final LoginStudent loginStudent = new LoginStudent();
        tit.setText("مستخدم");
        // Add Fragment to FrameLayout (flContainer), using FragmentManager
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        ft.add(R.id.frame, loginStudent);                                // add    Fragment
        ft.commit();
        student = (Button) findViewById(R.id.student);
        teacher = (Button) findViewById(R.id.teacher);
        // student.setBackgroundColor(R.color.colorPrimary);
        // teacher.setBackgroundColor(R.color.brown);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginStudent loginStudent = new LoginStudent();
                LoginTeacher loginTeacher = new LoginTeacher();
                getSupportFragmentManager().beginTransaction().remove(loginStudent).commit();
                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
                ft.replace(R.id.frame, loginStudent);
                ft.remove(loginTeacher);// add    Fragment
                ft.commit();
                tit.setText("مستخدم");
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginStudent loginStudent = new LoginStudent();
                LoginTeacher loginTeacher = new LoginTeacher();
                getSupportFragmentManager().beginTransaction().remove(loginStudent).commit();

                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
                ft.replace(R.id.frame, loginTeacher);

               // ft.remove(loginStudent);
                // add    Fragment
                ft.commit();
                tit.setText("موصل");
            }
        });
    }
}
