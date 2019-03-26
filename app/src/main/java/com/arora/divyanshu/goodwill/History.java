package com.arora.divyanshu.goodwill;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.arora.divyanshu.goodwill.Utility.Response;
import com.arora.divyanshu.goodwill.Utility.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {


        ArrayList<History_Model>historyDatas= new ArrayList<History_Model>();
        HistoryDetails_Adapter historyAdapter;

        ListView historyDetailsList;

        public static String debit1,credit1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            showHistory();

            historyDetailsList=(ListView)findViewById(R.id.transHistoryList);


        }


        public void showHistory()
        {
                final ProgressDialog pDialog = new ProgressDialog(History.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject();



                    Log.e("on request", "  " + Server.URL +"account_history" + jsonObject.toString());


                    final JSONObject finalJsonObject = jsonObject;
                    Server.fetchPost(getApplicationContext(), Server.URL +"account_history", jsonObject, "", new Server.OnResponseListener() {
                        @Override
                        public void onJSONResponse(Response response, String code) {
                            pDialog.dismiss();


                            try {



                                JSONArray array=response.data.getJSONArray("list");
                                for (int i = 0; i < array.length(); i++)
                                {

                                    JSONObject object = array.getJSONObject(i);

                                    History_Model data = new History_Model();

                                    String date1 = object.getString("date");
                                    String info1 = object.getString("transtion_info");
                                     debit1 = object.getString("debit");
                                     credit1 = object.getString("credit");
                                    String Balance1 = object.getString("balance");






                                    data.setUdate(date1);
                                    data.setCredit(credit1);
                                    data.setDebit(debit1);
                                    data.setBalance(Balance1);
                                    data.setInfo(info1);


                                    historyDatas.add(data);

    //                        Toast.makeText(Payment_Details.this, ""+response.data, Toast.LENGTH_SHORT).show();
                                }
                                historyAdapter = new HistoryDetails_Adapter(getApplicationContext(),historyDatas);
                                historyDetailsList.setAdapter(historyAdapter);

                            }


                            catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(History.this, ""+response.data, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {

                }




            }





    }




