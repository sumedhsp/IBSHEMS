package com.example.sumedhsp2.esep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends BaseAdapter {
    LayoutInflater mInflator;
    Map <String, Integer> price;
    Map <String, String> name;
    List<String> Name;
    List<Integer> price1;
    List<String> status;

    public ItemAdapter(Context c, Map m, List<String> s, Map m1){
        mInflator = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        price = m;
        name = m1;
        status = new ArrayList<>(s);
        Name = new ArrayList<>(name.values());
        price1 = new ArrayList<>(price.values());
    }
    @Override
    public int getCount() {
        return price.size();
    }

    @Override
    public Object getItem(int i) {
        return Name.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflator.inflate(R.layout.list_view, null);
        TextView name_relative = (TextView)v.findViewById(R.id.name_relative_ID);
        TextView price_relative = (TextView)v.findViewById(R.id.price_relative_ID);
        TextView status_relative = (TextView)v.findViewById(R.id.status_relative_ID);

        name_relative.setText(Name.get(i));
        price_relative.setText("Rs. "+String.valueOf(price1.get(i))+"/unit");
        status_relative.setText(status.get(i));
        return v;
    }
}
