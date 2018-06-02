package com.danik.smarthouse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

    final EditText etName = (EditText) findViewById(R.id.etName);
    final EditText etMiddleName = (EditText) findViewById(R.id.etMiddleName);
    final EditText etLastName = (EditText) findViewById(R.id.etLastName);
    final EditText etEmail = (EditText) findViewById(R.id.etEmail);
    final EditText etPassword = (EditText) findViewById(R.id.etPassword);
    final EditText etRepeatPassword = (EditText) findViewById(R.id.etRepeatPassword);
    final Button bSignUp = (Button) findViewById(R.id.bSignUp);

    final String name = ((EditText) findViewById(R.id.etName)).getText().toString();
    final String middleName = ((EditText) findViewById(R.id.etMiddleName)).getText().toString();
    final String lastName = ((EditText) findViewById(R.id.etLastName)).getText().toString();
    final String email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
    final String password = ((EditText) findViewById(R.id.etPassword)).getText().toString();
    final String repeatPassword = ((EditText) findViewById(R.id.etRepeatPassword)).getText().toString();

    boolean cancel = false;
    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
                UserDetails.user = userService.save(name, middleName, lastName, email, password);
                Log.e("user_save", UserDetails.user.toString());
                if (UserDetails.user != null) {
                    SignUpActivity.this.startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void attemptSignUp() {
        isFieldEmpty(etName);
        isFieldEmpty(etMiddleName);
        isFieldEmpty(etLastName);
        isFieldEmpty(etEmail);
        isFieldEmpty(etPassword);
        isFieldEmpty(etRepeatPassword);

        if (!isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        isPasswordsMatches(password, repeatPassword);
        if (cancel) {
            Log.i("tag", "cancel");
            focusView.requestFocus();
        }
    }

    private void isFieldEmpty(EditText et) {
        if (TextUtils.isEmpty(et.getText().toString())) {
            et.setError(getString(R.string.error_field_required));
            focusView = et;
            cancel = true;
        }
    }

    private void isPasswordsMatches(String password, String repeatPassword) {
        if (!password.equals(repeatPassword))
            etRepeatPassword.setError(getString(R.string.error_passwords_do_not_match));
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
