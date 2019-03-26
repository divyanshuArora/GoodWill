package com.arora.divyanshu.goodwill;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class PaymentDetails_Adapter extends BaseAdapter {

    ArrayList<payment_history> Userdata=new ArrayList<>();;


    Context c;
    LayoutInflater inflater;







//    public PaymentDetails_Adapter(Context c,ArrayList<payment_history>payment_date,ArrayList<payment_history>payment_amount,ArrayList<payment_history>payment_type,ArrayList<payment_history>payment_info,ArrayList<payment_history>payment_by,ArrayList<payment_history>payment_thumnail_slip,ArrayList<payment_history>payment_orignal_slip) {
public PaymentDetails_Adapter(Context c,ArrayList<payment_history>data) {
        this.c = c;

        this.Userdata=data;

//        this.payment_date = payment_date;
//        this.payment_amount = payment_amount;
//        this.payment_type=payment_type;
//        this.payment_info=payment_info;
//        this.payment_by=payment_by;
//        this.payment_thumnail_slip=payment_thumnail_slip;
//        this.payment_orignal_slip=payment_orignal_slip;


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


        final TextView pDate,pAmt,pType,pInfo,pBy;
        ImageView pThumbSlip,pOrigSlip;




        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View ItemView = inflater.inflate(R.layout.payment_history_list, parent, false);


        pDate = (TextView) ItemView.findViewById(R.id.pDate);
        pAmt = (TextView) ItemView.findViewById(R.id.pAmt);
        pType = (TextView) ItemView.findViewById(R.id.pType);
        pInfo = (TextView) ItemView.findViewById(R.id.pInfo);
        pBy = (TextView) ItemView.findViewById(R.id.pBy);
        pOrigSlip=(ImageView)ItemView.findViewById(R.id.originalImg);


        pThumbSlip=(ImageView) ItemView.findViewById(R.id.thumbnails);
        pThumbSlip.setClickable(true);

        pThumbSlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                showDialog(position);

            }
        });



        Log.e("position", ""+Userdata.get(position).getAmnt() );

        pDate.setText(Userdata.get(position).getDates());
        pAmt.setText(Userdata.get(position).getAmnt());
        pType.setText(Userdata.get(position).getTypes());
        pInfo.setText(Userdata.get(position).getInfos());
        pBy.setText(Userdata.get(position).getBys());


        Picasso.with(c).load(Userdata.get(position).getThumbs()).error(R.drawable.user_drawable_left).into(pThumbSlip);








        return ItemView;
    }




    /////////////show dialog




    private void showDialog(final int position ) {
        final Dialog dialog = new Dialog(c);//, R.style.FullHeightDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.image_preview);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        //((TextView) dialog.findViewById(R.id.originalImg)).setText("0");



         ImageView orglImg= (ImageView) dialog.findViewById(R.id.originalImg);
        Picasso.with(c).load(Userdata.get(position).getOrignal()).into(orglImg);




        dialog.findViewById(R.id.closedialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }









}