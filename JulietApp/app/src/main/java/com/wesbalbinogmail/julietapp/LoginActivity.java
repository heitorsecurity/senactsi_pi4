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
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    private TextView btnCadastro;
    private EditText edtUsuarioLogin, edtUsuarioSenha;
    private Button btnEntrar;
    private String loginDigitado, senhaDigitada, emailCliente;
    private SessionUsuario sessionUsuario;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtUsuarioLogin = (EditText) findViewById(R.id.edtUsuarioLogin);
        edtUsuarioSenha = (EditText) findViewById(R.id.edtSenhaLogin);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCadastro = (TextView) findViewById(R.id.btnCadastro);

        sessionUsuario = SessionUsuario.getInstance();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginDigitado = edtUsuarioLogin.getText().toString();
                senhaDigitada = edtUsuarioSenha.getText().toString();

                if ((loginDigitado.isEmpty()) || (senhaDigitada.isEmpty())) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ops!")
                            .setContentText("Login ou senha inválido!")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                } else {
                    WsLogin wsLogin = new WsLogin();
                    wsLogin.execute(loginDigitado, senhaDigitada);
                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastro = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(cadastro);
            }
        });

    }

    public class WsLogin extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://tsitomcat.azurewebsites.net/julietg2/v1/loginCliente/" + loginDigitado + "/" + senhaDigitada);
                //URL url = new URL("http://10.0.3.2:8080/JulietG2WS/v1/loginCliente/" + loginDigitado + "/" + senhaDigitada);
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
                Intent erro = new Intent(LoginActivity.this, ErroActivity.class);
                startActivity(erro);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                emailCliente = jsonObject.getString("emailCliente");

                if (loginDigitado.equals(emailCliente)) {
                    sessionUsuario.setUsuarioLogado(true);
                    Intent checkout = new Intent(LoginActivity.this, CheckoutActivity.class);
                    startActivity(checkout);
                    finish();
                }
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
                Intent intent = new Intent(LoginActivity.this, SobreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(LoginActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
