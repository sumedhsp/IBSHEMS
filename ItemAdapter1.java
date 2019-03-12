package com.example.sumedhsp2.esep;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter1 extends BaseAdapter {
    LayoutInflater mInflator1;
    List <Float> powerUsage;
    List <String> device_Name;
    List<String> location_Name;
    List<Integer> powerThreshold;

    public ItemAdapter1(Context c, List<String> h, List<String> h1, List<Float> h2, List<Integer> h3){
        mInflator1 = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        powerUsage = new ArrayList<>(h2);
        device_Name = new ArrayList<>(h);
        location_Name = new ArrayList<>(h1);
        powerThreshold = new ArrayList<>(h3);
    }
    @Override
    public int getCount() {
        return device_Name.size();
    }

    @Override
    public Object getItem(int i) {
        return device_Name.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v  = mInflator1.inflate(R.layout.prediction_layout, null);
        TextView device_ID = (TextView)v.findViewById(R.id.device_name_ID);
        TextView location_ID = (TextView)v.findViewById(R.id.location_ID);
        TextView powerUsage_ID = (TextView)v.findViewById(R.id.powerUsage_ID);
        TextView powerThreshold_ID = (TextView)v.findViewById(R.id.powerThreshold_ID);

        device_ID.setText(device_Name.get(i));
        location_ID.setText(location_Name.get(i));
        if (powerUsage.get(i) > powerThreshold.get(i)){
            powerUsage_ID.setTextColor(Color.RED);
        }
        else{
            powerUsage_ID.setTextColor(Color.GREEN);
        }
        powerUsage_ID.setText(String.valueOf(powerUsage.get(i))+"kWH");
        powerThreshold_ID.setText(String.valueOf(powerThreshold.get(i))+"kWH");

        return v;
    }
}
