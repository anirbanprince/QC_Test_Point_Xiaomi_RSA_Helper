package com.proseobd.testpoint;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer_Layout;
    FrameLayout frame;
    MaterialToolbar toolbar;
    NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_Layout = findViewById(R.id.drawer_layout);
        frame = findViewById(R.id.frame);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.drawer);

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame,new FirstFragment()).commit();


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle
                (MainActivity.this,drawer_Layout,toolbar, R.string.open,R.string.close);
        drawer_Layout.addDrawerListener(toggle);
        toggle.syncState();


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.share){
                    Toast.makeText(MainActivity.this, "Share Clicked", Toast.LENGTH_SHORT).show();
                } else if(item.getItemId()==R.id.rate){
                    Toast.makeText(MainActivity.this, "Rate Clicked", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.about){
                    Toast.makeText(MainActivity.this, "About Clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId()==R.id.notification){
                    Toast.makeText(MainActivity.this, "Notification Clicked", Toast.LENGTH_SHORT).show();
                    } else if (item.getItemId()==R.id.Other){
                    Toast.makeText(MainActivity.this, "Other Clicked", Toast.LENGTH_SHORT).show();
                }
                drawer_Layout.closeDrawer(navigationView);



                return true;
            }
        });



    }
}