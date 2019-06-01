package com.example.rss_app.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rss_app.R;
import com.example.rss_app.app.service.ApiClient;
import com.example.rss_app.app.service.IService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    Button register_button;
    EditText email, password, confirm_password;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IService iService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpComponents();

        Retrofit apiClient = ApiClient.getInstance();
        iService = apiClient.create(IService.class);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(email.getText().toString(), password.getText().toString(), confirm_password.getText().toString());
            }
        });
    }

    private void register(String email, String password, String confirm_password) {
        System.out.println("#### " + password + " === " + confirm_password);
        if (password.equals(confirm_password)) {
            compositeDisposable.add(iService.register(email, password).subscribeOn(
                    Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    new Consumer<String>() {

                        @Override
                        public void accept(String response) {
                            Toast.makeText(RegisterActivity.this, "User created!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
            ));
        } else {
            Toast.makeText(RegisterActivity.this, "Passwords are not the same!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpComponents() {
        register_button = findViewById(R.id.register_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
    }
}
