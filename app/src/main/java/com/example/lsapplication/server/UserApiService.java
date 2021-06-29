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

 /*   @GET(employees+"/getAll")
    @FormUrlEncoded
    Observable<List<EmployeeModel>> getEmployeeList();*/
   // Call<List<EmployeeModel>> getEmployeeList();

    @POST("users")
    @FormUrlEncoded
    Observable<String> register(@Field("firstName") String firstName,
                                @Field("lastName") String lastName,
                                @Field("email") String email,
                                @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Observable<String> login(@Field("email") String email,
                                @Field("password") String password);
}
