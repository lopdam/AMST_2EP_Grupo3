package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

public class HeroActivity extends AppCompatActivity {
    public BarChart graficoBarras;

    private String id;
    private Context context;
    private RequestQueue requestQueue = null;
    private String token="5443515708995737";
    private RequestQueue ListaRequest = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        context = this;
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        //id = intent.getStringExtra("id");
        System.out.println("este es el id: "+id);
//        requestQueue = Volley.newRequestQueue(context);
        ListaRequest = Volley.newRequestQueue(this);
        this.iniciarGrafico();
        solicitarHeroe(id);
    }

    public void solicitarHeroe(String id){
        System.out.println(id);
        String url_registros ="https://www.superheroapi.com/api.php/"+token+"/"+id;
        System.out.println(url_registros);
        JsonObjectRequest requestRegistros = new JsonObjectRequest(Request.Method.GET, url_registros, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject  response) {
                try {
                    JSONArray myJsonArray = response.getJSONArray("powerstats");
//                    showData(myJsonArray);
//                    System.out.println(myJsonArray);
                    actualizarGrafico(myJsonArray);
                } catch (JSONException e) {

                    e.printStackTrace();
                }

//                System.out.println("hola people");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
//                System.out.println("erorwaw");
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
    private void actualizarGrafico(JSONArray temperaturas) {
        JSONObject registro_temp;
        String inteligencia;
        String fuerza;
        String velocidad;
        String durabilidad;
        String poder;
        String combate;

//        String date;
        int count = 0;
        float temp_val;
        ArrayList<BarEntry> dato_temp = new ArrayList<>();
        try {
            for (int i = 0; i < temperaturas.length(); i++) {
                registro_temp = (JSONObject) temperaturas.get(i);

//                    temp = registro_temp.getString("value");
//                    date = registro_temp.getString("date_created");
//                    temp_val = Float.parseFloat(temp);
                    inteligencia= String.valueOf(temperaturas.get(1));
//                    fuerza =registro_temp.getString("strength");
//                    velocidad=registro_temp.getString("speed");
//                    durabilidad=registro_temp.getString("durability");
//                    poder=registro_temp.getString("power");
//                    combate=registro_temp.getString("combat");



                    dato_temp.add(new BarEntry(count,Integer.parseInt(inteligencia)));
//                    dato_temp.add(new BarEntry(count,Integer.parseInt(fuerza)));
//                    dato_temp.add(new BarEntry(count,Integer.parseInt(velocidad)));
//                    dato_temp.add(new BarEntry(count,Integer.parseInt(durabilidad)));
//                    dato_temp.add(new BarEntry(count,Integer.parseInt(poder)));
//                    dato_temp.add(new BarEntry(count,Integer.parseInt(combate)));



                    count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("error");
        }
        System.out.println(dato_temp);
        llenarGrafico(dato_temp);
    }

    public void iniciarGrafico() {
        graficoBarras = findViewById(R.id.barChart);
        graficoBarras.getDescription().setEnabled(false);
        graficoBarras.setMaxVisibleValueCount(60);
        graficoBarras.setPinchZoom(false);
        graficoBarras.setDrawBarShadow(false);
        graficoBarras.setDrawGridBackground(false);
        XAxis xAxis = graficoBarras.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        graficoBarras.getAxisLeft().setDrawGridLines(false);
        graficoBarras.animateY(1500);
        graficoBarras.getLegend().setEnabled(false);
        System.out.println("sali bien");
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

    private void llenarGrafico(ArrayList<BarEntry> dato_temp) {
        BarDataSet temperaturasDataSet;
        if (graficoBarras.getData() != null && graficoBarras.getData().getDataSetCount() > 0) {
            temperaturasDataSet = (BarDataSet) graficoBarras.getData().getDataSetByIndex(0);
            temperaturasDataSet.setValues(dato_temp);
            graficoBarras.getData().notifyDataChanged();
            graficoBarras.notifyDataSetChanged();
        } else {
            temperaturasDataSet = new BarDataSet(dato_temp, "Data Set");
            temperaturasDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            temperaturasDataSet.setDrawValues(true);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(temperaturasDataSet);
            BarData data = new BarData(dataSets);
            graficoBarras.setData(data);
            graficoBarras.setFitBars(true);
        }
        graficoBarras.invalidate();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                solicitarHeroe(id);
            }
        };
        handler.postDelayed(runnable, 3000);
    }
}