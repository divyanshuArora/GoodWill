package com.arora.divyanshu.goodwill;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arora.divyanshu.goodwill.Utility.Response;
import com.arora.divyanshu.goodwill.Utility.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Payment_Details extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();

    EditText startDate, endDate;
    ArrayList<emp_name> employee_name;
    emp_nameAdapter adapter;
    PaymentDetails_Adapter adapterPay;
    Button getData;
    Spinner selectEmp;
    ArrayList<payment_history> histories=new ArrayList<>();
    String empnm;
    ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__details);

        getempname();
        startDate = (EditText) findViewById(R.id.starDate);
        endDate = (EditText) findViewById(R.id.endDate);

        historyList = (ListView) findViewById(R.id.historyPayment);

        getData = (Button) findViewById(R.id.getRecord);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!histories.isEmpty())
                {
                    histories.clear();
                }

                getHistory();
            }
        });

        selectEmp = (Spinner) findViewById(R.id.empName);
        selectEmp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                empnm = employee_name.get(position).emp_id.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final DatePickerDialog.OnDateSetListener startdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDateUpdate();
            }

        };


        final DatePickerDialog.OnDateSetListener enddate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDateUpdate();
            }

        };


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Payment_Details.this, startdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=  new DatePickerDialog(Payment_Details.this, enddate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });


    }


    private void startDateUpdate() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDate.setText(sdf.format(myCalendar.getTime()));
    }


    private void endDateUpdate() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endDate.setText(sdf.format(myCalendar.getTime()));
    }


    private void getempname() {


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url = "http://guddukumar.com/account/api/employee_list.php";
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


                        }



                        catch (Exception e) {
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
        employee_name = new ArrayList<>();

        try {

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                emp_name user = new emp_name();



                String id = obj.getString("employee_id");
                String employee = obj.getString("employee_name");


                Log.e("employee_name", employee);

                user.setEmp_id(id);
                user.setEmp_name(employee);


                employee_name.add(user);

            }

            adapter = new emp_nameAdapter(Payment_Details.this, employee_name);
            selectEmp.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    public void getHistory()
    {




         String startDate1 = startDate.getText().toString();
         String endDate1 = endDate.getText().toString();
         String emp1 = empnm;





        if (emp1.matches("null"))
        {
            Toast.makeText(this, "Select Employee Name", Toast.LENGTH_SHORT).show();
            return;
        }


        final ProgressDialog pDialog = new ProgressDialog(Payment_Details.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();

            jsonObject.put("employee_id", emp1);
            jsonObject.put("start_date", startDate1);
            jsonObject.put("end_date", endDate1);



            Log.e("on request", "  " + Server.URL +"payment_history" + jsonObject.toString());


            final JSONObject finalJsonObject = jsonObject;
            Server.fetchPost(getApplicationContext(), Server.URL +"payment_history", jsonObject, "", new Server.OnResponseListener() {
                @Override
                public void onJSONResponse(Response response, String code) {
                    pDialog.dismiss();


                    try {



                        JSONArray array=response.data.getJSONArray("list");
                        for (int i = 0; i < array.length(); i++)
                        {

                            JSONObject object = array.getJSONObject(i);

                            payment_history histrys = new payment_history();




                            String dates = object.getString("payment_date");
                            String amt = object.getString("payment_amount");
                            String type = object.getString("payment_type");
                            String info = object.getString("payment_info");
                            String by = object.getString("payment_by");
                            String thumb = object.getString("payment_thumnail_slip");
                            String orgnl = object.getString("payment_orignal_slip");


                        histrys.setDates(dates);
                        histrys.setAmnt(amt);
                        histrys.setTypes(type);
                        histrys.setInfos(info);
                        histrys.setBys(by);
                        histrys.setThumbs(thumb);
                        histrys.setOrignal(orgnl);




                        histories.add(histrys);

//                        Toast.makeText(Payment_Details.this, ""+response.data, Toast.LENGTH_SHORT).show();
                        }
                       adapterPay = new PaymentDetails_Adapter(Payment_Details.this,histories);
                        historyList.setAdapter(adapterPay);

                        }


                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Payment_Details.this, ""+response.data, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {

        }




    }















}
