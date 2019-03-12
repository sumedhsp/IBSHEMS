package com.example.sumedhsp2.esep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter2 extends BaseAdapter {
    List<String> nameCustomer;
    List<Integer> powerRequired;
    LayoutInflater mInflator;

    public ItemAdapter2(Context c, List<String> Cust, List<Integer> power){
        mInflator = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nameCustomer = new ArrayList<>(Cust);
        powerRequired = new ArrayList<>(power);
    }
    @Override
    public int getCount() {
        return nameCustomer.size();
    }

    @Override
    public Object getItem(int i) {
        return nameCustomer.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflator.inflate(R.layout.energy_sharing_lender, null);
        TextView editText_Customer = (TextView)v.findViewById(R.id.editText_Customer);
        TextView editText_PowerReq = (TextView)v.findViewById(R.id.editText_PowerReq);

        editText_Customer.setText(nameCustomer.get(i));
        editText_PowerReq.setText(String.valueOf(powerRequired.get(i))+"kWH");

        return v;
    }
}
