package com.arora.divyanshu.goodwill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.MediaSessionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arora.divyanshu.goodwill.Utility.Response;
import com.arora.divyanshu.goodwill.Utility.Server;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText num, pass;
    Button login;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sessionManager = new SessionManager(getApplicationContext());

        num = (EditText) findViewById(R.id.number);
        pass = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                final String num1 = num.getText().toString();
//                String pass1 = pass.getText().toString();
//
//
//                if (num1.isEmpty()) {
//                    num.setError("Enter Your Number");
//                    return;
//                }
//
//                if (pass1.isEmpty()) {
//                    pass.setError("Enter Your Password");
//                    return;
//                }
//
//
//                if (num1.length() < 10) {
//                    num.setError("Enter Correct Number");
//                    return;
//                }
//


                String num1 = num.getText().toString();
                String pass1 = pass.getText().toString();

                Log.e("number",""+num1+" "+pass1);




                final ProgressDialog pDialog = new ProgressDialog(Login.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject();

                    jsonObject.put("number", num1);
                    jsonObject.put("password", pass1);

                } catch (Exception e
                        ) {

                }


                Server.fetch2(getApplicationContext(), "https://www.swaggersbazar.in/api/food/login.php", jsonObject,"", new Server.OnResponseListener() {
                    @Override
                    public void onJSONResponse(Response response, String code) {
                        pDialog.dismiss();
                        try {

                            if (response.data.getString("status").equalsIgnoreCase("0"))
                            {


                                JSONObject jsonObject1 =response.data;
                                sessionManager.addString("number",jsonObject1.getString("number"));
                                sessionManager.addString("name",jsonObject1.getString("name"));
                                sessionManager.addString("address",jsonObject1.getString("address"));
                                sessionManager.addString("user_id",jsonObject1.getString("user_id"));

                                Intent xyz = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(xyz);
                                finish();
                                Toast.makeText(Login.this,""+response.data.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            else if (response.data.getString("status").equalsIgnoreCase("1"))
                            {
                                Toast.makeText(Login.this, ""+response.data.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            else {
                                Toast.makeText(Login.this, ""+response.data.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });













    }

}












