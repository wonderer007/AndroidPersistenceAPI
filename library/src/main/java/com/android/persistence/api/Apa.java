package com.android.persistence.api;

import android.app.Application;

public class Apa extends Application{

    private static Apa instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Apa getAppContext() {
        return instance;
    }
}
