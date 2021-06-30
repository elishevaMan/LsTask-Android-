package com.example.lsapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lsapplication.R;
import com.example.lsapplication.ViewModel.EmployeeViewModel;
import com.example.lsapplication.adapter.EmployeesAdapter;
import com.example.lsapplication.databinding.ActivityEmployeeBinding;
import com.example.lsapplication.helper.MyLinearLayoutManager;
import com.example.lsapplication.model.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

public class employeeActivity extends AppCompatActivity {
    private static final int ACTION = 1;
    private static final int ADD = 3;
    ActivityEmployeeBinding binding;
    private EmployeesAdapter employeesAdapter;
    List<EmployeeModel> employees= new ArrayList<>();
    private EmployeeViewModel employeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_employee);
        initEmployeesList();
    }

    private void initEmployeesList() {
        employeeViewModel= ViewModelProviders.of(this).get(EmployeeViewModel.class);
        /*employeeViewModel.getEmployeeList();
        employeeViewModel.getEmployeeListResult().observe(this, result->onGetEmployeeList(result));*/

        employees.add(new EmployeeModel("יעל", "כהן", "0533114372", "הארבעה 12","15 פאבואר 2016", "HR", "http://app.dermabox.co.il/files/customersImage/d89f3a35931c386956c1a402a8e09941_30062021164156.jpg",false));
        employees.add(new EmployeeModel("אהרון", "פרוידיגר", "0533114372", "הארבעה 12","14 יוני 2014", "QA", "http://app.dermabox.co.il/files/customersImage/d89f3a35931c386956c1a402a8e09941_30062021164156.jpg", false));
        employees.add(new EmployeeModel("משה", "לוין", "0533114372", "הארבעה 12","14 אוגוסט 2013", "Developer", "http://app.dermabox.co.il/files/customersImage/d89f3a35931c386956c1a402a8e09941_30062021164156.jpg", false));
        employeesAdapter = new EmployeesAdapter(this, employees);
        binding.employeesList.setLayoutManager(new MyLinearLayoutManager(this,1,false));
        binding.employeesList.setAdapter(employeesAdapter);
    }

    private void onGetEmployeeList(List<EmployeeModel> result) {
        if(result!=null && result.size()>0)
        {
            employees=result;
            employeesAdapter = new EmployeesAdapter(this, employees);
            binding.employeesList.setLayoutManager(new MyLinearLayoutManager(this,1,false));
            binding.employeesList.setAdapter(employeesAdapter);
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION && resultCode == RESULT_OK) {
            if(data!=null && data.getStringExtra("action").equals("delete"))
            {
                int position = data.getIntExtra("position", 0);
               /* employeeViewModel.deleteEmployee(employees.get(position).getFirstName(), employees.get(position).getLastName(), employees.get(position).getPhone());
                employeeViewModel.getResult().observe(this, result-> onGetResult(result, position));*/
                employees.remove(position);
                Toast.makeText(this, R.string.the_employee_delte_in_sucsess, Toast.LENGTH_SHORT).show();
                employeesAdapter.notifyDataSetChanged();
            }
            if(data!=null && data.getStringExtra("action").equals("edit"))
            {
                employees.set(data.getIntExtra("position", 0), (EmployeeModel)data.getSerializableExtra("employee"));
                Toast.makeText(this, R.string.the_employee_edit_in_sucsess, Toast.LENGTH_SHORT).show();
                employeesAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == ADD && resultCode == RESULT_OK) {
            if(data!=null && data.getSerializableExtra("employee")!=null)
            {
                employees.add(0,(EmployeeModel) data.getSerializableExtra("employee"));
                Toast.makeText(this, R.string.the_employee_add_in_sucsess, Toast.LENGTH_SHORT).show();
                employeesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void onGetResult(String result, int position) {
        if(result.equals("ok"))
        {
            employees.remove(position);
            Toast.makeText(this, R.string.the_employee_delte_in_sucsess, Toast.LENGTH_SHORT).show();
            employeesAdapter.notifyDataSetChanged();
        }
    }

    public void addEmployee(View view) {
        Intent intent= new Intent(this, addEmployeeActivity.class);
        startActivityForResult(intent, ADD);
    }
}