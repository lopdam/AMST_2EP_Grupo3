package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HerosActivity extends AppCompatActivity {

    private String search;
    private Context context;
    private String token = "5443515708995737";
    private RequestQueue ListaRequest = null;
    private LinearLayout heroesContainer;
    private TextView heros_result;
    private Map<String, TextView> heroesMarvel;
    private HerosActivity contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heros);
        context = getApplicationContext();
        Intent intent = getIntent();
        search = intent.getStringExtra("search");
        Toast.makeText(context, search, Toast.LENGTH_SHORT).show();

        heroesMarvel = new HashMap<String, TextView>();
        ListaRequest = Volley.newRequestQueue(this);
        contexto = this;
        this.solicitarHeroes(search);


    }

    public void solicitarHeroes(String name) {
        System.out.println(name);
        String url_registros = "https://www.superheroapi.com/api.php/" + token + "/search/" + name;
        JsonObjectRequest requestRegistros = new JsonObjectRequest(Request.Method.GET, url_registros, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myJsonArray = response.getJSONArray("results");
                    mostrarHeroes(myJsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT " + token);
                return params;
            }
        };
        ListaRequest.add(requestRegistros);

    }

    private void mostrarHeroes(JSONArray heroes) {
        String registroId;
        String registroNombre;
        String registroNombreCompleto;
        JSONObject registroHeroe;
        TextView valorRegistro;
        heroesContainer = findViewById(R.id.heros_container);
        heros_result = findViewById(R.id.heros_result);
        LinearLayout.LayoutParams parametrosLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        try {
            for (int i = 0; i < heroes.length(); i++) {
                registroHeroe = (JSONObject) heroes.get(i);
                registroId = registroHeroe.getString("id");
                registroNombre = registroHeroe.getString("name");

                System.out.println("hoooooola");
                System.out.println(registroId);
                System.out.println(registroNombre);
                Button myButton = new Button(this);
                myButton.setText(registroNombre);
                String finalRegistroId = registroId;
                String finalRegistroNombre = registroNombre;
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, HeroActivity.class);
                        intent.putExtra("id", finalRegistroId);
                        intent.putExtra("nombre", finalRegistroNombre);

                        startActivity(intent);
                    }
                });
                heroesContainer.addView(myButton);
            }
            heros_result.setText(String.valueOf(heroes.length()));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }
}