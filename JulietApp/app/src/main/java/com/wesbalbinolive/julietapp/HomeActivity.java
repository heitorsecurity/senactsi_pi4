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
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {


    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public FloatingActionButton btnCam;
    public LinearLayout searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        btnCam = (FloatingActionButton) findViewById(R.id.btnCam);
        searchBar = (LinearLayout) findViewById(R.id.searchBar);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            if (btnCam.getVisibility() == View.VISIBLE) {
                btnCam.setVisibility(View.GONE);
                searchBar.setVisibility(View.GONE);
            } else{
                btnCam.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.VISIBLE);
            }

            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_sobre:
                Intent sobre = new Intent(HomeActivity.this, SobreActivity.class);
                startActivity(sobre);
                return true;
            case R.id.action_search:
                if (searchBar.getVisibility() == View.GONE){
                    searchBar.setVisibility(View.VISIBLE);
                } else{
                    searchBar.setVisibility(View.GONE);
                }
        }

        return super.onOptionsItemSelected(item);
    }
}
