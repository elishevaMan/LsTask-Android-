package com.example.lsapplication.helper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lsapplication.App;
import com.example.lsapplication.R;

public final class AndroidUtils {
    public static String getDeviceId()
    {
        return Settings.Secure.getString(App.appContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
    /**
     * for example "Android 4.0.4 SDKv17"
     *
     * @return
     */
    public static String getOperatingSystem()
    {
        return "Android " + Build.VERSION.RELEASE + " SDKv" + Build.VERSION.SDK_INT;
    }

    /**
     * for example "Samsung GT-I9100"
     *
     * @return make and model
     */
    public static String getDeviceTypeName()
    {
        String make = Build.MANUFACTURER.toUpperCase();
        String model = Build.MODEL.equalsIgnoreCase(Build.PRODUCT) ? Build.MODEL : Build.MODEL + " " + Build.PRODUCT;
        return make + " " + model;
    }

    /**
     * @param permission
     *            as defined in android.Manifest.permission.*
     * @return true if granted
     */
    public static boolean checkPermission(final String permission)
    {
        return App.app.getPackageManager().checkPermission(permission, App.app.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }
    /**
     * @return The applications name (As seen by the user)
     */
    public static String getAppName()
    {
        try
        {
            return App.app.getPackageManager().getApplicationLabel(App.app.getApplicationInfo()).toString();
        } catch (Exception e)
        {
            return "Unknown";
        }
    }

    public static void startTwitterTweet(final AppCompatActivity context, final int requestCode, final String tweet, final String url)
    {
        AndroidUtils.startActionView(context, requestCode, "https://www.twitter.com/intent/tweet?url=" + url + "&text=" + tweet, null);
    }

    public static void startActionView(final AppCompatActivity context, final int requestCode, final String dataToParse, final String title)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        try
        {
            intent.setData(Uri.parse(dataToParse));
            if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0)
                context.startActivityForResult(Intent.createChooser(intent, title), requestCode);
            else
                throw new ActivityNotFoundException();
        } catch (Exception e)
        {
            // Constant.toast(context.getString(R.string.no_matching_app_found_));
            Toast.makeText(context, R.string.no_matching_app_found_, Toast.LENGTH_SHORT).show();
        }
    }

    public static void startWhatsappShare(final AppCompatActivity act, final int requestCode, final String subject,final String text,final Uri uri)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            //
            if(uri!=null){
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
            }
            else
                intent.setType("text/plain");
            intent.setPackage("com.whatsapp");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.putExtra(Intent.EXTRA_SUBJECT,subject);


            act.startActivityForResult(intent, requestCode);
        } catch (Exception e)
        {
            AndroidUtils.startGooglePlay(act, requestCode, "com.whatsapp");
        }
    }
    public static void startGooglePlay(final AppCompatActivity context, final int requestCode)
    {
        AndroidUtils.startGooglePlay(context, requestCode, context.getPackageName());
    }

    /**
     * Automatically starts Google Play with the packagename's app page opened
     *
     * @param context
     * @param packageName
     *            The packageName of the com.appgate.nahagtoran.App's page to show
     */
    public static void startGooglePlay(final AppCompatActivity context, final int requestCode, final String packageName)
    {
        AndroidUtils.startActivityChooser(context,
                requestCode,
                "market://details?id=" + packageName,
                null,
                "http://play.google.com/store/apps/details?id=" + packageName,
                null);
    }
    public static void startActivityChooser(final AppCompatActivity context,
                                            final int requestCode,
                                            final String dataToParse,
                                            final String title,
                                            final String ifErrorUrl,
                                            final String ifErrorTitle)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        try
        {
            intent.setData(Uri.parse(dataToParse));
        } catch (Exception e)
        {}
        AndroidUtils.startActivityChooser(context, requestCode, intent, title, ifErrorUrl, ifErrorTitle);
    }
    public static void startActivityChooser(final AppCompatActivity context,
                                            final int requestCode,
                                            final Intent intent,
                                            final String title,
                                            final String ifErrorUrl,
                                            final String ifErrorTitle)
    {
        try
        {
            if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0)
                context.startActivityForResult(Intent.createChooser(intent, title), requestCode);
            else
                throw new ActivityNotFoundException();
        } catch (Exception e)
        {
            AndroidUtils.startActionView(context, requestCode, ifErrorUrl, ifErrorTitle);
        }
    }
}

