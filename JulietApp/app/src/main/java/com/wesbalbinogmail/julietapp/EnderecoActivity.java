package com.wesbalbinogmail.julietapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class EnderecoActivity extends AppCompatActivity {
    private EditText edtCep, edtNumero, edtLogradouro;
    private Button btnConcluir;
    private String cepDigitado, logradouroDigitado, numeroDigitado, idCliente, nomeDigitado;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        idCliente = intent.getStringExtra("idCliente");

        edtCep = (EditText) findViewById(R.id.edtCep);
        edtLogradouro = (EditText) findViewById(R.id.edtLogradouro);
        edtNumero = (EditText) findViewById(R.id.edtNumero);
        btnConcluir = (Button) findViewById(R.id.btnConcluir);

        edtCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cepDigitado = edtCep.getText().toString();
                WsCep wsCep = new WsCep();
                wsCep.execute(cepDigitado);
            }
        });

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cepDigitado = edtCep.getText().toString();
                logradouroDigitado= edtLogradouro.getText().toString();
                numeroDigitado = edtNumero.getText().toString();
                nomeDigitado = "Casa";

                if((cepDigitado.isEmpty()) || (logradouroDigitado.isEmpty()) || (numeroDigitado.isEmpty())){
                    new SweetAlertDialog(EnderecoActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ops!")
                            .setContentText("Todos os campos são obrigatórios!")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                }  else {
                    new SweetAlertDialog(EnderecoActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Aee!")
                            .setContentText("Usuário cadastrado com sucesso :)")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    WsEndereco wsEndereco = new WsEndereco();
                                    wsEndereco.execute(idCliente, nomeDigitado,logradouroDigitado, numeroDigitado, cepDigitado);
                                }
                            })
                            .show();

                }
            }
        });
    }

    public class WsCep extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://viacep.com.br/ws/" + cepDigitado + "/json/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;

                while ((inputStr = reader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                String respostaCompleta = responseStrBuilder.toString();
                return  respostaCompleta;
            } catch (Exception e){
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject json = new JSONObject(s);
                String cep = json.getString("cep");
                String logradouro = json.getString("logradouro");
                edtCep.setText(cep);
                edtLogradouro.setText(logradouro);

            }catch (Exception e){
            }
        }
    }

    public class WsEndereco extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://tsitomcat.azurewebsites.net/julietg2/v1/registrarEndereco/" + idCliente + "/" + nomeDigitado + "/" + logradouroDigitado + "/" + numeroDigitado + "/" + cepDigitado);
                //URL url = new URL("http://10.0.3.2:8080/JulietG2WS/v1/registrarEndereco/" + idCliente + "/" + nomeDigitado + "/" + logradouroDigitado + "/" + numeroDigitado + "/" + cepDigitado);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;

                while ((inputStr = reader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                String respostaCompleta = responseStrBuilder.toString();
                return  respostaCompleta;
            } catch (Exception e){
                Intent erro = new Intent(EnderecoActivity.this, ErroActivity.class);
                startActivity(erro);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                finish();

            }catch (Exception e){
            }
        }
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
                Intent intent = new Intent(EnderecoActivity.this, SobreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(EnderecoActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
