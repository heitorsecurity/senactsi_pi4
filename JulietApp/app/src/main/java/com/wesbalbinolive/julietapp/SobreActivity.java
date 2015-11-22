package com.wesbalbinolive.julietapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SobreActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private  ListView listIntegrantes;
    private int[] imagens = {
                R.drawable.wes,
                R.drawable.rafa,
                R.drawable.sid,
                R.drawable.cris,
                R.drawable.hei
    };
    private  String[] integrantes = new String []{"Wesley Balbino", "Rafael Carignato", "Sidney Sousa", "Cristhian Alves", "Heitor Teixeira"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listIntegrantes = (ListView) findViewById(R.id.listIntegrantes);
        SobreAdapter adapter = new SobreAdapter(this, integrantes, imagens);
        listIntegrantes.setAdapter(adapter);
    }

    public class SobreAdapter extends ArrayAdapter<String>{
        Context context;
        int[] images;
        String[] titleArray;

        SobreAdapter(Context c, String[]nomeIntegrantes, int imgs[]){
            super(c, R.layout.item_list_integrantes, R.id.txtNomeIntegrante, nomeIntegrantes);
            this.context = c;
            this.images = imgs;
            this.titleArray = nomeIntegrantes;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row =  inflater.inflate(R.layout.item_list_integrantes, viewGroup, false);
            ImageView imgIntegrante = (ImageView) row.findViewById(R.id.imgIntegrante);
            TextView nomeIntegrante = (TextView) row.findViewById(R.id.txtNomeIntegrante);

            imgIntegrante.setImageResource(images[position]);
            nomeIntegrante.setText(titleArray[position]);

            return row;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
