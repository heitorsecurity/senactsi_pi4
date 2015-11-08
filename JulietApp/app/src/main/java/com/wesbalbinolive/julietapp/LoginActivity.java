package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView btnCadastro;
    private EditText edtUsuarioLogin, edtUsuarioSenha;
    private Button btnEntrar;
    private String usuarioDigitado, senhaDigitada;
    private SessionProduto data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        edtUsuarioLogin = (EditText) findViewById(R.id.edtUsuarioLogin);
        edtUsuarioSenha = (EditText) findViewById(R.id.edtSenhaLogin);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCadastro = (TextView) findViewById(R.id.btnCadastro);


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = SessionProduto.getInstance();
                usuarioDigitado = edtUsuarioLogin.getText().toString();
                senhaDigitada = edtUsuarioSenha.getText().toString();
                data.setLogin(usuarioDigitado);
                data.setSenha(senhaDigitada);
                data.setUsuarioLogado(true);
                Intent checkout = new Intent(LoginActivity.this, CheckoutActivity.class);
                startActivity(checkout);
                finish();
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
