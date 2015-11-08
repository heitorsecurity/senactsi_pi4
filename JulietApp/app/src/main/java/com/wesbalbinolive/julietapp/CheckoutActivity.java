package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class CheckoutActivity extends AppCompatActivity {

    private EditText edtNrCartao, edtValidadeCartao, edtQtdParcelas;
    private RadioButton rdbCartao, rdbBoleto;
    private Button btnEfetuarPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

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
                        Snackbar.make(view, "Número do cartão inválido", Snackbar.LENGTH_LONG).show();
                    } else if (edtValidadeCartao.getText().toString().length() < 10 || edtValidadeCartao.getText().toString() == ""){
                        Snackbar.make(view, "Validade incorreta", Snackbar.LENGTH_LONG).show();

                    } else if (edtQtdParcelas.getText().toString().length() < 2 || edtQtdParcelas.getText().toString() == ""){
                        Snackbar.make(view, "Valor inválido", Snackbar.LENGTH_LONG).show();
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
}
