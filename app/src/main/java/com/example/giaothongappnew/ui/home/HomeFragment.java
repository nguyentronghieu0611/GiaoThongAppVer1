package com.example.giaothongappnew.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.giaothongappnew.R;
import com.example.giaothongappnew.model.Error;
import com.example.giaothongappnew.adapter.ErrorAdapter;
import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.ui.error.ErrorFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    Button btnAdd;
    TrafficDatabase db;
    ListView lvError, lvSearchHistory;
    List<Error> listError = new ArrayList<>();
    FragmentManager fragmentManager;
    FrameLayout layoutHistory;
    private Snackbar snackbar;
    private ConstraintLayout layout;
    private int typeMenu = 1;
    private int role=0;
    SharedPreferences preferences;
    int user_id=0;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initControl(root);
        bindEvent();
        return root;
    }

    private void initControl(View view){
        btnAdd = view.findViewById(R.id.btnAdd);
        layout = view.findViewById(R.id.home_container);
        preferences = getActivity().getSharedPreferences(Utils.MY_REF,MODE_PRIVATE);
        role = preferences.getInt("role",0);
        user_id = preferences.getInt("user_id",0);
        layout = view.findViewById(R.id.layout);
        fragmentManager = getActivity().getSupportFragmentManager();
        lvError = view.findViewById(R.id.listview);
        db = new TrafficDatabase(getContext());
        initDefaultData();
        listError = db.getError();
        lvError.setAdapter(new ErrorAdapter(listError,getContext(),fragmentManager,db));
    }

    private void bindEvent(){
        lvError.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new ErrorFragment(listError.get(position),db,role)).addToBackStack(null).commit();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new ErrorFragment(null,db,role)).addToBackStack(null).commit();
            }
        });
    }

    private void initDefaultData(){
        if(db.getError().size()==0){
            db.insertError(new Error("Lỗi vượt đèn đỏ","Lỗi vượt đèn đỏ",null));
            db.insertError(new Error("Lỗi không gương","Lỗi không gương",null));
            db.insertError(new Error("Lỗi quay xe","Lỗi quay xe",null));
            db.insertError(new Error("Lỗi chở quá người quy định","Lỗi chở quá người quy định",null));
            db.insertError(new Error("Lỗi chuyển làn","Lỗi chuyển làn",null));
        }
    }
}