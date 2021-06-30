package com.example.lsapplication.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.lsapplication.model.EmployeeModel;
import com.example.lsapplication.server.EmployeeRepository;
import com.example.lsapplication.server.UserRepository;

import java.util.List;

public class EmployeeViewModel extends  BaseViewModel{
    MutableLiveData<List<EmployeeModel>> employeeList;
    MutableLiveData<String> response;

    public EmployeeViewModel() {
        employeeList= new MutableLiveData<>();
        response= new MutableLiveData<>();
    }
    public MutableLiveData<List<EmployeeModel>> getEmployeeListResult()
    {
        return employeeList;
    }

    public void getEmployeeList()
    {
        employeeList = EmployeeRepository.getInstance().getEmployeeList();
    }

    public MutableLiveData<String> getResult()
    {
        return response;
    }


    public void addEmployee(EmployeeModel employeeModel)
    {
        response= EmployeeRepository.getInstance().add(employeeModel);
    }
    public void editEmployee(EmployeeModel employeeModel)
    {
        response= EmployeeRepository.getInstance().edit(employeeModel);
    }
    public void deleteEmployee(String firstName, String lastName, String phone)
    {
        response= EmployeeRepository.getInstance().delete(firstName+lastName+phone);
    }

}
