package com.example.giaothongappnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;


import com.example.giaothongappnew.R;
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

    public AmercementAdapter(List<AmercementLevel> amercementLevelList, Context context, FragmentManager fragmentManager, TrafficDatabase db) {
        this.amercementLevelList = amercementLevelList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.db = db;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AmercementAdapter.ViewHolder holder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.layout_amercement_items, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imgAvatar);
            holder.txtVehicle = convertView.findViewById(R.id.txtVehicle);
            holder.txtAmercement = convertView.findViewById(R.id.txtAmercement);
            holder.txtIcon = convertView.findViewById(R.id.txtIcon);
            convertView.setTag(holder);
        }
        else
            holder = (AmercementAdapter.ViewHolder) convertView.getTag();
        final AmercementLevel amercementLevel = amercementLevelList.get(position);
        holder.txtVehicle.setText(amercementLevel.getVehical());
        holder.txtAmercement.setText(amercementLevel.getAmercement());
        holder.txtIcon.setVisibility(View.VISIBLE);
        holder.imageView.setVisibility(View.INVISIBLE);
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
        TextView txtIcon, txtVehicle, txtAmercement;
        CircleImageView imageView;
    }
}
