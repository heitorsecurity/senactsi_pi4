package com.wesbalbinolive.julietapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {


    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public FloatingActionButton btnCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        btnCam = (FloatingActionButton) findViewById(R.id.btnCam);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                drawerLayout.closeDrawers();

                switch (id) {
                    case R.id.action_categorias:
                        Intent categorias = new Intent(HomeActivity.this, CategoriasActivity.class);
                        startActivity(categorias);
                        return true;
                    case R.id.action_carrinho:
                        Intent carrinho = new Intent(HomeActivity.this, CarrinhoActivity.class);
                        startActivity(carrinho);
                        return true;
                    case R.id.action_camera:
                        Intent camera = new Intent(HomeActivity.this, CameraActivity.class);
                        startActivity(camera);
                        return true;
                    case R.id.action_sobre:
                        Intent sobre = new Intent(HomeActivity.this, SobreActivity.class);
                        startActivity(sobre);
                        return true;
                    case R.id.action_produto:
                        Intent produto = new Intent(HomeActivity.this, ProdutoActivity.class);
                        startActivity(produto);
                        return true;
                }
                return false;
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.abrirMenu, R.string.fecharMenu);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(camera);
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }

        int id = item.getItemId();

        switch (id) {
            case R.id.action_sobre:
                Intent sobre = new Intent(HomeActivity.this, SobreActivity.class);
                startActivity(sobre);
                return true;
            case R.id.action_search:
                Intent pesquisa = new Intent(HomeActivity.this, PesquisaActivity.class);
                startActivity(pesquisa);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
