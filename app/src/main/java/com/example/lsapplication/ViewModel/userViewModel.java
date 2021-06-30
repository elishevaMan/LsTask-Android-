package com.example.lsapplication.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.lsapplication.model.EmployeeModel;
import com.example.lsapplication.server.UserRepository;


import java.util.List;

public class userViewModel extends  BaseViewModel{
    MutableLiveData<String> response;
    public userViewModel() {
        response= new MutableLiveData<>();
    }
    public MutableLiveData<String> getResult()
    {
        return response;
    }


    public void login(String email, String password)
    {
        response= UserRepository.getInstance().login(email, password);
    }
    public void register(String firstName, String lastName, String email, String password, String passwordRetype)
    {
        response= UserRepository.getInstance().register(firstName,lastName, email, password, passwordRetype );
    }
}

