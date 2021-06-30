package com.example.lsapplication.server;

import androidx.lifecycle.MutableLiveData;

import com.example.lsapplication.model.EmployeeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {
    EmployeeApiService service;
    private static EmployeeRepository instance;

    public static EmployeeRepository getInstance() {
        if (instance == null) {
            instance = new EmployeeRepository();
        }
        return instance;
    }

    private EmployeeRepository() {
        service = NetworkClient.getInstance().getEmployeeApiService();
    }

    public MutableLiveData<List<EmployeeModel>> getEmployeeList() {
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
    }

    public MutableLiveData<String> add(EmployeeModel employeeModel) {
        MutableLiveData<String> result = new MutableLiveData<>();
        Call<String> call = service.add(employeeModel.getFirstName(), employeeModel.getLastName(), employeeModel.getPhone(), employeeModel.getAddress(), employeeModel.getRole());
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

    public MutableLiveData<String> edit(EmployeeModel employeeModel) {
        MutableLiveData<String> result = new MutableLiveData<>();
        Call<String> call = service.edit(employeeModel.getFirstName(), employeeModel.getLastName(), employeeModel.getPhone(), employeeModel.getAddress(), employeeModel.getRole());
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

    public MutableLiveData<String> delete(String likeID) {
        MutableLiveData<String> result = new MutableLiveData<>();
        Call<String> call = service.delete(likeID);
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
