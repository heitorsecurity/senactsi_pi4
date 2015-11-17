package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CarrinhoActivity extends AppCompatActivity {

    private ViewGroup container;
    private TextView btnContinuarCompra, nomeView, precoView;
    private FloatingActionButton btnFinalizarCompra;
    private SessionUsuario sessionUsuario;
    private Double valorProduto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnContinuarCompra = (TextView) findViewById(R.id.btnContinuarCompra);
        btnFinalizarCompra = (FloatingActionButton) findViewById(R.id.btnFinalizarCompra);
        sessionUsuario = SessionUsuario.getInstance();


        SessionCarrinho sessionCarrinho = SessionCarrinho.getInstance();

        container = (ViewGroup) findViewById(R.id.container);

        for (ItemCart item: sessionCarrinho.getItens().values()){


            addItem(item.getNome(), String.valueOf(item.getValor()), String.valueOf(item.getQuantidade()), item.getIdProduto());

        }

        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionUsuario.getUsuarioLogado() == true) {
                    Intent checkout = new Intent(CarrinhoActivity.this, CheckoutActivity.class);
                    startActivity(checkout);

                } else {
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

    private  void addItem(String titulo, final String valor,String qtd,final int IdProduto){



        final LinearLayout linha = (LinearLayout) LayoutInflater.from(CarrinhoActivity.this).inflate(R.layout.cardview_row, container, false);
        nomeView = (TextView) linha.findViewById(R.id.nomeView);
        precoView = (TextView) linha.findViewById(R.id.precoView);
        final TextView qtdProdutoView = (TextView) linha.findViewById(R.id.qtdProdutoView);
        ImageView imageView = (ImageView) linha.findViewById(R.id.image);


        nomeView.setText(titulo);
        precoView.setText(valor);
        qtdProdutoView.setText(qtd);

        Button btnRemoveProduto = (Button) linha.findViewById( R.id.RemoveItem);
        btnRemoveProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(CarrinhoActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Remover produto")
                        .setCancelText("Não")
                        .setConfirmText("Sim")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                SessionCarrinho sessionCarrinho = SessionCarrinho.getInstance();
                                sessionCarrinho.deleteProduto(IdProduto);
                                container.removeView(linha);

                                sDialog
                                        .setTitleText("Pronto!")
                                        .setContentText("Produto deletado do carrinho")
                                        .setConfirmText("Ok")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

            }
        });

        Button btnSubtrai = (Button) linha.findViewById(R.id.subtrai);
        btnSubtrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionCarrinho sessionCarrinho = SessionCarrinho.getInstance();
                sessionCarrinho.subtraiProduto(IdProduto, 1);

                valorProduto = Double.valueOf(precoView.getText().toString());


                if(Integer.parseInt((String) qtdProdutoView.getText()) > 1) {
                    qtdProdutoView.setText(String.valueOf(Integer.parseInt((String) qtdProdutoView.getText()) - 1));
                    precoView.setText(String.valueOf(Double.parseDouble((String) precoView.getText()) - valorProduto));
                }else{
                    container.removeView(linha);
                }
            }
        });

        Button btnSoma = (Button) linha.findViewById(R.id.soma);
        btnSoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                valorProduto = Double.valueOf(precoView.getText().toString());

                SessionCarrinho sessionCarrinho = SessionCarrinho.getInstance();
                sessionCarrinho.somaProduto(IdProduto, 1);
                qtdProdutoView.setText(String.valueOf(Integer.parseInt((String) qtdProdutoView.getText()) + 1));
                precoView.setText(String.valueOf(Double.parseDouble((String) precoView.getText()) + valorProduto));

            }
        });

        container.addView(linha);
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
