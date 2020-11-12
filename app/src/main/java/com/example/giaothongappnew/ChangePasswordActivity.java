package com.example.giaothongappnew;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.User;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordActivity extends AppCompatActivity {
    String MY_PREF = "my_pref";
    SharedPreferences preferences;
    EditText pass, repass;
    TextInputLayout errpass, errrepass;
    Button btnChangePass;
    TrafficDatabase db;
    int user_id = 0;
    boolean isPasswordValid = false;
    ScrollView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initControl();
        bindEvent();
    }

    private void initControl() {
        getSupportActionBar().setTitle("Đổi mật khẩu");
        pass = (EditText) findViewById(R.id.password);
        repass = (EditText) findViewById(R.id.repassword);
        errpass = (TextInputLayout) findViewById(R.id.passError);
        errrepass = (TextInputLayout) findViewById(R.id.repassError);
        preferences = getSharedPreferences(MY_PREF, MODE_PRIVATE);
        if (preferences.contains("user_id")) {
            user_id = preferences.getInt("user_id", 0);
        }
        layout = findViewById(R.id.layout_change_password);
        db = new TrafficDatabase(this);
        btnChangePass = findViewById(R.id.changepass);
    }

    private void bindEvent() {
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().isEmpty()) {
                    errpass.setError(getResources().getString(R.string.password_error));
                    return;
                } else if (pass.getText().length() < 6) {
                    errpass.setError(getResources().getString(R.string.error_invalid_password));
                    return;
                } else if (!repass.getText().toString().equals(pass.getText().toString())) {
                    errrepass.setError(getResources().getString(R.string.pass_not_match));
                    errpass.setErrorEnabled(false);
                    return;
                } else {
                    errrepass.setErrorEnabled(false);
                    User user = new User(user_id, "", "", repass.getText().toString());
                    int a = db.changePassword(user);
                    if (a > 0) {
                        Utils.showSnackbar("Cập nhật thành công!", layout);
                    } else
                        Utils.showSnackbar("Cập nhật không thành công!", layout);
                }
            }
        });
    }
}