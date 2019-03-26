package com.arora.divyanshu.goodwill;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class emp_nameAdapter extends BaseAdapter {


    ArrayList<emp_name> data=new ArrayList<emp_name>();

    Context c;
    LayoutInflater Inflater;


    public emp_nameAdapter(Context c, ArrayList<emp_name> userdata)
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



        convertView = LayoutInflater.from(c).inflate(R.layout.activity_listview, parent, false);
        TextView t1;
        t1 = (TextView) convertView.findViewById(R.id.texturl);

        Log.e("position",""+data.get(position).getEmp_name());
        t1.setText(data.get(position).getEmp_name());


        return convertView;
    }
}
