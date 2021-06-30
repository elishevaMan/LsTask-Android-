package com.example.lsapplication.server;

import com.example.lsapplication.model.EmployeeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import static com.example.lsapplication.server.NetworkClient.User;
import static com.example.lsapplication.server.NetworkClient.employees;

public interface EmployeeApiService {
    @GET(employees+"getAll")
    Call<List<EmployeeModel>> getEmployeeList();

    @POST(employees+"insert")
    Call<String> add(@Field("firstName") String firstName,
                     @Field("lastName") String password,
                     @Field("phone") String phone,
                     @Field("address") String address,
                     @Field("roll") String roll
                     );

    @PUT(employees+"edit")
    Call<String> edit(@Field("firstName") String firstName,
                     @Field("lastName") String password,
                     @Field("phone") String phone,
                     @Field("address") String address,
                     @Field("roll") String roll
                     );

    @DELETE(employees+"delete")
    Call<String> delete(@Field("id") String likeId
                     );

}
