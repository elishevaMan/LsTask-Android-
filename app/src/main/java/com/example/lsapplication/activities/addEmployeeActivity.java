package com.example.lsapplication.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsapplication.R;
import com.example.lsapplication.databinding.ActivityAddEmployeeBinding;
import com.example.lsapplication.helper.ImageCapture;
import com.example.lsapplication.model.EmployeeModel;
import com.example.lsapplication.popUp.EditEmployeePopUp;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class addEmployeeActivity extends AppCompatActivity {
    private static final int IMAGE_CAPTURE =2 ;
    ActivityAddEmployeeBinding binding;
    private static final int ADDRESS = 1;
    ArrayAdapter adapter;
    String[] roleList= new String[]{"role","HR", "ENGENIR"};
    private static final int CAMERA_REQUEST = 1888;
    EmployeeModel employeeModel;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    boolean edit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_add_employee);
        employeeModel=(EmployeeModel) getIntent().getSerializableExtra("employee");
        if(employeeModel!=null)
        {
            edit= true;
            Picasso.get().load(employeeModel.getImage()).fit().into(binding.profileImg);
        }
        else
            employeeModel= new EmployeeModel();
        initSpinner();
        binding.setEmployee(employeeModel);
        binding.addImage.setOnClickListener(v -> {
            openCamera();
        });
    }

    public void openCamera() {
       /* startActivityForResult(new Intent(this, ImageCapture.class)
                        .putExtra(ImageCapture.REQUIRED_WIDTH, 400)
                        .putExtra(ImageCapture.REQUIRED_HEIGHT, 400),
                IMAGE_CAPTURE);*/

        openSomeActivityForResult();
    }

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Picasso.get().load(data.getStringExtra("url")).fit().into(binding.profileImg);
                            employeeModel.setImage(data.getStringExtra("url"));

                        }
                    }
                });

        public void openSomeActivityForResult() {
          /*  Intent intent = new Intent(this, ImageCapture.class);
            intent.putExtra(ImageCapture.REQUIRED_WIDTH, 400);
            intent.putExtra(ImageCapture.REQUIRED_HEIGHT, 400);
            someActivityResultLauncher.launch(intent);*/
           /* *//*if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }*//*
            else
            {*/
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
          //  }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void initSpinner() {
        adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, roleList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        binding.roleSpinner.setAdapter(adapter);
        binding.roleSpinner.setOnItemSelectedListener(new
                                                                     AdapterView.OnItemSelectedListener() {
                                                                         @Override
                                                                         public void onItemSelected(AdapterView<?> parent, View view, int
                                                                                 position, long id) {
                                                                             String data= parent.getItemAtPosition(position).toString();
                                                                             TextView selectedText = (TextView) parent.getChildAt(0);
                                                                             selectedText.setText("");
                                                                             if(position==0 &&edit)
                                                                             {
                                                                                 binding.roleText.setText(employeeModel.getRole());
                                                                             }
                                                                             else
                                                                             binding.roleText.setText(data);

                                                                         }

                                                                         @Override
                                                                         public void onNothingSelected(AdapterView<?> parent) {
                                                                         }
                                                                     });
    }

    public void OpenGooglePlace(View view) {
            binding.phoneTxt.setFocusable(false);
        try {
            startActivityForResult(new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this), ADDRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS && resultCode == RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(this, data);
           // isAddressValid = true;
            binding.roleText.requestFocus();
            binding.addressTxt.setText(place.getAddress().toString());
        }
       /* if (requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Picasso.get().load(data.getStringExtra("url")).fit().into(binding.profileImg);
        *//*    viewModel.getCustomerDetails().setFileImage((File) data.getSerializableExtra("file"));
            viewModel.getCustomerDetails().setImageFile(data.getStringExtra("url"));
            change=true;*//*
        }*/

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri uri= getImageUri(addEmployeeActivity.this, photo);
            employeeModel.setImage(uri.toString());
            binding.profileImg.setImageBitmap(photo);
        }
    }

    private Uri getImageUri(AppCompatActivity context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void save(View view) {
        if(isValidFirstName(binding.firstNameTxt.getText().toString()) && isValidLastName(binding.lastNameTxt.getText().toString()) && isValidPhone(binding.phoneTxt.getText().toString()) &&  isValidAddress(binding.addressTxt.getText().toString()) && isValidRole(binding.roleText.getText().toString())) {
            binding.btnAdd.setBackgroundResource(R.drawable.dark_blue_btn);
            Intent intent= new Intent();
            intent.putExtra("employee", employeeModel);
            if(edit)
            {
                intent.putExtra("position", getIntent().getIntExtra("position", 0));
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    private boolean isValidPhone(CharSequence target) {
        if (!(!TextUtils.isEmpty(target) && target.length()>=9))
        {
            binding.validFirstName.setVisibility(View.VISIBLE);
            return  false;
        }
        binding.validFirstName.setVisibility(View.INVISIBLE);
        return true;
    }
    private boolean isValidAddress(CharSequence target) {
        if (!(!TextUtils.isEmpty(target)))
        {
            binding.validAddress.setVisibility(View.VISIBLE);
            return  false;
        }
        binding.validAddress.setVisibility(View.INVISIBLE);
        return true;
    }
    private boolean isValidRole(CharSequence target) {
        if (!(!TextUtils.isEmpty(target)))
        {
            binding.roleText.setVisibility(View.VISIBLE);
            return  false;
        }
        binding.roleText.setVisibility(View.INVISIBLE);
        return true;
    }

    public  boolean isValidFirstName(CharSequence target) {
        if (!(!TextUtils.isEmpty(target) && target.length()>=2))
        {
            binding.validFirstName.setVisibility(View.VISIBLE);
            isValidLastName(binding.lastNameTxt.getText().toString());
            return  false;
        }
        binding.validFirstName.setVisibility(View.INVISIBLE);
        return true;
    }
    public  boolean isValidLastName(CharSequence target) {
        if (!(!TextUtils.isEmpty(target) && target.length()>=2))
        {
            binding.validLastName.setVisibility(View.VISIBLE);
            isValidPhone(binding.phoneTxt.getText().toString());
            return  false;
        }
        binding.validLastName.setVisibility(View.INVISIBLE);
        return true;
    }
}