package com.example.rss_app.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rss_app.R;
import com.example.rss_app.app.service.RetrieveFeed;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_button, register_button;
    EditText email, password;
    ArrayList<String> headlines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpComponents();
//        Service api_service = new Service();
//        try {
//            api_service.get("/api/news/all");
//        } catch (IOException e) {
//            System.out.print(R.string.API_FETCH_ERROR);
//            e.printStackTrace();
//        }

        headlines = new ArrayList<>();

        RetrieveFeed getXML = new RetrieveFeed();
        getXML.execute();
        headlines = getXML.heads();

    }

    private void setUpComponents() {
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        setButtonsListeners(new Button[]{login_button, register_button});
    }

    private void login() {
        System.out.println("### " + headlines);
        startActivity(getSpecificIntent(NewsActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                login();
                break;
            case R.id.register_button:
                startActivity(getSpecificIntent(RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    private void setButtonsListeners(Button[] arr) {
        for(Button btn : arr) {
            btn.setOnClickListener(this);
        }
    }

    public Intent getSpecificIntent(Class cs) {
        Intent intent = new Intent(this, cs);

        return intent;
    }

}
