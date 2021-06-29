package com.example.lsapplication.server;

import androidx.lifecycle.MutableLiveData;

import com.example.lsapplication.model.EmployeeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetropitClient {

    public static final String BASE_URL =  "http://localhost:3020/";

    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null)
            // instance = new UserRepository();
            instance = new Retrofit.Builder()
                    .baseUrl(/*"http://10.0.2.2:3000/"*/BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return instance;
    }
    }
