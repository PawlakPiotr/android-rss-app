package com.example.rss_app.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rss_app.R;

public class RegisterActivity extends AppCompatActivity {

    Button register_button;
    EditText email, password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpComponents();

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        if (password.getText().toString() == confirm_password.getText().toString()) {
            // TODO register account handler
        } else {
            // TODO error = passwords are not the same
        }
    }

    private void setUpComponents() {
        register_button = findViewById(R.id.register_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
    }
}
