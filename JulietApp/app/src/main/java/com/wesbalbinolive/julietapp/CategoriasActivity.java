package com.wesbalbinolive.julietapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CategoriasActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView listCategorias;
    private int[] imagens = {
            R.drawable.chinelos,
            R.drawable.esportivos,
            R.drawable.casuais,
            R.drawable.infantil,
            R.drawable.salto_alto
    };
    private  String[] categorias = new String []{"Chinelos", "Esportivos", "Casuais", "Linha Infantil", "Salto Alto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listCategorias = (ListView) findViewById(R.id.listCategorias);
        CategoriaAdapter adapter = new CategoriaAdapter(this, categorias, imagens);
        listCategorias.setAdapter(adapter);
    }

    public class CategoriaAdapter extends ArrayAdapter<String>{
        Context context;
        int[] images;
        String[] titleArray;

        CategoriaAdapter(Context c, String[]nomeCategorias, int imgs[]){
            super(c, R.layout.item_list_categorias, R.id.txtNomeCategorias, nomeCategorias);
            this.context = c;
            this.images = imgs;
            this.titleArray = nomeCategorias;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row =  inflater.inflate(R.layout.item_list_categorias, viewGroup, false);
            ImageView imgCategorias = (ImageView) row.findViewById(R.id.imgCategorias);
            TextView nomeCategorias = (TextView) row.findViewById(R.id.txtNomeCategorias);

            imgCategorias.setImageResource(images[position]);
            nomeCategorias.setText(titleArray[position]);

            return row;
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
                Intent intent = new Intent(CategoriasActivity.this, SobreActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(CategoriasActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
