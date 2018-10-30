package ua.forposttest;

import android.app.Application;

// @author Sergio Shevatro
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getAppContext() {
        return instance;
    }
}