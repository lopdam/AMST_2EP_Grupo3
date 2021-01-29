package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HerosActivity extends AppCompatActivity {

    private  String search;
    private Context context;
    private String token="5443515708995737";
    private RequestQueue ListaRequest = null;
    private LinearLayout heroesContainer;
    private Map<String, TextView> heroesMarvel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heros);

        context=getApplicationContext();

        Intent intent=getIntent();
        search=intent.getStringExtra("search");

        Toast.makeText(context,search,Toast.LENGTH_SHORT).show();




        this.solicitarHerores();


    }

    public void solicitarHerores(){
        String url_registros ="https://www.superheroapi.com/api.php/"+token+"/search/bat";
        JsonArrayRequest requestRegistros = new JsonArrayRequest(Request.Method.GET, url_registros, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                mostrarTemperaturas(response);
//                actualizarGrafico(response);
                mostrarHeroes(response);

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
        JSONObject registroHeroe;
        Button nuevoRegistro;
        TextView valorRegistro;
//        contenedorTemperaturas = findViewById(R.id.cont_temperaturas);
        heroesContainer = findViewById(R.id.heros_container);
        LinearLayout.LayoutParams parametrosLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        try {
            for (int i = 0; i < temperaturas.length(); i++) {
                registroHeroe = (JSONObject) temperaturas.get(i);
                registroId = registroHeroe.getString("id");
                if (registroHeroe.getString("key").equals("temperatura")) {
                    if (heroesMarvel.containsKey(registroId) && fechasTVs.containsKey(registroId)) {
                        fechaRegistro = fechasTVs.get(registroId);
                        valorRegistro = heroesMarvel.get(registroId);
                        fechaRegistro.setText(registroHeroe.getString("date_created"));
                        valorRegistro.setText(registroHeroe.getString("value") + " C");
                    } else {
                        nuevoRegistro = new LinearLayout(this);
                        nuevoRegistro.setOrientation(LinearLayout.HORIZONTAL);
                        fechaRegistro = new TextView(this);
                        fechaRegistro.setLayoutParams(parametrosLayout);
                        fechaRegistro.setText(registroHeroe.getString("date_created"));

                        valorRegistro = new TextView(this);
                        valorRegistro.setLayoutParams(parametrosLayout);
                        valorRegistro.setText(registroHeroe.getString("value") + " C");
                        nuevoRegistro.addView(valorRegistro);
                        heroesContainer.addView(nuevoRegistro);
                        fechasTVs.put(registroId);
                        heroesMarvel.put(registroId, valorRegistro);
                    }
                } else {
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }
}