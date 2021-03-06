package com.example.lsapplication.server;

import com.example.lsapplication.model.EmployeeModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;


import static com.example.lsapplication.server.NetworkClient.User;
import static com.example.lsapplication.server.NetworkClient.employees;

public interface UserApiService {

    @POST(User+"register")
    Call<String> register(@Field("firstName") String firstName,
                                @Field("lastName") String lastName,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("passwordRetype") String passwordRetype);

    @POST(User+"login")
    Call<String> login(@Field("email") String email,
                       @Field("password") String password);
}
