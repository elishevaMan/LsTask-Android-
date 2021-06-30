package com.example.lsapplication.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lsapplication.R;
import com.example.lsapplication.databinding.ActivitySignInWithNiceValidationBinding;
import com.example.lsapplication.server.RetropitClient;
import com.example.lsapplication.server.UserApiService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SignInWithNiceValidationActivity extends AppActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserApiService userApiService;

    @NotEmpty(messageResId = R.string.errorEmailText)
    @Email(messageResId = R.string.errorEmailText)
    @BindView(R.id.email_txt)
    public TextInputLayout email;
    @Password(min = 6, messageResId = R.string.errorPasswordText)
    @NotEmpty
    public TextInputLayout password;
    ActivitySignInWithNiceValidationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_in_with_nice_validation);
        Retrofit retropitClient = RetropitClient.getInstance(); //connect to node
        userApiService = retropitClient.create(UserApiService.class); //connect to node
        initView();

    }

    private void initView() {
        email= binding.email;
        password= binding.password;
        binding.btnSignin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (validated) {
            //  signInInServer(); try sent to server

            binding.btnSignin.setBackgroundResource(R.drawable.dark_blue_btn);
            startActivity(new Intent(this, employeeActivity.class));
            finish();
        }
    }

   /* @RequiresApi(api = Build.VERSION_CODES.N)
    public void signInInServer() {
        compositeDisposable.add(userApiService.login(binding.emailTxt.getText().toString(),binding.passwordTxt.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) {
                        Toast.makeText(SignInWithNiceValidationActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }));
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }*/


}