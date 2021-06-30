package com.example.lsapplication.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

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
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsapplication.R;
import com.example.lsapplication.ViewModel.userViewModel;
import com.example.lsapplication.databinding.ActivitySignInBinding;
import com.example.lsapplication.server.RetropitClient;
import com.example.lsapplication.server.UserApiService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;


import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class signInActivity extends AppActivity  {
         ActivitySignInBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserApiService userApiService;
    private userViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        userViewModel = ViewModelProviders.of(this).get(userViewModel.class);
        Retrofit retropitClient = RetropitClient.getInstance(); //connect to mode
        userApiService = retropitClient.create(UserApiService.class); //connect to mode
        initView();
    }
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }




    private void initView() {
        binding.passwordTxt.addTextChangedListener (new TextWatcher() {
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
    }




    public void signIn(View view) {

        if((isValidEmail(binding.emailTxt.getText().toString()) && isValidPassword(binding.passwordTxt.getText().toString())))
        {
            //  signInInServer(); try sent to server
           /* userViewModel.login(binding.emailTxt.getText().toString(),binding.passwordTxt.getText().toString()); //show send to viewModel
            userViewModel.getResult().observe(this, result-> onGetResult(result));*/
            binding.btnSignin.setBackgroundResource(R.drawable.dark_blue_btn);
            startActivity(new Intent(this, employeeActivity.class));
            finish();
        }



    }

    private void onGetResult(String result) {
        if(result.equals("ok"))
        {
            startActivity(new Intent(this, employeeActivity.class));
            finish();
        }
    }

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

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void signInInServer() {
//        compositeDisposable.add(userApiService.login(binding.emailTxt.getText().toString(),binding.passwordTxt.getText().toString())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String response) {
//                        Toast.makeText(SignUpActivity.this, ""+response, Toast.LENGTH_SHORT).show();
//                    }
//                    public void onError(final Throwable e) {
//                        // handle an error here
//                    }
//                }, throwable -> Log.e(TAG, "Throwable " + throwable.getMessage())*//*,onSuccess -> {
//                        Toast.makeText(SignUpActivity.this, ""+"sucsses", Toast.LENGTH_SHORT).show();
//    }, onError -> {Toast.makeText(SignUpActivity.this, ""+"error", Toast.LENGTH_SHORT).show();} ));
//
//}


    public void niceValidation(View view) {
        startActivity(new Intent(this, SignInWithNiceValidationActivity.class));
    }
}