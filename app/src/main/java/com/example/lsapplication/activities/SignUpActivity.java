package com.example.lsapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsapplication.R;
import com.example.lsapplication.ViewModel.userViewModel;
import com.example.lsapplication.databinding.ActivitySignUpBinding;
import com.example.lsapplication.model.UserTextModel;
import com.example.lsapplication.server.RetropitClient;
import com.example.lsapplication.server.UserApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity  {
 ActivitySignUpBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserApiService userApiService;
    private ArrayAdapter<String> adapter;
    String[] language = new String[]{"En", "He"};
    UserTextModel userText;
    private userViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        userViewModel = ViewModelProviders.of(this).get(userViewModel.class);
        userText= getTextInEnglish();
        binding.setUserText(userText);
        initView();
    }
    public  UserTextModel getTextInEnglish()
    {
      return   new UserTextModel("First Name", "Last Name", "Email", "Password", "Retype Password", "personal Details", "Sign Up", "Sign In", "Have an account?","Our Terms of Use and Privacy Policy");
    }

    public  UserTextModel getTextInHebrow()
    {
      return   new UserTextModel("שם פרטי", "שם משפחה", "כתובת מייל", "סיסמא", "אימות סיסמא","פרטים אישיים","הרשמה",  "התחברות?", "אם יש לך חשבון " ,"תנאי השימוש ומדיניות הפרטיות שלנו");
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
        binding.seePasswordRetype.setBackgroundResource(R.drawable.not_see_password);

        new Handler().postDelayed(() -> {
            binding.seePasswordRetype.setBackgroundResource(R.drawable.see_password);
            binding.retypePasswordTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }, 2000);
    }
   /* @RequiresApi(api = Build.VERSION_CODES.N)
    public void  save(View view) {
      *//*  startActivity(new Intent(this, signInActivity.class));
        finish();*//*
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
                }, throwable -> Log.e(TAG, "Throwable " + throwable.getMessage())*//*,onSuccess -> {
                    Toast.makeText(SignUpActivity.this, ""+"sucsses", Toast.LENGTH_SHORT).show();
                    }, onError -> {Toast.makeText(SignUpActivity.this, ""+"error", Toast.LENGTH_SHORT).show();} ));

     }
*/
    public void signIn(View view) {
        startActivity(new Intent(this, signInActivity.class));
        finish();

    }
    private void onGetResult(String result) {
        if(result.equals("ok"))
        {
            startActivity(new Intent(this, employeeActivity.class));
            finish();
        }
    }
    private void initView() {
      initAdapter();
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

    private void initAdapter() {

            adapter = new ArrayAdapter<String>(this,
                    R.layout.item_spinner, language);
            adapter.setDropDownViewResource(R.layout.item_spinner);
            binding.spinner1.setAdapter(adapter);
            binding.spinner1.setOnItemSelectedListener(new
                                                                  AdapterView.OnItemSelectedListener() {
                                                                      @Override
                                                                      public void onItemSelected(AdapterView<?> parent, View view, int
                                                                              position, long id) {
                                                                          String data= parent.getItemAtPosition(position).toString();
                                                                               if(data.equals("He"))
                                                                               {
                                                                                   userText = getTextInHebrow();
                                                                                   binding.setUserText(userText);

                                                                               }
                                                                               else
                                                                               {
                                                                                   userText= getTextInEnglish();
                                                                                   binding.setUserText(userText);

                                                                               }
                                                                      }

                                                                      @Override
                                                                      public void onNothingSelected(AdapterView<?> parent) {
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

    public void save(View view) {
        if(isValidFirstName(binding.firstNameTxt.getText().toString()) && isValidLastName(binding.lastNameTxt.getText().toString()) &&  isValidEmail(binding.emailTxt.getText().toString()) && isValidPassword(binding.passwordTxt.getText().toString()) && isValidPasswordRetype(binding.retypePasswordTxt.getText().toString()))
        {
          /* userViewModel.register(binding.firstNameTxt.getText().toString(),binding.lastNameTxt.getText().toString(),binding.emailTxt.getText().toString(),binding.passwordTxt.getText().toString(),binding.retypePasswordTxt.getText().toString()); //show send to viewModel
            userViewModel.getResult().observe(this, result-> onGetResult(result));*/
            binding.btnSignin.setBackgroundResource(R.drawable.dark_blue_btn);
            startActivity(new Intent(this, employeeActivity.class));
            finish();
        }
    }
}