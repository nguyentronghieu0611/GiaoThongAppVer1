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

public class UserInfoActivity extends AppCompatActivity {
    TrafficDatabase db;
    String MY_PREF = "my_pref";
    SharedPreferences preferences;
    EditText name, email;
    TextInputLayout nameError, emailError;
    Button btnUpdate;
    ScrollView layout;
    int user_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initControl();
        bindEvent();
    }

    private void initControl(){
        getSupportActionBar().setTitle("Thông tin tài khoản");
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        nameError = (TextInputLayout) findViewById(R.id.nameError);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        preferences = getSharedPreferences(MY_PREF, MODE_PRIVATE);
        if (preferences.contains("user_id")) {
            name.setText(preferences.getString("name", ""));
            email.setText(preferences.getString("email", ""));
            user_id = preferences.getInt("user_id", 0);
        }
        layout = findViewById(R.id.layout_update_user);
        db = new TrafficDatabase(this);
        btnUpdate = findViewById(R.id.update);
    }

    private void bindEvent(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    nameError.setError(getResources().getString(R.string.name_error));
                } else {
                    nameError.setErrorEnabled(false);
                    User user = new User(user_id, email.getText().toString(), name.getText().toString(), "");
                    int a = db.updateUser(user);
                    if (a > 0) {
                        Utils.showSnackbar("Cập nhật thành công!", layout);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", email.getText().toString());
                        editor.putString("name", name.getText().toString());
                        editor.commit();
                    } else
                        Utils.showSnackbar("Cập nhật không thành công!", layout);
                }
            }
        });
    }
}