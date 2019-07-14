package com.khalej.magsala.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.khalej.magsala.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
    }
}
