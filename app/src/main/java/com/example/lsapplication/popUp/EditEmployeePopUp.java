package com.example.lsapplication.popUp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lsapplication.R;
import com.example.lsapplication.activities.addEmployeeActivity;
import com.example.lsapplication.databinding.PopUpEditEmployeeBinding;
import com.example.lsapplication.model.EmployeeModel;

public class EditEmployeePopUp extends AppCompatActivity {
    private static final int EDIT = 3;
    PopUpEditEmployeeBinding binding;
    int employeePosition;
    EmployeeModel employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.pop_up_edit_employee);
        employeePosition= getIntent().getIntExtra("position", 0);
        employee=(EmployeeModel) getIntent().getSerializableExtra("employee");
    }

    public void editEmployee(View view) {
        Intent intent= new Intent(this, addEmployeeActivity.class);
        intent.putExtra("action", view.getTag().toString());
        intent.putExtra("position",employeePosition);
        intent.putExtra("employee",employee);
        startActivityForResult(intent, EDIT);
    }

    public void deleteEmployee(View view) {
        Intent intent= new Intent();
        intent.putExtra("action", view.getTag().toString());
        intent.putExtra("position",employeePosition);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra("employee", (EmployeeModel)data.getSerializableExtra("employee"));
            intent.putExtra("position", data.getIntExtra("position",0));
            intent.putExtra("action","edit");
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}