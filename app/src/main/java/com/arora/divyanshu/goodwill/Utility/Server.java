package com.arora.divyanshu.goodwill.Utility;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amitkumar on 9/13/16.
 */
public class Server {

    public static int SOCKET_TIMEOUT_MS = 30000;


    public static String URL = "http://www.guddukumar.com/account/api/";




    public static void fetchLogin(Context context, final String user, final String pass, final String token, final OnStringResponseListener listener) {
        Log.e("Requesting on ", user + " : " + pass);

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest jsonRequest = new StringRequest(Request.Method.POST, "https://newindianmatrimony.com/app/GetRSA.php" ,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("OnResponse of ", response.toString());
                        listener.onStringFetch(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("OnResponse of ", "" + error.toString());
                        listener.onStringFetch(null);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> pars = new HashMap<String, String>();
                pars.put("Content-Type", "application/x-www-form-urlencoded");
                return pars;
            }
           /* @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }*/


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_code", "AVBH73EJ55AM80HBMA");
                params.put("order_id", pass);
             //   params.put("grant_type", "password");
              //  params.put(Params.ANDROID_DEVICE_ID, token);

                return params;
            }

        };


        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonRequest);
    }//fetch login


    public static void fetch(Context context, String url, JSONObject json, final OnResponseListener listener) {
        Log.e("Requesting on " + url ,"");

        final String url1=url;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonRequest jsonRequest = new JsonRequest
                (Request.Method.GET, url,null, new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.e("OnResponse of " +url1, response.toString());
                        listener.onJSONResponse(decodeJsonRequests(response), "");
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("OnErrorResponse of " , error.toString());
                        //listener.onJSONResp
                        // onse(null, code);
                        if (error instanceof NoConnectionError)
                        {
                            Log.e("OnErrorResponse of " , error.toString());
                        }
                    }
                });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonRequest);
    }//fetch

    public static void fetchMain(Context context, String url, JSONObject json, final OnResponseListener listener) {
        Log.e("Requesting on " + url ,"");

        final String url1=url;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonRequest jsonRequest = new JsonRequest
                (Request.Method.POST, url,null, new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.e("OnResponse of " +url1, response.toString());
                        listener.onJSONResponse(decodeJsonRequestsMain(response), "");
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("OnErrorResponse of " , error.toString());
                        //listener.onJSONResp
                        // onse(null, code);
                        if (error instanceof NoConnectionError)
                        {
                            Log.e("OnErrorResponse of " , error.toString());
                        }
                    }
                });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonRequest);
    }//fetch

    public static void fetch2(Context context, String url, final JSONObject json, final String code, final OnResponseListener listener) {
        Log.e("Requesting on " + url ,""+json.toString());

        final String url1=url;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonRequest jsonRequest = new JsonRequest
                (Request.Method.POST, url,json, new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.e("OnResponse of " +url1, response.toString());
                        listener.onJSONResponse(decodeJsonObject(response), code);
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("OnErrorResponse of " + code, error.toString());
                        //listener.onJSONResp
                        // onse(null, code);
                        if (error instanceof NoConnectionError)
                        {
                            Log.e("OnErrorResponse of " + code, error.toString());
                        }else if (error instanceof ServerError) {
                            Log.e("Volley", "ServerError");
                        }
                        }
                });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonRequest);
    }//fetch

    public static void fetchPost(Context context, String url, JSONObject json, final String code, final OnResponseListener listener) {
        Log.e("Requesting on " + url ,""+json.toString());

        final String url1=url;
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        JsonRequest jsonRequest = new JsonRequest
                (Request.Method.POST, url,json, new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.e("OnResponse of " + code+url1, response.toString());
                        listener.onJSONResponse(decodeJson(response), code);
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("OnErrorResponse of " + code, error.toString());
                        //listener.onJSONResp
                        // onse(null, code);
                        if (error instanceof NoConnectionError)
                        {
                            Log.e("OnErrorResponse of " + code, error.toString());
                        }
                    }
                });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonRequest);
    }//fetch

    public static Response decodeJsonObject(JSONObject json) {
        Response response = new Response();

        try {
            if (json.getInt("status")==1 || json.getInt("status")==2 ) {
                response.success = true;

                response.data = json;
            }
            response.data = json;
        } catch (Exception ex) {
            Log.e("Server.decodeJson", ex.toString());
        }//try

        return response;
    }//decode json



    public static Response decodeJson(JSONObject json) {
        Response response = new Response();

        try {
           /* if (json.getBoolean("status")==true) {
                response.success = true;

                response.array = json.getJSONArray("data");
            }*/
            response.data = json;
        } catch (Exception ex) {
            Log.e("Server.decodeJson", ex.toString());
        }//try

        return response;
    }//decode json

    public static Response decodeJsonLogin(JSONObject json) {
        Response response = new Response();

        try {
            if (json.getBoolean("status")==true) {
                response.success = true;

                response.array = json.getJSONArray("data");
            }
        } catch (Exception ex) {
            Log.e("Server.decodeJson", ex.toString());
        }//try

        return response;
    }//decode json


    public static Response decodeJson1(JSONObject json) {
        Response response = new Response();

        try {
            if (json.getBoolean("status")==true) {
                response.success = true;

                response.array = json.getJSONArray("data");
            }
        } catch (Exception ex) {
            Log.e("Server.decodeJson", ex.toString());
        }//try

        return response;
    }//decode json

    public static Response decodeJsonRequestsMain(JSONObject json) {
        Response response = new Response();

        try {
           /* if (json.getBoolean("status")==true) {
                // response.success = true;
            }*/
            response.data = json;

        } catch (Exception ex) {
            Log.e("Server.decodeJson", ex.toString());
        }//try

        return response;
    }//decode json



    public static Response decodeJsonRequests(JSONObject json) {
        Response response = new Response();

        try {
           /* if (json.getBoolean("status")==true) {
                // response.success = true;
            }*/
                response.array = json.getJSONArray("data");

        } catch (Exception ex) {
            Log.e("Server.decodeJson", ex.toString());
        }//try

        return response;
    }//decode json


    public interface OnResponseListener {
        void onJSONResponse(Response response, String code);
      //  void onJsonErrorResponse()
    }//response listener

    public interface OnStringResponseListener {
        void onStringFetch(String str);
    }//string listener
}
