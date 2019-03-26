package com.arora.divyanshu.goodwill;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

public class cmp_nameAdapter extends BaseAdapter {


    ArrayList<Company_Name> data=new ArrayList<Company_Name>();

    Context c;
    LayoutInflater Inflater;


    public cmp_nameAdapter(Context c, ArrayList<Company_Name> userdata)
    {
        this.data=userdata;
        this.c=c;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        convertView = LayoutInflater.from(c).inflate(R.layout.spinner_layout, parent, false);
        TextView t1;
        t1 = (TextView) convertView.findViewById(R.id.text);

        Log.e("position",""+data.get(position).getCompany_name());
        t1.setText(data.get(position).getCompany_name());


        return convertView;
    }
}
