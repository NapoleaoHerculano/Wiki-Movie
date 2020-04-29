package com.napoleao.wikimovie;

import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialSearchView = findViewById(R.id.searchView);

        //Cria referência para toda a área do NavigationDrawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //Cria referência para a área de navegação
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Define configurações do NavigationDrawer
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_favorites, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();

        //Configura área que irá carregar os fragments
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //Configura menu superior de navegação
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //Configura navegação para o Navigationview
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Configurando o fechamento do ícone de busca ao apertar no botão voltar.
     */
    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

}
