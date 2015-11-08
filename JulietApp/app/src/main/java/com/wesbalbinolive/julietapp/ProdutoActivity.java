package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ProdutoActivity extends AppCompatActivity {


    private Button btnAddCarrinho;
    private FloatingActionButton btnCarrinho;
    private int qtdProduto;
    private SessionProduto data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnAddCarrinho = (Button) findViewById(R.id.btnAddCarrinho);
        btnCarrinho = (FloatingActionButton) findViewById(R.id.btnCarrinho);
        data = SessionProduto.getInstance();

        btnAddCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtdProduto = data.getQuantidade();
                qtdProduto++;
                data.setQuantidade(qtdProduto);

                if (qtdProduto > 1){
                    Snackbar.make(view, qtdProduto + " produtos adicionados ao carrinho", Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(view, qtdProduto + " produto adicionado ao carrinho", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        btnCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carrinho = new Intent(ProdutoActivity.this, CarrinhoActivity.class);
                startActivity(carrinho);
            }
        });
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
                Intent intent = new Intent(ProdutoActivity.this, SobreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(ProdutoActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
