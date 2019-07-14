package com.khalej.magsala.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.khalej.magsala.Model.orders_realm;
import com.khalej.magsala.R;

import io.realm.Realm;
import me.anwarshahriar.calligrapher.Calligrapher;

public class Order_Details extends AppCompatActivity {
Intent intent;
int id;
String name,imageLink;
    ImageView image;
    TextView name_,price_;
    ImageView car;
    AppCompatButton add;
    Realm realm;
    EditText details;
    Toolbar toolbar;
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        image=findViewById(R.id.image);
        name_=findViewById(R.id.name);
        price_=findViewById(R.id.price);
        details=findViewById(R.id.details);
        add=findViewById(R.id.btn_login);
        this.setTitle("");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
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
        car=findViewById(R.id.car);
        realm=Realm.getDefaultInstance();
        intent=getIntent();
        name=intent.getStringExtra("name");
       id=intent.getIntExtra("id",0);
        price=intent.getIntExtra("price",0);
        imageLink=intent.getStringExtra("image");
        Glide.with(this).load(imageLink).error(R.drawable.circlelogo).into(image);
        name_.setText(name);
        price_.setText(price+"");
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Order_Details.this,Shopping_car.class));
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteTodatabase();
            }
        });

    }
    public void WriteTodatabase(){
//             realm.delete(subject_content_realm.class);
        // Create a new object

        realm.beginTransaction();
        orders_realm fatora = realm.createObject(orders_realm.class);
        fatora.setDetails(details.getText().toString());
        fatora.setName(name);
        fatora.setId(id);
        realm.commitTransaction();
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Order_Details.this);
        dlgAlert.setMessage("تم أضافة طلبك الى سلة الطلبات  ");
        dlgAlert.setTitle("نظرة النظافة");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        finish();
    }

}
