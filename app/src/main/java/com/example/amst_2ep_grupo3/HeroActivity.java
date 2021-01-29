package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HeroActivity extends AppCompatActivity {

    private String id;
    private Context context;
    private RequestQueue requestQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        context = this;
        Intent intent = getIntent();
        //id = intent.getStringExtra("id");
        id = "1";
        requestQueue = Volley.newRequestQueue(context);
        loadData();
    }

    private void loadData() {
        String token = "5443515708995737";
        String url = "https://www.superheroapi.com/api.php/" + token + "/" + id;
        url = "https://www.superheroapi.com/api.php/5443515708995737/1";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        showData(response);
                        Log.d("Hero", response.toString());
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Hero", error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);

    }

    public void showData(JSONArray data) {


        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject registro_data = (JSONObject) data.get(i);
                Log.d("Hero", registro_data.toString());

                if (registro_data.getString("key").equals("powerstats")) {

                    JSONArray dataHero = data.getJSONArray(i);
                    Log.d("Hero", dataHero.toString());

                    for (int k = 0; k < dataHero.length(); k++) {
                        JSONObject habilidad = (JSONObject) dataHero.get(k);
                        String nameHabilidad = habilidad.getString("key");
                        String valueHabilidad = habilidad.getString("value");


                        Toast.makeText(context, nameHabilidad + ":" + valueHabilidad, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("error");
        }


    }
}