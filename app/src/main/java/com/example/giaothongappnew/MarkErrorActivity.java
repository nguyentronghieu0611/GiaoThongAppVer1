package com.example.giaothongappnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.example.giaothongappnew.adapter.MarkUserAdapter;
import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.MarkUser;

import java.util.ArrayList;
import java.util.List;

public class MarkErrorActivity extends AppCompatActivity {
    int user_id=0;
    TrafficDatabase db;
    SharedPreferences preferences;
    ListView lvMarkError;
    List<MarkUser> markUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_error);
        initControl();
    }

    private void initControl(){
        getSupportActionBar().setTitle("Danh sách lỗi đã lưu");
        preferences = getSharedPreferences(Utils.MY_REF, MODE_PRIVATE);
        user_id = preferences.getInt("user_id", 0);
        db = new TrafficDatabase(this);
        lvMarkError = findViewById(R.id.lvMarkError);
        markUserList = db.getMarkUser(user_id);
        lvMarkError.setAdapter(new MarkUserAdapter(markUserList,this,db,user_id));
    }
}