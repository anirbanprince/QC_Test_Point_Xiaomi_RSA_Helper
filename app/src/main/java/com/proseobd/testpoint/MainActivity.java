package com.proseobd.testpoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer_Layout;
    FrameLayout frame;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_Layout = findViewById(R.id.drawer_layout);
        frame = findViewById(R.id.frame);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.NavigationView);

        // Load theme preference
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("NightMode", false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FirstFragment()).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer_Layout, toolbar, R.string.open, R.string.close
        );
        drawer_Layout.addDrawerListener(toggle);
        toggle.syncState();

        // Toolbar Menu Item Click Listener
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.rate) {
                    openAppOnPlayStore();
                    return true;
                } else if (item.getItemId() == R.id.share) {
                    shareContent();
                    return true;
                }
                return false;
            }
        });

        // Navigation Drawer Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.about) {
                    Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.notification) {
                    Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nightmode) {
                    toggleNightMode();
                } else if (item.getItemId() == R.id.Other) {
                    Toast.makeText(MainActivity.this, "Other Apps", Toast.LENGTH_SHORT).show();
                }
                drawer_Layout.closeDrawers();
                return true;
            }
        });
    }

    // Toggle Night Mode
    private void toggleNightMode() {
        boolean isNightMode = sharedPreferences.getBoolean("NightMode", false);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("NightMode", false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("NightMode", true);
        }
        editor.apply();
    }

    // Method to open the app on Play Store
    private void openAppOnPlayStore() {
        String packageName = getPackageName();
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Uri webUri = Uri.parse("https://play.google.com/store/apps/details?id=" + packageName);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webIntent);
        }
    }

    // Method to share content
    private void shareContent() {
        String shareText = "Check out this awesome app!";
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(shareText)
                .setChooserTitle("Share via")
                .createChooserIntent();
        startActivity(shareIntent);
    }
}
