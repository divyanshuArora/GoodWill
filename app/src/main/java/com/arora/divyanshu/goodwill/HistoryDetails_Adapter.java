package com.arora.divyanshu.goodwill;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HistoryDetails_Adapter extends BaseAdapter {

    ArrayList<History_Model> Userdata=new ArrayList<History_Model>();;


    Context c;
    LayoutInflater inflater;








public HistoryDetails_Adapter(Context c, ArrayList<History_Model> data) {
        this.c = c;

        this.Userdata=data;

    }



    @Override
    public int getCount() {
        return Userdata.size();
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {


        final TextView info,date,credit,debit,balance;





        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View ItemView = inflater.inflate(R.layout.history_item, parent, false);


        date = (TextView) ItemView.findViewById(R.id.dateHistory);
        info = (TextView) ItemView.findViewById(R.id.infoHistory);
        debit = (TextView) ItemView.findViewById(R.id.debitHistory);
        credit = (TextView) ItemView.findViewById(R.id.creditHistory);
        balance=(TextView) ItemView.findViewById(R.id.balanceHistory);



        Log.e("position", ""+Userdata.get(position).getUname() );

        info.setText(Userdata.get(position).getInfo());
        date.setText(Userdata.get(position).getUdate());
        credit.setText(Userdata.get(position).getCredit());
        debit.setText(Userdata.get(position).getDebit());
        balance.setText(Userdata.get(position).getBalance());





        if(Userdata.get(position).getCredit().equals("0"))
        {
            debit.setTextColor(Color.RED);
            debit.setTypeface(Typeface.DEFAULT_BOLD);
        }




        if (Userdata.get(position).getDebit().equals("0"))
        {

            credit.setTextColor(Color.parseColor("#058107"));
            credit.setTypeface(Typeface.DEFAULT_BOLD);


        }


















        return ItemView;
    }





}