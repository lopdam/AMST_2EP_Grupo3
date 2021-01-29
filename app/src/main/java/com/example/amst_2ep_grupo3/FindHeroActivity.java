package com.example.amst_2ep_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FindHeroActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hero);
        context=getApplicationContext();
    }

    public void buscarHeroe(View view){
        Intent intent =new Intent(context,HerosActivity.class);
        String search=((EditText)findViewById(R.id.input_hero_name)).getText().toString();
        intent.putExtra("search",search);
        startActivity(intent);

    }
}