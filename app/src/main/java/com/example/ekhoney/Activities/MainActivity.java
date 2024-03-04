package com.example.ekhoney.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekhoney.Fragments.Home;
import com.example.ekhoney.Fragments.Login;
import com.example.ekhoney.Fragments.Products;
import com.example.ekhoney.Fragments.Register;
import com.example.ekhoney.Fragments.Services;
import com.example.ekhoney.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.search.SearchBar;

public class MainActivity extends AppCompatActivity {

    NavigationView top_navigation;
    BottomNavigationView bottom_navigation;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    ImageView toggle;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // #### GET IDS STARTS ####
        drawerLayout = findViewById(R.id.drawerLayout);
        top_navigation = findViewById(R.id.top_navigation);
        bottom_navigation = findViewById(R.id.bottm_navigation);
        toggle = findViewById(R.id.toggle);
        frameLayout = findViewById(R.id.frameLayout);
        searchView = findViewById(R.id.searchView);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new Home()).commit();
        // #### GET IDS ENDS ####


        // #### TOGGLE CLICK EVENTS STARTS ####
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(top_navigation,true);
            }
        });
        // #### TOGGLE CLICK EVENTS ENDS ####


        // #### TOP NAVIGATION BTN CLICK EVENTS STARTS ####
        top_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.top_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();
                    //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.top_products) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Products()).commit();
                    //Toast.makeText(MainActivity.this, "Product", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.top_services) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Services()).commit();
                }
                else if (id == R.id.top_login) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Login()).commit();
                }
                else if (id == R.id.top_register) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Register()).commit();
                }
                else {
                    //
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // #### TOP NAVIGATION BTN CLICK EVENTS ENDS ####
    }
}