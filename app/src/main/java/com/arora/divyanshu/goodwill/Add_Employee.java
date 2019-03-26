package com.arora.divyanshu.goodwill;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arora.divyanshu.goodwill.Utility.Response;
import com.arora.divyanshu.goodwill.Utility.Server;

import org.json.JSONObject;

public class Add_Employee extends AppCompatActivity {

    EditText addName,addNumber;
    Button insertEmp;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__employee);

        sessionManager = new SessionManager(getApplicationContext());


        addName=(EditText)findViewById(R.id.nameAdd);
        addNumber=(EditText)findViewById(R.id.numAdd);

        insertEmp=(Button)findViewById(R.id.insertEmp);
        insertEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEmpl();
            }
        });




    }




    public void insertEmpl()
    {


            String ename1 = addName.getText().toString();
            String emobile1 = addNumber.getText().toString();





        if (ename1.isEmpty())
        {
            addName.setError("Enter Employee Name");
            return;
        }





          else {


                final ProgressDialog pDialog = new ProgressDialog(Add_Employee.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject();


//                    String uname1 = sessionManager.getString("user_name");



                    jsonObject.put("new_employee_name", ename1);
                    jsonObject.put("employee_mobile", emobile1);


                    Log.e("on request", "  " + Server.URL + "add_employee" + jsonObject.toString());


                    Server.fetchPost(getApplicationContext(), Server.URL + "add_employee", jsonObject, "", new Server.OnResponseListener() {
                        @Override
                        public void onJSONResponse(Response response, String code) {
                            pDialog.dismiss();


                            try {

                                if (response.data.getString("error").equalsIgnoreCase("0")) {

                                    // JSONObject jsonObject1 =response.data;

                                    Toast.makeText(Add_Employee.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();

                                } else if (response.data.getString("error").equalsIgnoreCase("1")) {
                                    Toast.makeText(Add_Employee.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (response.data.getString("error").equalsIgnoreCase("2")) {
                                    Toast.makeText(Add_Employee.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Add_Employee.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
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



