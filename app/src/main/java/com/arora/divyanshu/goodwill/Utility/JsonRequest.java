package com.arora.divyanshu.goodwill.Utility;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by amitkumar on 9/12/16.
 */
public class JsonRequest extends JsonObjectRequest {

    public static String token = "";

    public JsonRequest(int method, String url, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

   /* @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> param = new HashMap<String,String>();
        param.put("Authorization", "bearer " + token);
        param.put("content-type","application/json; charset=utf-8");
        return param;
    }*/

}