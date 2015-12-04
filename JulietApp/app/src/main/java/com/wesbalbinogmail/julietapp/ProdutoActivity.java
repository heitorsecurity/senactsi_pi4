package com.wesbalbinogmail.julietapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProdutoActivity extends AppCompatActivity {

    private Button btnAddCarrinho;
    private FloatingActionButton btnCarrinho;
    private int idProdutoInteiro;
    private TextView txtNomeProduto, txtPrecoProduto, txtDescricaoProduto, txtDescontoProduto;
    private String nomeProduto, descricaoProduto, idProdutoRecebido;
    private double precoProduto, descontoPromocao, precoFinal;
    private Toolbar mToolbar;
    private SessionCarrinho sessionCarrinho;
    private ImageView imgProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        Intent intent = getIntent();
        idProdutoRecebido = intent.getStringExtra("idProduto");
        idProdutoInteiro = Integer.parseInt(idProdutoRecebido);

        WsProduto wsProduto = new WsProduto();
        wsProduto.execute(idProdutoRecebido);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAddCarrinho = (Button) findViewById(R.id.btnAddCarrinho);
        btnCarrinho = (FloatingActionButton) findViewById(R.id.btnCarrinho);
        txtNomeProduto = (TextView) findViewById(R.id.txtNomeProduto);
        txtDescricaoProduto = (TextView) findViewById(R.id.txtDescricaoProduto);
        txtDescontoProduto = (TextView) findViewById(R.id.txtDescontoProduto);
        txtPrecoProduto = (TextView) findViewById(R.id.txtPrecoProduto);
        imgProduto = (ImageView) findViewById(R.id.imgProduto);

        sessionCarrinho = SessionCarrinho.getInstance();

        btnAddCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionCarrinho.AddItemCarrinho(new ItemCart(idProdutoInteiro, nomeProduto, 1, precoProduto));

                new SweetAlertDialog(ProdutoActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Oba!")
                        .setContentText("Produto foi adicionado no carrinho :)")
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

    public class WsProduto extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://tsitomcat.azurewebsites.net/julietg2/v1/detalheProduto/" + idProdutoRecebido);
                //URL url = new URL("http://10.0.3.2:8080/JulietG2WS/v1/detalheProduto/" + idProdutoRecebido);
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
                Intent erro = new Intent(ProdutoActivity.this, ErroActivity.class);
                startActivity(erro);
                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                nomeProduto = jsonObject.getString("nomeProduto");
                precoProduto = jsonObject.getDouble("precProduto");
                descontoPromocao = jsonObject.getDouble("descontoPromocao");
                descricaoProduto = jsonObject.getString("descProduto");

                precoFinal = precoProduto - descontoPromocao;
                String descontoPromocaoString = String.valueOf(precoFinal);
                String precoProdutoString = String.valueOf(precoProduto);

                String imageProduto = jsonObject.getString("imagemBASE64");
                byte[] decodedString = Base64.decode(imageProduto, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                txtNomeProduto.setText(nomeProduto);
                txtDescontoProduto.setText(descontoPromocaoString);
                txtPrecoProduto.setText(precoProdutoString);
                txtDescricaoProduto.setText(descricaoProduto);
                imgProduto.setImageBitmap(decodedByte);

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
