package com.wesbalbinolive.julietapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class PesquisaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
