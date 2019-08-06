package com.khalej.magsala;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class application extends MultiDexApplication {
    @Override
    public void onCreate() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        super.onCreate();
    }

}
