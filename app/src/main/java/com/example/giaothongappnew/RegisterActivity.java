package com.example.giaothongappnew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.User;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    TrafficDatabase db;
    EditText name, email, password;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPasswordValid;
    TextInputLayout nameError, emailError, passError;
    ScrollView layout;
//    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new TrafficDatabase(this);
//        getActionBar().hide();
        layout = findViewById(R.id.layout_register);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        nameError = (TextInputLayout) findViewById(R.id.nameError);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);
//        radioGroup = findViewById(R.id.rdGroup);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
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
        } else {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isNameValid && isEmailValid  && isPasswordValid) {
//            int role = radioGroup.getCheckedRadioButtonId()==R.id.rdAdmin?0:1;
            long a = db.insertUser(new User(email.getText().toString(),name.getText().toString(),password.getText().toString(),0));
            if(a>0){
                Utils.showSnackbar("Đăng ký thành công",layout);
                finish();
            }
            else
                Utils.showSnackbar("Đăng ký thất bại",layout);
        }

    }
}
