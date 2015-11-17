package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNomeCadastro, edtEmailCadastro, edtSenhaCadastro;
    private Button btnCadastrar;
    private SessionUsuario sessionUsuario;
    private String nomeDigitado, senhaDigitada, emailDigitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        edtNomeCadastro = (EditText) findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro = (EditText) findViewById(R.id.edtEmailCadastro);
        edtSenhaCadastro = (EditText) findViewById(R.id.edtSenhaCadastro);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionUsuario = SessionUsuario.getInstance();
                nomeDigitado = edtNomeCadastro.getText().toString();
                senhaDigitada = edtSenhaCadastro.getText().toString();

                if ((nomeDigitado.length() >= 6) && (nomeDigitado.length() <= 20)){
                    sessionUsuario.setLogin(nomeDigitado);
                    if ((senhaDigitada.length() >= 6) && (senhaDigitada.length() <= 20 )){
                        sessionUsuario.setSenha(senhaDigitada);
                        finish();
                    }
                } else {
                    Snackbar.make(view, "Entre com no mínimo de 6 dígitos.", Snackbar.LENGTH_LONG).show();
                }
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
