package ru.geekbrains.noteapphomework.data;

import android.app.Application;

public class PreferencesApplication extends Application {
    private static PreferencesApplication instance;
    public static PreferencesApplication getInstance(){
      return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
