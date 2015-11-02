package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProdutoActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
                Intent home = new Intent(ProdutoActivity.this, HomeActivity.class);
                startActivity(home);
                finish();
                return true;
            case R.id.action_sobre:
                Intent sobre = new Intent(ProdutoActivity.this, SobreActivity.class);
                startActivity(sobre);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
