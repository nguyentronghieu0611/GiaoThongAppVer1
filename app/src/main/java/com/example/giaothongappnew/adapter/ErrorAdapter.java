package com.example.giaothongappnew.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;


import com.bumptech.glide.Glide;
import com.example.giaothongappnew.R;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.Error;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ErrorAdapter extends BaseAdapter {
    List<Error> errorList;
    Context context;
    FragmentManager fragmentManager;
    TrafficDatabase db;
    private LayoutInflater layoutInflater;

    public ErrorAdapter(List<Error> errorList, Context context, FragmentManager fragmentManager, TrafficDatabase db) {
        this.errorList = errorList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.db = db;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return errorList.size();
    }

    @Override
    public Object getItem(int position) {
        return errorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.layout_error_items, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imgAvatar);
            holder.txtContactName = convertView.findViewById(R.id.txtErrorName);
            holder.txtErrorDescription = convertView.findViewById(R.id.txtErrorDescription);
            holder.txtView = convertView.findViewById(R.id.txtView);
            holder.txtIcon = convertView.findViewById(R.id.txtIcon);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();
        holder.txtView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        final Error error = errorList.get(position);
        holder.txtContactName.setText(error.getName());
        holder.txtErrorDescription.setText(error.getDescription());
        if(error.getImage() != null){
            Glide.with(context).load(error.getImage()).centerCrop().into(holder.imageView);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.txtIcon.setVisibility(View.INVISIBLE);
        }
        else {
            holder.txtIcon.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class ViewHolder{
        TextView txtContactName,txtErrorDescription, txtView;
        CircleImageView imageView,txtIcon;
    }
}
