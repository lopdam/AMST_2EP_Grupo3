package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HeroActivity extends AppCompatActivity {

    private String id;
    private RequestQueue ListaRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        id = "1";
        ListaRequest = Volley.newRequestQueue(this);
        loadData();
    }

    private void loadData() {
        String token = "5443515708995737";
        String url = "https://www.superheroapi.com/api.php/" + token + "/" + id;

        JsonArrayRequest requestRegistros =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                            }
                        }, new Responseg.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<>();
                        params.put("Authorization", "JWT " + token);
                        return params;
                    }
                };
        ListaRequest.add(requestRegistros);
    }
}