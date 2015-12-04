package com.wesbalbinogmail.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FinalizadaActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button btnVoltarCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizada);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnVoltarCompras = (Button)findViewById(R.id.btnVoltarCompras);

        btnVoltarCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categorias = new Intent(FinalizadaActivity.this, CategoriasActivity.class);
                startActivity(categorias);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}


