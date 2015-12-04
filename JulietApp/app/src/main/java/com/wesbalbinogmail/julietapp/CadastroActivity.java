package com.wesbalbinogmail.julietapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNomeCadastro, edtEmailCadastro, edtSenhaCadastro, edtCpfCadastro;
    private Button btnCadastrar;
    private Toolbar mToolbar;
    private String nomeDigitado, senhaDigitada, emailDigitado, cpfDigitado, idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNomeCadastro = (EditText) findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro = (EditText) findViewById(R.id.edtEmailCadastro);
        edtSenhaCadastro = (EditText) findViewById(R.id.edtSenhaCadastro);
        edtCpfCadastro = (EditText) findViewById(R.id.edtCpfCadastro);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nomeDigitado = edtNomeCadastro.getText().toString();
                senhaDigitada= edtSenhaCadastro.getText().toString();
                emailDigitado = edtEmailCadastro.getText().toString();
                cpfDigitado = edtCpfCadastro.getText().toString();

                if((nomeDigitado.isEmpty()) || (senhaDigitada.isEmpty()) || (emailDigitado.isEmpty()) || (cpfDigitado.isEmpty())){
                    new SweetAlertDialog(CadastroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ops!")
                            .setContentText("Todos os campos são obrigatórios!")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                } else if ((nomeDigitado.length()< 3) || (nomeDigitado.length() > 20)) {
                    new SweetAlertDialog(CadastroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Opa!")
                            .setContentText("Usuário entre 6 e 20 caracteres")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                } else if ((senhaDigitada.length() < 6) || (senhaDigitada.length() > 20 )){
                    new SweetAlertDialog(CadastroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Vish!")
                            .setContentText("Senha entre 6 e 20 caracteres")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                } else if ((emailDigitado.length() < 10)){
                    new SweetAlertDialog(CadastroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Caracas!")
                            .setContentText("E-mail inválido")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                } else if ((cpfDigitado.length() < 10)){
                    new SweetAlertDialog(CadastroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Eita!")
                            .setContentText("CPF inválido")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                }  else {
                    WsCadastro wsCadastro = new WsCadastro();
                    wsCadastro.execute(nomeDigitado, senhaDigitada, emailDigitado, cpfDigitado);
                }
            }
        });
    }

    public class WsCadastro extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://tsitomcat.azurewebsites.net/julietg2/v1/registrarCliente/" + nomeDigitado + "/" + emailDigitado + "/" + senhaDigitada + "/" + cpfDigitado);
                //URL url = new URL("http://10.0.3.2:8080/JulietG2WS/v1/registrarCliente/" + nomeDigitado + "/" + emailDigitado + "/" + senhaDigitada + "/" + cpfDigitado);
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
                Intent erro = new Intent(CadastroActivity.this, ErroActivity.class);
                startActivity(erro);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                idCliente = jsonObject.getString("idCliente");
                Intent endereco = new Intent(CadastroActivity.this, EnderecoActivity.class);
                endereco.putExtra("idCliente", idCliente);
                startActivity(endereco);
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
                Intent intent = new Intent(CadastroActivity.this, SobreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(CadastroActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
