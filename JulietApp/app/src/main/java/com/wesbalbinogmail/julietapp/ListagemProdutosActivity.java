package com.wesbalbinogmail.julietapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListagemProdutosActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String idRecebido, idProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_produtos);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent idCategoriaRecebido = getIntent();
        idRecebido = idCategoriaRecebido.getStringExtra("idCategoria");

        Intent idProdutoRecebido = getIntent();
        idProduto = idProdutoRecebido.getStringExtra("idProduto");

        ListView listProdutosCategoria = (ListView) findViewById(R.id.listProdutosCategoria);
        listProdutosCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ListagemProdutosActivity.this, ErroActivity.class));
                finish();
            }
        });

        WsListagem wsListagem = new WsListagem();
        wsListagem.context = getBaseContext();
        wsListagem.listProdutosCategoria = (ListView) findViewById(R.id.listProdutosCategoria);
        wsListagem.execute(idRecebido);
    }

    public class WsListagem extends AsyncTask<String, Void, String> {

        public Context context;
        public ListView listProdutosCategoria;

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://tsitomcat.azurewebsites.net/julietg2/v1/buscaProdCategoria/" + idRecebido);
                //URL url = new URL("http://10.0.3.2:8080/JulietG2WS/v1/buscaProdCategoria/" + idRecebido);
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
                Intent erro = new Intent(ListagemProdutosActivity.this, ErroActivity.class);
                startActivity(erro);
                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                ListagemProdutosAdapter adapter = new ListagemProdutosAdapter(context, jsonArray);
                listProdutosCategoria.setAdapter(adapter);

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
                Intent intent = new Intent(ListagemProdutosActivity.this, SobreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(ListagemProdutosActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}