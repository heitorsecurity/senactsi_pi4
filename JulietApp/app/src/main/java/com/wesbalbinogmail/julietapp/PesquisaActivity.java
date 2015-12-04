package com.wesbalbinogmail.julietapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PesquisaActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton btnSearch;
    private EditText edtSearch;
    private String paramBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        ListView listPesquisa = (ListView) findViewById(R.id.listPesquisa);
        listPesquisa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(PesquisaActivity.this, ErroActivity.class));
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramBusca = edtSearch.getText().toString();
                WsPesquisa wsPesquisa = new WsPesquisa();
                wsPesquisa.context = v.getContext();
                wsPesquisa.listPesquisa = (ListView) findViewById(R.id.listPesquisa);
                wsPesquisa.execute(paramBusca);
            }
        });
    }

    public class WsPesquisa extends AsyncTask<String, Void, String> {

        public Context context;
        public ListView listPesquisa;

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://tsitomcat.azurewebsites.net/julietg2/v1/buscarProduto/" + paramBusca);
                //URL url = new URL("http://10.0.3.2:8080/JulietG2WS/v1/buscarProduto/" + paramBusca);
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
                Intent erro = new Intent(PesquisaActivity.this, ErroActivity.class);
                startActivity(erro);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                PesquisaAdapter adapter = new PesquisaAdapter(context, jsonArray);
                listPesquisa.setAdapter(adapter);

            }catch (Exception e){
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
