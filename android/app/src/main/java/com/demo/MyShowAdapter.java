package com.dz365.ble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyShowAdapter extends BaseAdapter {
    private Context context;
    private List listMaster,listDetail;

    public MyShowAdapter(Context context,List listMaster,List listDetail) {
        this.context = context;
        this.listMaster = listMaster;
        this.listDetail = listDetail;
    }

    @Override
    public int getCount() {
        return listMaster.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
            TextView one = (TextView)convertView.findViewById(R.id.textViewOne);
            TextView two = (TextView)convertView.findViewById(R.id.textViewTwo);

            one.setText((String)listMaster.get(position));
            two.setText((String)listDetail.get(position));
        }
        return convertView;
    }
}
