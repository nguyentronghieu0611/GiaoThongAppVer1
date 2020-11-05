package com.example.giaothongappnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences preferences;
    TrafficDatabase db;
    EditText email, password;
    Button login;
    TextView register;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(Utils.MY_REF,MODE_PRIVATE);
        if(preferences.contains("user_id")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        db = new TrafficDatabase(this);
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        getActionBar().hide();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {
        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isEmailValid && isPasswordValid) {
            List<User> lsRs = new ArrayList<>();
            lsRs = db.login(email.getText().toString(),password.getText().toString());
            if(lsRs.size()>0){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("user_id",lsRs.get(0).getId());
                editor.putString("email",lsRs.get(0).getEmail());
                editor.putString("name",lsRs.get(0).getName());
                editor.putInt("role",lsRs.get(0).getRole());
                editor.commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            else
                Toast.makeText(LoginActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
        }

    }

}