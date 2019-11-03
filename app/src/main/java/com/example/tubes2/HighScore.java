package com.example.tubes2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HighScore {
    final String BASE_URL = "http://p3b.labftis.net/api.php";
    Presenter presenter;
    Gson gson;
    Context context;

    public HighScore(Presenter presenter, Context context){
        this.gson = new Gson();
        this.presenter = presenter;
        this.context = context;
    }


    void executePost(final String order, final String value){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("api_key", "2017730018");
                params.put("order", order);
                params.put("value", value);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    void executeGet(){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://p3b.labftis.net/api.php?api_key=2017730018&page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    processResult(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        });
        queue.add(stringRequest);
    }

    void processResult(String json) throws JSONException{
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        int score = jsonArray.getJSONObject(0).getInt("value");
        Log.d("Get",score+"");
        this.presenter.writeHighScore(score);
    }

}
