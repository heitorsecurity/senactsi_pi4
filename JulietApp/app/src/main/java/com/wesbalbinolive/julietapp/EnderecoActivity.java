package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnderecoActivity extends AppCompatActivity {

    public EditText edtCep;
    public EditText edtLogradouro;
    public EditText edtBairro;
    public EditText edtCidade;
    public String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtCep = (EditText) findViewById(R.id.edtCep);
        edtLogradouro = (EditText) findViewById(R.id.edtLogradouro);
        edtBairro = (EditText) findViewById(R.id.edtBairro);
        edtCidade = (EditText) findViewById(R.id.edtCidade);

        edtCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                valor = edtCep.getText().toString();

                WsEndereco wsEndereco = new WsEndereco();
                wsEndereco.execute(valor);
            }
        });

    }

    public class WsEndereco extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://viacep.com.br/ws/" + valor + "/json/");
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
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject json = new JSONObject(s);
                String cep = json.getString("cep");
                String logradouro = json.getString("logradouro");
                String bairro = json.getString("bairro");
                String cidade = json.getString("localidade");

                edtCep.setText(cep);
                edtLogradouro.setText(logradouro);
                edtBairro.setText(bairro);
                edtCidade.setText(cidade);

            }catch (Exception e){
                e.printStackTrace();
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
        }



        return super.onOptionsItemSelected(item);
    }
}
