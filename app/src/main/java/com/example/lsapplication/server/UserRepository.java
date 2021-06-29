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

    /*public MutableLiveData<List<EmployeeModel>> getEmployeeList() {
        MutableLiveData<List<EmployeeModel>> result = new MutableLiveData<>();
        Call<List<EmployeeModel>> call = service.getEmployeeList();
        call.enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.body() != null) {
                    result.postValue(response.body());
                } else {
                    result.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                result.postValue(null);
            }
        });
        return result;
    }*/

}
