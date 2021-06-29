package com.example.lsapplication.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.lsapplication.model.EmployeeModel;
import com.example.lsapplication.server.UserRepository;

import java.util.List;

public class EmployeeViewModel extends  BaseViewModel{
    MutableLiveData<List<EmployeeModel>> employeeList;
    public EmployeeViewModel() {
        employeeList= new MutableLiveData<>();
    }
    public MutableLiveData<List<EmployeeModel>> getEmployeeListResult()
    {
        return employeeList;
    }

    public void getEmployeeList()
    {
       // employeeList = UserRepository.getInstance().getEmployeeList();
    }
}
