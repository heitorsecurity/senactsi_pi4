package com.wesbalbinogmail.julietapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private static final String FIRST_TIME = "first_time";
    private Toolbar mToolbar;
    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton btnCam;
    private int mSelectedId;
    private boolean mUserSawDrawer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnCam = (FloatingActionButton) findViewById(R.id.btnCam);

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CameraActivity.class));
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mDrawer = (NavigationView) findViewById(R.id.nav_view);
        mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this,
                mDrawerLayout,
                mToolbar,
                R.string.abrirMenu,
                R.string.fecharMenu);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if (!didUserSeeDrawer()) {
            showDrawer();
            markDrawerSeen();
        } else {
            hideDrawer();
        }

    }

    private boolean didUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);
        return mUserSawDrawer;
    }

    private void markDrawerSeen() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void navigate(int mSelectedId) {
        Intent intent = null;


        if (mSelectedId == R.id.action_cadastro) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        }
        if (mSelectedId == R.id.action_categorias) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, CategoriasActivity.class);
            startActivity(intent);
        }
        if (mSelectedId == R.id.action_carrinho) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, CarrinhoActivity.class);
            startActivity(intent);
        }
        if (mSelectedId == R.id.action_camera) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        }

        if (mSelectedId == R.id.action_pesquisa) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, PesquisaActivity.class);
            startActivity(intent);
        }

        if (mSelectedId == R.id.action_sobre) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, SobreActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Sair")
                    .setContentText("Certeza que quer sair?")
                    .setCancelText("Não")
                    .setConfirmText("Sim")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            HomeActivity.this.finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();

        navigate(mSelectedId);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

}
