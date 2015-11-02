package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CarrinhoActivity extends AppCompatActivity {

    private ViewGroup container;
    private TextView btnContinuarCompra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnContinuarCompra = (TextView) findViewById(R.id.btnContinuarCompra);
        container = (ViewGroup) findViewById(R.id.container);

        for (int i = 0; i < 10; i++){
            addItem();
        }

        btnContinuarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(CarrinhoActivity.this, HomeActivity.class);
                startActivity(home);
            }
        });

    }

    private void addItem(){
        CardView cardView = (CardView) LayoutInflater.from(CarrinhoActivity.this).inflate(R.layout.cardview_carrinho, container, false);

        container.addView(cardView);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
