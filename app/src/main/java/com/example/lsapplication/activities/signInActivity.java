package com.example.lsapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lsapplication.R;
import com.example.lsapplication.databinding.ActivitySignInBinding;
import com.example.lsapplication.server.RetropitClient;
import com.example.lsapplication.server.UserApiService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class signInActivity extends AppCompatActivity implements Validator.ValidationListener {
         ActivitySignInBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserApiService userApiService;
    Validator validator;
    @NotEmpty
    @Email
    private EditText emailEditText ;

   /* @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText passwordEditText;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        Retrofit retropitClient = RetropitClient.getInstance(); //connect to mode
        userApiService = retropitClient.create(UserApiService.class); //connect to mode
        emailEditText = binding.emailTxt;
        validator = new Validator(this);
        validator.setValidationListener(this);
        initView();
    }
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void initView() {
      /*  binding.passwordTxt.addTextChangedListener (new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!binding.emailTxt.getText().toString().equals(""))
                    binding.btnSignin.setBackgroundResource(R.drawable.dark_blue_btn);
            }
        });
*/
        binding.btnSignin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

    }

   /* public void signIn(View view) {
      //  signInInServer(); try sent to server
      *//*  if((isValidEmail(binding.emailTxt.getText().toString()) && isValidPassword(binding.passwordTxt.getText().toString())))
        {
            binding.btnSignin.setBackgroundResource(R.drawable.dark_blue_btn);
            startActivity(new Intent(this, employeeActivity.class));
            finish();
        }*//*


        validator.validate();
    }*/

    public void ClickSeePassword(View view) {
        binding.passwordTxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        binding.seePassword.setBackgroundResource(R.drawable.not_see_password);

        new Handler().postDelayed(() -> {
            binding.seePassword.setBackgroundResource(R.drawable.see_password);
            binding.passwordTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }, 2000);
    }

    public  boolean isValidEmail(CharSequence target) {
        if (!(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()))
        {
            binding.validEmail.setVisibility(View.VISIBLE);
            isValidPassword(binding.passwordTxt.getText().toString());
              return  false;
        }
        binding.validEmail.setVisibility(View.INVISIBLE);
        return true;
    }
    public  boolean isValidPassword(CharSequence target) {
        if (!(!TextUtils.isEmpty(target) && target.length() >= 6))
        {
            binding.validPassword.setVisibility(View.VISIBLE);
        return  false;
    }
        binding.validPassword.setVisibility(View.INVISIBLE);
        return true;
    }

    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void signInInServer() {
        compositeDisposable.add(userApiService.login(binding.emailTxt.getText().toString(),binding.passwordTxt.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) {
                        Toast.makeText(signInActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }));
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}