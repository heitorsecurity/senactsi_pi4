package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinalizadaActivity extends AppCompatActivity {

    private Button btnVoltarCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizada);

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
}
