package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProdutoActivity extends AppCompatActivity {


    private Button btnAddCarrinho;
    private FloatingActionButton btnCarrinho;
    private int qtdProduto, idProduto;
    private TextView txtProduto, txtPrecoProduto;
    private String nomeProduto;
    private int precoProduto;
    private ItemCart itemCart;
    private SessionCarrinho sessionCarrinho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnAddCarrinho = (Button) findViewById(R.id.btnAddCarrinho);
        btnCarrinho = (FloatingActionButton) findViewById(R.id.btnCarrinho);
        txtProduto = (TextView) findViewById(R.id.txtProduto);
        txtPrecoProduto = (TextView) findViewById(R.id.txtPrecoProduto);

        nomeProduto = txtProduto.getText().toString();
        precoProduto = Integer.parseInt(txtPrecoProduto.getText().toString());

        sessionCarrinho = SessionCarrinho.getInstance();
        itemCart = new ItemCart(1, nomeProduto, 1, precoProduto);

        btnAddCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtdProduto = itemCart.getQuantidade();
                idProduto = itemCart.getIdProduto();
                sessionCarrinho.AddItemCarrinho(new ItemCart(idProduto, nomeProduto, qtdProduto, precoProduto));

                new SweetAlertDialog(ProdutoActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Oba!")
                        .setContentText("Produto já está no carrinho :)")
                        .showCancelButton(false)
                        .setConfirmText("Ok")
                        .setConfirmClickListener(null)
                        .show();

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
