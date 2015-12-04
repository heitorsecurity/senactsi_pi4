package com.wesbalbinogmail.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CheckoutActivity extends AppCompatActivity {

    private EditText edtNrCartao, edtValidadeCartao, edtQtdParcelas;
    private RadioButton rdbCartao, rdbBoleto;
    private Button btnEfetuarPagamento;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rdbCartao = (RadioButton) findViewById(R.id.rdbCartao);
        rdbBoleto = (RadioButton) findViewById(R.id.rdbBoleto);

        edtNrCartao = (EditText) findViewById(R.id.edtNrCartao);
        edtValidadeCartao = (EditText) findViewById(R.id.edtValidadeCartao);
        edtQtdParcelas = (EditText) findViewById(R.id.edtQtdParcelas);

        btnEfetuarPagamento = (Button) findViewById(R.id.btnEfetuarPagmento);

        rdbBoleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rdbBoleto.isChecked()) {
                    edtNrCartao.setEnabled(false);
                    edtValidadeCartao.setEnabled(false);
                    edtQtdParcelas.setEnabled(false);
                } else{
                    edtNrCartao.setEnabled(true);
                    edtValidadeCartao.setEnabled(true);
                    edtQtdParcelas.setEnabled(true);
                }
            }
        });

        rdbCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdbCartao.isChecked()) {
                    edtNrCartao.setEnabled(true);
                    edtValidadeCartao.setEnabled(true);
                    edtQtdParcelas.setEnabled(true);
                }
            }
        });

        btnEfetuarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdbCartao.isChecked()) {
                    if (edtNrCartao.getText().toString().length() < 11 || edtNrCartao.getText().toString() == ""){

                        new SweetAlertDialog(CheckoutActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ops")
                                .setContentText("Número do cartão inválido")
                                .showCancelButton(false)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(null)
                                .show();

                    } else if (edtValidadeCartao.getText().toString().length() < 8 || edtValidadeCartao.getText().toString() == ""){

                        new SweetAlertDialog(CheckoutActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ops")
                                .setContentText("Validade incorreta")
                                .showCancelButton(false)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(null)
                                .show();


                    } else if (edtQtdParcelas.getText().toString().length() < 2 || edtQtdParcelas.getText().toString() == ""){

                        new SweetAlertDialog(CheckoutActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ops")
                                .setContentText("Máximo de 20 parcelas")
                                .showCancelButton(false)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(null)
                                .show();
                    } else{
                        Intent finalizado = new Intent(CheckoutActivity.this, FinalizadaActivity.class);
                        startActivity(finalizado);
                        finish();
                    }
                } else{
                    Intent finalizado = new Intent(CheckoutActivity.this, FinalizadaActivity.class);
                    startActivity(finalizado);
                    finish();
                }
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
        }
        return super.onOptionsItemSelected(item);
    }
}
