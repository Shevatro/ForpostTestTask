package ua.forposttest;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

// @author Sergio Shevatro
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
    }

    public static App getAppContext() {
        return instance;
    }
}