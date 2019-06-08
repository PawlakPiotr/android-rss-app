package com.example.rss_app.app.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rss_app.R;
import com.example.rss_app.app.model.Article;
import com.example.rss_app.app.service.ApiClient;
import com.example.rss_app.app.service.IService;
import com.example.rss_app.app.service.RetrieveFeed;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Ref;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_button, register_button;
    EditText email, password;
    ArrayList<String> headlines, links, desc, dates, images;
    ArrayList<Article> news;


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IService iService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpComponents();

        Retrofit apiClient = ApiClient.getInstance();
        iService = apiClient.create(IService.class);

        retriveFeed();

        news = new ArrayList<>();
        System.out.println(headlines);
    }

    private void setUpComponents() {
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        setButtonsListeners(new Button[]{login_button, register_button});
    }

    private void retriveFeed() {
        headlines = new ArrayList<>();
        desc = new ArrayList<>();
        dates = new ArrayList<>();
        images = new ArrayList<>();

        RetrieveFeed getXML = new RetrieveFeed(this);
        getXML.execute();
        headlines = getXML.heads();
        desc = getXML.desc();
        dates = getXML.date();
        links = getXML.links();
    }

    private void login(final String email, String password) {
        for (int i=0; i< headlines.size(); i++) {
            String desc_modified = desc.get(i).replaceAll("amp;", "").substring(53).replaceAll("=50", "=650");
            int index = desc_modified.indexOf("align")-2;
            String url = desc_modified.substring(0, index);

            images.add(url);

            news.add(new Article(headlines.get(i), desc.get(i), dates.get(i), images.get(i), links.get(i)));
        }


        if (!email.isEmpty() && !password.isEmpty())

            compositeDisposable.add(iService.login(email, password).subscribeOn(
                    Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    new Consumer<String>() {
                        @Override
                        public void accept(String s) throws JSONException {

                            JSONObject res = new JSONObject(s);

                            if (res.getString("code").equals("200")) {
                                Toast.makeText(MainActivity.this, res.getString("message"), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                                intent.putParcelableArrayListExtra("news", news);
                                intent.putExtra("email", email);
                                intent.putExtra("fav", "true");

                                startActivity(intent);
                            } else{
                                Toast.makeText(MainActivity.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                                clear();
                            }
                        }
                    }));

        else
            Toast.makeText(MainActivity.this, "Fill in the email and password fields!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                login(email.getText().toString(), password.getText().toString());
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

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void clear(){
        email.setText("");
        password.setText("");
    }
}
