package com.example.giaothongappnew.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.giaothongappnew.R;
import com.example.giaothongappnew.adapter.SearhHistoryAdapter;
import com.example.giaothongappnew.model.DataChange;
import com.example.giaothongappnew.model.Error;
import com.example.giaothongappnew.adapter.ErrorAdapter;
import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.SearchHistory;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements DataChange {
    EditText edtSearch;
    Button btnAdd;
    ImageView btnSearch;
    TrafficDatabase db;
    ListView lvError, lvSearchHistory;
    List<Error> listError = new ArrayList<>();
    List<SearchHistory> historyList = new ArrayList<>();
    FragmentManager fragmentManager;
    FrameLayout layoutHistory;
    private Snackbar snackbar;
    private ConstraintLayout layout;
    private int typeMenu = 1;
    private int role=0;
    SharedPreferences preferences;
    int user_id=0;
    SearchView searchView;
    private  NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initControl(root);
        bindEvent();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void initControl(View view){
        lvSearchHistory = view.findViewById(R.id.lvHistory);
        btnSearch = view.findViewById(R.id.btnSearch);
        edtSearch = view.findViewById(R.id.edtSearch);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
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
        lvError.setAdapter(new ErrorAdapter(listError,getContext(),fragmentManager,db,user_id));
    }

    private void bindEvent(){
        lvError.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ErrorFragment(listError.get(position),db,role)).addToBackStack("abc").commit();
                Bundle bundle = new Bundle();
                bundle.putSerializable("db",db);
                bundle.putSerializable("error",listError.get(position));
                bundle.putInt("role",role);
                navController.navigate(R.id.layout_add,bundle);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("db",db);
                bundle.putInt("role",role);
                navController.navigate(R.id.layout_add,bundle);
            }
        });

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    initSearch(true,edtSearch.getText().toString());
                }
                else{
                    initSearch(false,"");
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSearch.hasFocus()){
                    edtSearch.clearFocus();
                }
                else{
                    edtSearch.requestFocus();
                }
                initSearch(true, edtSearch.getText().toString());
            }
        });

        lvSearchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtSearch.setText(historyList.get(position).getSearch_txt());
                initSearch(true,historyList.get(position).getSearch_txt());
                lvSearchHistory.setVisibility(View.GONE);
            }
        });


        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if(edtSearch.getText().toString().isEmpty()){
                            Utils.forceHide(getActivity(),edtSearch);
                            edtSearch.clearFocus();
                        }
                        else {
                            edtSearch.setText("");
                            initSearch(true,"");
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initSearch(boolean isSearch,String searchText){
        if(isSearch && searchText.isEmpty()){
            historyList = db.getHistory(user_id);
            if(historyList.size()>0){
                lvSearchHistory.setAdapter(new SearhHistoryAdapter(historyList,getContext(),db));
                lvSearchHistory.setVisibility(View.VISIBLE);
            }
            else
                lvSearchHistory.setVisibility(View.GONE);
            listError = db.getError();
            lvError.setAdapter(new ErrorAdapter(listError,getContext(),fragmentManager,db,user_id));
        }
        else if(isSearch && !searchText.isEmpty()){
            db.insertHistory(new SearchHistory(user_id,searchText));
            historyList = db.getHistory(user_id);
            if(historyList.size()>0){
                lvSearchHistory.setAdapter(new SearhHistoryAdapter(historyList,getContext(),db));
                lvSearchHistory.setVisibility(View.GONE);
            }
            else
                lvSearchHistory.setVisibility(View.GONE);
            listError = db.searchError(searchText);
            lvError.setAdapter(new ErrorAdapter(listError,getContext(),fragmentManager,db,user_id));
        }
        else
            lvSearchHistory.setVisibility(View.GONE);
    }

    private void initDefaultData(){
        if(db.getError().size()==0){
            db.insertError(new Error("Lỗi vượt đèn đỏ","Lỗi vượt đèn đỏ phạt tiền từ 100.000 - 200.000 vnd",null));
            db.insertError(new Error("Lỗi không gương","Lỗi không gương phạt tiền từ 50.000 - 100.000 vnd",null));
            db.insertError(new Error("Lỗi quay xe","Lỗi quay xe phạt tiền từ 200.000 - 300.000 vnd",null));
            db.insertError(new Error("Lỗi chở quá người quy định","Lỗi chở quá người quy định phạt tiền từ 100.000 - 200.000 vnd",null));
            db.insertError(new Error("Lỗi chuyển làn","Lỗi chuyển làn phạt tiền từ 100.000 - 200.000 vnd",null));
        }
    }

    @Override
    public void onErrorChange() {
        Log.d("TAG", "onErrorChange: asdasdas");
    }
}