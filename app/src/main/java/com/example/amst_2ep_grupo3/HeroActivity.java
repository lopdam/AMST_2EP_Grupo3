package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroActivity extends AppCompatActivity {
    public BarChart graficoBarras;

    private String id;
    private String nombre;
    private String nombreCompleto;
    private Context context;
    private RequestQueue requestQueue = null;
    private String token = "5443515708995737";
    private RequestQueue ListaRequest = null;
    private TextView txtNombre;
    private TextView txtNombreCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        context = this;
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nombre = intent.getStringExtra("nombre");
        nombreCompleto = intent.getStringExtra("nombreCompleto");
        System.out.println("este es el id: " + id);
        ListaRequest = Volley.newRequestQueue(this);
        this.iniciarGrafico();
        solicitarHeroe(id);
        solicitarDatosHeroe(id);
    }

    public void solicitarHeroe(String id) {
        System.out.println(id);
        String url_registros = "https://www.superheroapi.com/api.php/" + token + "/" + id + "/powerstats";
        System.out.println(url_registros);
        JsonObjectRequest requestRegistros = new JsonObjectRequest(Request.Method.GET, url_registros, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                txtNombre = findViewById(R.id.txtNombre);
                txtNombre.setText(nombre);
                actualizarGrafico(response);
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

    public void solicitarDatosHeroe(String id) {
        System.out.println(id);
        String url_registros = "https://www.superheroapi.com/api.php/" + token + "/" + id + "/biography";
        System.out.println(url_registros);
        JsonObjectRequest requestRegistros = new JsonObjectRequest(Request.Method.GET, url_registros, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                actualizarDatos(response);
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

    private void actualizarDatos(JSONObject temperaturas) {
        try {
            String fullname = temperaturas.getString("full-name");
            System.out.println(fullname);
            txtNombreCompleto = findViewById(R.id.txtNombreCompleto);
            txtNombreCompleto.setText(fullname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void actualizarGrafico(JSONObject temperaturas) {
        JSONObject registro_temp;
        String inteligencia;
        String fuerza;
        String velocidad;
        String durabilidad;
        String poder;
        String combate;
        ArrayList<BarEntry> dato_temp = new ArrayList<>();
        try {
            inteligencia = temperaturas.getString("intelligence");
            System.out.println(inteligencia);
            fuerza = temperaturas.getString("strength");
            velocidad = temperaturas.getString("speed");
            durabilidad = temperaturas.getString("durability");
            poder = temperaturas.getString("power");
            combate = temperaturas.getString("combat");

            dato_temp.add(new BarEntry(0, Integer.parseInt(inteligencia)));
            dato_temp.add(new BarEntry(1, Integer.parseInt(fuerza)));
            dato_temp.add(new BarEntry(2, Integer.parseInt(velocidad)));
            dato_temp.add(new BarEntry(3, Integer.parseInt(durabilidad)));
            dato_temp.add(new BarEntry(4, Integer.parseInt(poder)));
            dato_temp.add(new BarEntry(5, Integer.parseInt(combate)));

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
        Legend l = graficoBarras.getLegend();
        ArrayList<String> labelsname = new ArrayList<>();
        String[] nombresOperacion = {"inteligencia", "fuerza", "velocidad", "durabilidad", "poder", "combate"};
        for (int i = 0; nombresOperacion.length > i; i++) {
            String tipo = nombresOperacion[i];
            labelsname.add(tipo);

        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsname));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        graficoBarras.getAxisLeft().setDrawGridLines(false);
        graficoBarras.animateY(1500);
        graficoBarras.getLegend().setEnabled(false);
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