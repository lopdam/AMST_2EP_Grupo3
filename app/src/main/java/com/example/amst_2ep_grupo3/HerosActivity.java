package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class HerosActivity extends AppCompatActivity {

    private  String search;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heros);

        context=getApplicationContext();

        Intent intent=getIntent();
        search=intent.getStringExtra("search");

        Toast.makeText(context,search,Toast.LENGTH_SHORT).show();

    }
}