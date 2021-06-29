package com.example.lsapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.lsapplication.R;
import com.example.lsapplication.databinding.ActivitySignUpBinding;
import com.example.lsapplication.server.RetropitClient;
import com.example.lsapplication.server.UserApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity {
 ActivitySignUpBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserApiService userApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        Retrofit retropitClient = RetropitClient.getInstance();
        userApiService = retropitClient.create(UserApiService.class);
        initView();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    public void ClickSeePassword(View view) {
        binding.passwordTxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        binding.seePassword.setBackgroundResource(R.drawable.not_see_password);

        new Handler().postDelayed(() -> {
            binding.seePassword.setBackgroundResource(R.drawable.see_password);
            binding.passwordTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }, 2000);
    }


    public void ClickSeePasswordRetype(View view) {
        binding.retypePasswordTxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        binding.seePasswordRetype.setBackgroundResource(R.drawable.see_password_button);

        new Handler().postDelayed(() -> {
            binding.seePasswordRetype.setBackgroundResource(R.drawable.see_rate_button);
            binding.retypePasswordTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }, 2000);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void  save(View view) {
      /*  startActivity(new Intent(this, signInActivity.class));
        finish();*/
        compositeDisposable.add(userApiService.register(binding.firstNameTxt.getText().toString(),binding.lastNameTxt.getText().toString(), binding.emailTxt.getText().toString(), binding.passwordTxt.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) {
                        Toast.makeText(SignUpActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                    public void onError(final Throwable e) {
                        // handle an error here
                    }
                }, throwable -> Log.e(TAG, "Throwable " + throwable.getMessage())/*,onSuccess -> {
                    Toast.makeText(SignUpActivity.this, ""+"sucsses", Toast.LENGTH_SHORT).show();
                    }, onError -> {Toast.makeText(SignUpActivity.this, ""+"error", Toast.LENGTH_SHORT).show();}*/ ));
    }

    public void signIp(View view) {
        if(isValidFirstName(binding.firstNameTxt.getText().toString()) && isValidLastName(binding.lastNameTxt.getText().toString()) &&  isValidEmail(binding.emailTxt.getText().toString()) && isValidPassword(binding.passwordTxt.getText().toString()) && isValidPasswordRetype(binding.retypePasswordTxt.getText().toString()))
        {
            binding.btnSignin.setBackgroundResource(R.drawable.dark_blue_btn);
            startActivity(new Intent(this, employeeActivity.class));
            finish();
        }

    }
    private void initView() {
        binding.retypePasswordTxt.addTextChangedListener (new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!binding.emailTxt.getText().toString().equals("") && !binding.firstNameTxt.getText().toString().equals("") && !binding.lastNameTxt.getText().toString().equals("") && !binding.passwordTxt.getText().toString().equals(""))
                    binding.btnSignin.setBackgroundResource(R.drawable.dark_blue_btn);
            }
        });

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
            isValidEmail(binding.emailTxt.getText().toString());
            return  false;
        }
        binding.validLastName.setVisibility(View.INVISIBLE);
        return true;
    }
    public  boolean isValidPassword(CharSequence target) {
        if (!(!TextUtils.isEmpty(target) && target.length() >= 6))
        {
            binding.validPassword.setVisibility(View.VISIBLE);
            isValidPasswordRetype(binding.retypePasswordTxt.getText().toString());
            return  false;
        }
        binding.validPassword.setVisibility(View.INVISIBLE);
        return true;
    }
    public  boolean isValidPasswordRetype(CharSequence target) {
        if (!(!TextUtils.isEmpty(target) && target.equals(binding.passwordTxt.getText().toString())))
        {
            binding.validRetypePassword.setVisibility(View.VISIBLE);
            return  false;
        }
        binding.validRetypePassword.setVisibility(View.INVISIBLE);
        return true;
    }
}