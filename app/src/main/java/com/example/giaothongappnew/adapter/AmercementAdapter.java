package com.example.giaothongappnew.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;


import com.bumptech.glide.Glide;
import com.example.giaothongappnew.R;
import com.example.giaothongappnew.common.Utils;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.AmercementLevel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AmercementAdapter extends BaseAdapter {
    List<AmercementLevel> amercementLevelList;
    Context context;
    FragmentManager fragmentManager;
    TrafficDatabase db;
    private LayoutInflater layoutInflater;
    int error_id=-1;

    public AmercementAdapter(List<AmercementLevel> amercementLevelList, Context context, FragmentManager fragmentManager, TrafficDatabase db, int error_id) {
        this.amercementLevelList = amercementLevelList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.db = db;
        this.error_id = error_id;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return amercementLevelList.size();
    }

    @Override
    public Object getItem(int position) {
        return amercementLevelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AmercementAdapter.ViewHolder holder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.layout_amercement_items, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imgAvatar);
            holder.txtVehicle = convertView.findViewById(R.id.txtVehicle);
            holder.txtAmercement = convertView.findViewById(R.id.txtAmercement);
            holder.imgDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        }
        else
            holder = (AmercementAdapter.ViewHolder) convertView.getTag();
        final AmercementLevel amercementLevel = amercementLevelList.get(position);
        holder.txtVehicle.setText(Utils.getVehicle(amercementLevel.getVehical()));
        holder.txtAmercement.setText(amercementLevel.getAmercement()+" vnđ");
        switch (amercementLevel.getVehical()){
            case 0:
                Glide.with(context).load(context.getDrawable(R.drawable.bycicle)).centerCrop().into(holder.imageView);
                break;
            case 1:
                Glide.with(context).load(context.getDrawable(R.drawable.scooter)).centerCrop().into(holder.imageView);
                break;
            case 2:
                Glide.with(context).load(context.getDrawable(R.drawable.car)).centerCrop().into(holder.imageView);
                break;
            case 3:
                Glide.with(context).load(context.getDrawable(R.drawable.airplane)).centerCrop().into(holder.imageView);
                break;
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long a = db.deleteAmercement(amercementLevel);
                if(a>0){
                    Toast.makeText(context,"Thành công",Toast.LENGTH_SHORT).show();
                    amercementLevelList = db.getListAmercement(error_id);
                    notifyDataSetChanged();
                }
                else
                    Toast.makeText(context,"Không thành công",Toast.LENGTH_SHORT).show();
            }
        });
//        if(amercementLevel.getImage() != null){
//            Glide.with(context).load(error.getImage()).centerCrop().into(holder.imageView);
//            holder.imageView.setVisibility(View.VISIBLE);
//            holder.txtIcon.setVisibility(View.INVISIBLE);
//        }
//        else {
//            holder.txtIcon.setVisibility(View.VISIBLE);
//            holder.imageView.setVisibility(View.INVISIBLE);
//        }
        return convertView;
    }

    static class ViewHolder{
        TextView txtVehicle, txtAmercement;
        CircleImageView imageView;
        ImageView imgDelete;
    }
}
