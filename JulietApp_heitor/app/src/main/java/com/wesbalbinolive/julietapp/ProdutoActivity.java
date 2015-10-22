package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProdutoActivity extends AppCompatActivity {

    public TextView txtAlgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        txtAlgo = (TextView) findViewById(R.id.txtAlgo);

        Intent resultado = getIntent();
        String recebido = resultado.getStringExtra("dado");
        txtAlgo.setText(recebido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                Intent home = new Intent(ProdutoActivity.this, HomeActivity.class);
                startActivity(home);
                finish();
                return true;
            case R.id.action_sobre:
                Intent sobre = new Intent(ProdutoActivity.this, SobreActivity.class);
                startActivity(sobre);
                return true;
            case R.id.action_Login:
                Intent login = new Intent(ProdutoActivity.this, LoginActivity.class);
                startActivity(login);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
