package com.example.giaothongappnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.giaothongappnew.R;
import com.example.giaothongappnew.config.TrafficDatabase;
import com.example.giaothongappnew.model.MarkUser;

import java.util.List;

public class MarkUserAdapter extends BaseAdapter {
    List<MarkUser> markUserList;
    Context context;
    TrafficDatabase db;
    private LayoutInflater layoutInflater;
    int user_id=0;

    public MarkUserAdapter(List<MarkUser> markUserList, Context context, TrafficDatabase db, int user_id) {
        this.markUserList = markUserList;
        this.context = context;
        this.db = db;
        this.user_id = user_id;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return markUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return markUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MarkUserAdapter.ViewHolder holder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.layout_markuser_items, null);
            holder = new MarkUserAdapter.ViewHolder();
            holder.txtErrorName = convertView.findViewById(R.id.txtErrorName);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        }
        else
            holder = (MarkUserAdapter.ViewHolder) convertView.getTag();
        final MarkUser markUser = markUserList.get(position);
        holder.txtErrorName.setText(markUser.getError_name());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long a = db.deleteMarkUser(user_id,markUser.getError_id());
                if(a>0){
                    Toast.makeText(context,"Thành công",Toast.LENGTH_SHORT).show();
                    markUserList = db.getMarkUser(user_id);
                    notifyDataSetChanged();
                }
                else
                    Toast.makeText(context,"Không thành công",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView txtErrorName;
        ImageView btnDelete;
    }
}
