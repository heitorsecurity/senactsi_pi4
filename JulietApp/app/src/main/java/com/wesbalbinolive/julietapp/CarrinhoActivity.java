package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarrinhoActivity extends AppCompatActivity {

    private ViewGroup container;
    private TextView btnContinuarCompra;
    private FloatingActionButton btnFinalizarCompra;
    private CardView cardView;
    private int qtdProduto;
    private SessionProduto data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnContinuarCompra = (TextView) findViewById(R.id.btnContinuarCompra);
        btnFinalizarCompra = (FloatingActionButton) findViewById(R.id.btnFinalizarCompra);
        container = (ViewGroup) findViewById(R.id.container);

        data = SessionProduto.getInstance();

        qtdProduto = data.getQuantidade();

        for (int i = 0; i < qtdProduto; i++){
            addItem();
        }

        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.getUsuarioLogado() == true){
                        Intent checkout = new Intent(CarrinhoActivity.this, CheckoutActivity.class);
                        startActivity(checkout);

                    } else{
                        Intent login = new Intent(CarrinhoActivity.this, LoginActivity.class);
                        startActivity(login);
                    }
                }
            });

        btnContinuarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categorias = new Intent(CarrinhoActivity.this, CategoriasActivity.class);
                startActivity(categorias);
            }
        });
    }

    private void addItem(){
        cardView = (CardView) LayoutInflater.from(CarrinhoActivity.this).inflate(R.layout.cardview_carrinho, container, false);
        container.addView(cardView);
    }

    private void removeItem() {
            container.removeView(cardView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            case R.id.action_sobre:
                Intent intent = new Intent(CarrinhoActivity.this, SobreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(CarrinhoActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
