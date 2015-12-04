package com.wesbalbinogmail.julietapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TermosActivity extends AppCompatActivity {

    public TextView btnSair;
    public Button btnAceito;
    public CheckBox checkBox;
    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);

       prefs = getSharedPreferences("termosConfig", MODE_PRIVATE);

        if (prefs.getBoolean("termosConfig", true)) {
            btnSair = (TextView) findViewById(R.id.btnSair);
            btnAceito = (Button) findViewById(R.id.btnAceito);
            checkBox = (CheckBox) findViewById(R.id.checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {

                        btnAceito.setBackgroundColor(Color.parseColor("#37474F"));
                    } else{

                        btnAceito.setBackgroundColor(Color.parseColor("#9c9c9c"));
                    }
                }
            });

            btnAceito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        SharedPreferences.Editor prefEditor = prefs.edit();
                        prefEditor.putBoolean("termosConfig", false);
                        prefEditor.apply();

                        Intent home = new Intent(TermosActivity.this, HomeActivity.class);
                        startActivity(home);
                        finish();
                    } else {
                        new SweetAlertDialog(TermosActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Ops")
                                .setContentText("Aceite os termos para avançar")
                                .showCancelButton(false)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(null)
                                .show();
                    }
                }
            });

            btnSair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else{
            Intent intent = new Intent(TermosActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
