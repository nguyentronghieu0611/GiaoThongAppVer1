package com.example.giaothongappnew.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.giaothongappnew.R;
import com.example.giaothongappnew.model.Vehicle;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {
    public static final String MY_REF = "my_pref";
    private static final HashMap<Integer, String> hashVehicle = new HashMap<Integer, String>(){
        {
            put(0,"Xe đạp");
            put(1,"Xe máy");
            put(2,"Xe ô tô");
            put(3,"Máy bay");
        }
    };

    public static final String[] arrVehicle = new String[]{"Xe đạp","Xe máy","Xe ô tô","Máy bay"};

    public static void showSnackbar(String message, ViewGroup layout) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundResource(R.color.colorPrimaryDark);
        snackbar.show();
    }

    public static void forceHide(@NonNull Activity activity, @NonNull EditText editText) {
        if (activity.getCurrentFocus() == null || !(activity.getCurrentFocus() instanceof EditText)) {
            editText.requestFocus();
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static String getVehicle(int vehicle_id){
        return hashVehicle.get(vehicle_id);
    }

}