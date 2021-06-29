package com.example.lsapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App extends Application implements  Application.ActivityLifecycleCallbacks {

    public static final String LOG_TAG = "LsApplication";


 //   public static final String SECRET_KEY = "DermaBoxFMRPjWqY8z8eadlWPTUwb7AFUZjWqP";
    private static Application sApplication;
    public static int screenWidth, screenHeight,screenDensity;
    public static Context appContext;
    public static App app;
    static final int NumMinutesForTitle = 15;
    public static android.graphics.Typeface typeRegular, typeBold, typeLight;
    public static SharedPreferences sharedPreferences;
    public static boolean sendToCamera = false;
    public static final int REFRESH_DATA = 999;
    private static AppCompatActivity topActivity;





    public static Application getApplication() {
        return sApplication;
    }

    public static final String operatingSystemType = "1";
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    //    sApplication = this;
        app = this;
        appContext = getApplicationContext();
        App.screenHeight = getResources().getDisplayMetrics().heightPixels;
        App.screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenDensity = getResources().getDisplayMetrics().densityDpi;
        registerActivityLifecycleCallbacks(this);
        appContext = getApplicationContext();

        //  App.sharedPreferences = getSharedPreferences("com.appgate.dermabox"/*getPackageName()*/, 0);
        Picasso.get().setLoggingEnabled(true);


    }


    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof AppCompatActivity)
            topActivity = ((AppCompatActivity) activity);

    }

    public static AppCompatActivity getTopActivity() {
        return topActivity;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {

        if (activity instanceof AppCompatActivity)
            topActivity = ((AppCompatActivity) activity);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

//    @Override
//    public void onActivityResumed(@NonNull Activity activity) {
//
//    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}

