package com.danik.smarthouse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.User;
import com.danik.smarthouse.service.UserService;
import com.danik.smarthouse.service.impl.UserServiceImpl;
import com.danik.smarthouse.service.utils.HttpClient;
import com.danik.smarthouse.service.utils.JsonMapper;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity {

    private UserService userService = new UserServiceImpl();
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        final EditText etLastName = (EditText) findViewById(R.id.etLastName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bSignUp = (Button) findViewById(R.id.bSignUp);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String response = "";
//                try {
////                    response = httpClient.execute("http://192.168.1.232:9090/user/save?" +
//                    response = httpClient.execute("http://mplus.hopto.org:9090/user/save?" +
//                                "name=" + etName.getText().toString() +
//                                "&middleName=" + etMiddleName.getText().toString() +
//                                "&lastName=" + etLastName.getText().toString() +
//                                "&email=" + etEmail.getText().toString() +
//                                "&password=" + etPassword.getText().toString(), "POST").get();
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//                Log.i("tag", "-------------------------------------");
//                Log.i("tag", response);
//                Log.i("tag", "-------------------------------------");
//
////                UserDetails.user = JsonMapper
////                        .parseJSONFromLink("http://192.168.1.232:9090/user/find-by-email/" + etEmail.getText().toString(),
////                                User.class);
//                UserDetails.user = JsonMapper.parseJSON(response, User.class);
                UserDetails.user = userService.save(etName.getText().toString(),
                        etMiddleName.getText().toString(),
                        etLastName.getText().toString(),
                        etEmail.getText().toString(),
                        etPassword.getText().toString());
                Log.e("tag", UserDetails.user.toString());
                if (UserDetails.user != null) {
                    SignUpActivity.this.startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }
            }
        });
    }
}
