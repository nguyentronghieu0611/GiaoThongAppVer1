package com.example.giaothongappnew.ui.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.giaothongappnew.ChangePasswordActivity;
import com.example.giaothongappnew.LoginActivity;
import com.example.giaothongappnew.MarkErrorActivity;
import com.example.giaothongappnew.R;
import com.example.giaothongappnew.UserInfoActivity;

public class DashboardFragment extends Fragment {
    LinearLayout layoutInfo, layoutChangePass, layoutLogout, layoutExit, layoutMarkError;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initControl(root);
        bindEvent();
        return root;
    }

    private void initControl(View view){
        layoutInfo = view.findViewById(R.id.layoutInfo);
        layoutChangePass = view.findViewById(R.id.layoutChangePass);
        layoutLogout = view.findViewById(R.id.layoutLogout);
        layoutExit = view.findViewById(R.id.layoutExit);
        layoutMarkError = view.findViewById(R.id.layoutMarkError);
    }

    private void bindEvent(){
        layoutMarkError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MarkErrorActivity.class));
            }
        });

        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserInfoActivity.class));
            }
        });

        layoutChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });

        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                SharedPreferences settings = getActivity().getSharedPreferences("my_pref", Context.MODE_PRIVATE);
                                settings.edit().clear().commit();
                                getActivity().finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                builder.setMessage("Bạn có muốn đăng xuất?").setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener).show();
            }
        });

        layoutExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                getActivity().finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                builder.setMessage("Bạn có muốn thoát?").setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener).show();
            }
        });
    }
}