package com.arora.divyanshu.goodwill;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arora.divyanshu.goodwill.Utility.Response;
import com.arora.divyanshu.goodwill.Utility.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Received_Payment extends AppCompatActivity {
    cmp_nameAdapter adapter;
    Spinner selectname;
    ArrayList<Company_Name> Company_name;
    EditText dateSet,amt;
    Button getRecord;
    Calendar myCalendar = Calendar.getInstance();
    public static String  posValue,posID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received__payment);

        getCmpname();
        dateSet=(EditText) findViewById(R.id.DateRec);
        amt=(EditText) findViewById(R.id.amntRece_record);

        selectname=(Spinner)findViewById(R.id.empNameReceive);
        selectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 posValue = Company_name.get(position).company_name.toString();
                 posID = Company_name.get(position).company_id.toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getRecord=(Button)findViewById(R.id.getRecord);
        getRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecords();
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };



        dateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=  new DatePickerDialog(Received_Payment.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

                }
        });

        }


    private void getCmpname() {


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url = "http://guddukumar.com/account/api/company_list.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        pDialog.hide();
                        try {


                            JSONArray array = response.getJSONArray("list");

                            getJsonData(array);




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onError", "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "json req");
    }


    private void getJsonData(JSONArray array)

    {

        //    JSONArray array=response.getJsonArray("data");
        Company_name = new ArrayList<>();

        try {

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Company_Name company = new Company_Name();

                String id = obj.getString("company_id");
                String companyName = obj.getString("company_name");


                Log.e("employee_name", companyName);

                company.setCompany_id(id);
                company.setCompany_name(companyName);


                Company_name.add(company);

            }

            adapter = new cmp_nameAdapter(Received_Payment.this, Company_name);
            selectname.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    private void updateLabel() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateSet.setText(sdf.format(myCalendar.getTime()));
    }












    public void getRecords()
    {
            String date1 =dateSet.getText().toString();

            String amntRecord1 = amt.getText().toString();

            String selectName1 = selectname.getSelectedItem().toString();
            String id= posID;


            if (date1.isEmpty())
            {
                dateSet.performClick();
                return;
            }

        if (id.matches("null"))
        {
            Toast.makeText(this, "Select Company Name", Toast.LENGTH_SHORT).show();
            return;
        }



        if (amntRecord1.isEmpty())
        {
            amt.setError("Enter Amount");
            return;
        }







            else {


                final ProgressDialog pDialog = new ProgressDialog(Received_Payment.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject();


                    jsonObject.put("recevied", posValue);
//                    jsonObject.put("recevied", posID);
                    jsonObject.put("amount", amntRecord1);
                    jsonObject.put("payment_recevied_date", date1);



                    Log.e("on request", "  " + Server.URL + "recevied" + jsonObject.toString());


                    Server.fetchPost(getApplicationContext(), Server.URL + "recevied_payment", jsonObject, "", new Server.OnResponseListener() {
                        @Override
                        public void onJSONResponse(Response response, String code) {
                            pDialog.dismiss();


                            try {

                                if (response.data.getString("error").equalsIgnoreCase("0")) {

                                    // JSONObject jsonObject1 =response.data;

                                    Toast.makeText(Received_Payment.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();

                                } else if (response.data.getString("error").equalsIgnoreCase("1")) {
                                    Toast.makeText(Received_Payment.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (response.data.getString("error").equalsIgnoreCase("2")) {
                                    Toast.makeText(Received_Payment.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Received_Payment.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {

                }


            }

        }


    }


















