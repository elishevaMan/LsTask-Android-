package com.example.lsapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lsapplication.R;
import com.example.lsapplication.databinding.EmployeeRowBinding;
import com.example.lsapplication.model.EmployeeModel;
import com.example.lsapplication.popUp.EditEmployeePopUp;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.ViewHolder> {
    private static final int ACTION = 1;
    List<EmployeeModel> objects;
    Context context;
    RelativeLayout.LayoutParams layoutParams2;




    public EmployeesAdapter(Context context, List<EmployeeModel> objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public EmployeesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EmployeeRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.employee_row, parent, false);
        return new EmployeesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesAdapter.ViewHolder holder, int position) {
        EmployeeModel item = objects.get(position);
        holder.bind(item, position);
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final EmployeeRowBinding binding;

        public ViewHolder(EmployeeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EmployeeModel obj, int position) {
            binding.setEmployee(obj);
            binding.employeeName.setText(obj.getFirstName()+" "+obj.getLastName());
            if(obj.getImage()!=null && !obj.getImage().equals(""))
            {
                if(obj.getChange())
                {
                    Picasso.get().load(obj.getImage()).into(binding.img);
                }
                else
                Picasso.get().load(obj.getImage()).rotate(90).into(binding.img);
            }
            binding.editEmployeeBig.setOnClickListener(v -> {
                Intent intent= new Intent(context, EditEmployeePopUp.class);
                intent.putExtra("position", position);
                intent.putExtra("employee", obj);
                ((Activity)context).startActivityForResult(intent, ACTION);
            });
            binding.executePendingBindings();
        }
    }
}


