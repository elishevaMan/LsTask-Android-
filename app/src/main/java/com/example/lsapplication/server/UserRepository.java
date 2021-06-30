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

public class UserRepository {
    UserApiService service;
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        service = NetworkClient.getInstance().getUserApiService();
    }

    public MutableLiveData<String> login(String email, String password) {
        MutableLiveData<String> result = new MutableLiveData<>();
        Call<String> call = service.login(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    result.postValue(response.body());
                } else {
                    result.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                result.postValue(null);
            }
        });
        return result;
    }
    public MutableLiveData<String> register(String firstName, String lastName, String email, String password, String passwordRetype) {
        MutableLiveData<String> result = new MutableLiveData<>();
        Call<String> call = service.register(firstName, lastName, email, password, passwordRetype);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    result.postValue(response.body());
                } else {
                    result.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                result.postValue(null);
            }
        });
        return result;
    }

}
