package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNomeCadastro, edtConfirmaSenhaCadastro, edtSenhaCadastro;
    private Button btnCadastrar;
    private Toolbar mToolbar;
    private SessionUsuario sessionUsuario;
    private String nomeDigitado, senhaDigitada, emailDigitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNomeCadastro = (EditText) findViewById(R.id.edtNomeCadastro);
        edtConfirmaSenhaCadastro = (EditText) findViewById(R.id.edtConfirmaSenhaCadastro);
        edtSenhaCadastro = (EditText) findViewById(R.id.edtSenhaCadastro);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionUsuario = SessionUsuario.getInstance();
                nomeDigitado = edtNomeCadastro.getText().toString();
                senhaDigitada = edtSenhaCadastro.getText().toString();

                if ((nomeDigitado.length()< 3) || (nomeDigitado.length() > 20)){
                    new SweetAlertDialog(CadastroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Opa!")
                            .setContentText("Usuário entre 6 e 20 caracteres")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                } else{
                    sessionUsuario.setLogin(nomeDigitado);
                }

                if ((senhaDigitada.length() < 6) || (senhaDigitada.length() > 20 )){
                    new SweetAlertDialog(CadastroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Vish")
                            .setContentText("Senha entre 6 e 20 caracteres")
                            .showCancelButton(false)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();
                } else{
                    sessionUsuario.setSenha(senhaDigitada);
                    Intent endereco = new Intent(CadastroActivity.this, EnderecoActivity.class);
                    startActivity(endereco);
                    finish();
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
