package com.example.giaothongappnew.ui.amercement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.giaothongappnew.R;
import com.example.giaothongappnew.adapter.AmercementAdapter;
import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.AmercementLevel;
import com.example.giaothongappnew.model.Vehicle;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AmercementFragment extends DialogFragment {
    int user_id=-1;
    int error_id=-1;
    SharedPreferences preferences;
    TrafficDatabase db;
    List<AmercementLevel> amercementLevelList = new ArrayList<>();
    ListView lvAmercement;
    EditText edtAmercement;
    Spinner spnVehicle;
    Button btnSave,btnClose,btnClear;
    AmercementLevel currentAmercement=null;

    public AmercementFragment(TrafficDatabase db, int error_id){
        this.db = db;
        this.error_id = error_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_amercement, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getActivity().getSharedPreferences(Utils.MY_REF,MODE_PRIVATE);
        user_id = preferences.getInt("user_id",0);
        initControl(view);
        bindEvent();
    }

    private void initControl(View view){
        btnClear = view.findViewById(R.id.btnCancel);
        lvAmercement = view.findViewById(R.id.lvAmercement);
        edtAmercement = view.findViewById(R.id.edtAmercemen);
        spnVehicle = view.findViewById(R.id.spnVehicle);
        btnSave = view.findViewById(R.id.btnSave);
        btnClose = view.findViewById(R.id.btnClose);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,Utils.arrVehicle);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVehicle.setAdapter(aa);
        amercementLevelList = db.getListAmercement(error_id);
        lvAmercement.setAdapter(new AmercementAdapter(amercementLevelList,getContext(),getActivity().getSupportFragmentManager(),db,error_id));
    }

    private void bindEvent(){
        lvAmercement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtAmercement.setText(amercementLevelList.get(position).getAmercement());
                spnVehicle.setSelection(amercementLevelList.get(position).getVehical());
                currentAmercement = amercementLevelList.get(position);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentAmercement!=null){
                    edtAmercement.setText("");
                    spnVehicle.setSelection(0);
                    currentAmercement = null;
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtAmercement.getText().toString().isEmpty()){
                    if(currentAmercement!=null){
                        currentAmercement.setAmercement(edtAmercement.getText().toString());
                        long a = db.updateAmercement(currentAmercement);
                        if(a>0){
                            Toast.makeText(getContext(),"Thành công",Toast.LENGTH_SHORT).show();
                            amercementLevelList = db.getListAmercement(error_id);
                            lvAmercement.setAdapter(new AmercementAdapter(amercementLevelList,getContext(),getActivity().getSupportFragmentManager(),db,error_id));
                            edtAmercement.setText("");
                            spnVehicle.setSelection(0);
                        }
                        else{
                            Toast.makeText(getContext(),"Không thành công!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        long a = db.insertAmercement(new AmercementLevel(error_id,spnVehicle.getSelectedItemPosition(),edtAmercement.getText().toString()));
                        if(a>0){
                            Toast.makeText(getContext(),"Thành công",Toast.LENGTH_SHORT).show();
                            amercementLevelList = db.getListAmercement(error_id);
                            lvAmercement.setAdapter(new AmercementAdapter(amercementLevelList,getContext(),getActivity().getSupportFragmentManager(),db,error_id));
                            edtAmercement.setText("");
                            spnVehicle.setSelection(0);
                        }
                        else{
                            Toast.makeText(getContext(),"Đã tồn tại mức phạt cho loại phương tiện này!",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {
                    Toast.makeText(getContext(),"Vui lòng nhập giá trị mức phạt",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
