package com.example.lsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.lsapplication.activities.signInActivity;
import com.example.lsapplication.server.RetropitClient;
import com.example.lsapplication.server.UserApiService;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init service

        startActivity(new Intent(MainActivity.this, signInActivity.class));
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setMessage(this.getString(R.string.do_you_want_exit_from_application));
        alertBuilder.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                finish();
            }
        });
        alertBuilder.setNegativeButton(R.string.cancel, null);
        alertBuilder.show();
    }

}