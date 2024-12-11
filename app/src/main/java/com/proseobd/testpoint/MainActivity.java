package com.proseobd.testpoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.HapticFeedbackConstants;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.WindowCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import com.proseobd.testpoint.databinding.ActivityMainBinding;

import com.google.android.material.color.DynamicColors;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer_Layout;
    FrameLayout frame;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enable dynamic colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivitiesIfAvailable(getApplication());
        }
        
        // Enable splash screen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        
        super.onCreate(savedInstanceState);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Replace findViewById with binding
        drawer_Layout = binding.drawerLayout;
        frame = binding.frame;
        toolbar = binding.toolbar;
        navigationView = binding.NavigationView;

        // Load theme preference
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("NightMode", false);

        // Set initial theme
        MenuItem themeItem = navigationView.getMenu().findItem(R.id.nightmode);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            themeItem.setTitle("Light Mode");
            themeItem.setIcon(R.drawable.ic_light_mode);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            themeItem.setTitle("Dark Mode");
            themeItem.setIcon(R.drawable.ic_dark_mode);
        }

        // Add fragment transitions
        getSupportFragmentManager().setFragmentFactory(new FragmentFactory());
        getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.frame, new FirstFragment())
            .commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer_Layout, toolbar, R.string.open, R.string.close
        );
        drawer_Layout.addDrawerListener(toggle);
        toggle.syncState();

        // Add navigation drawer animation
        drawer_Layout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float scaleFactor = 1 - (slideOffset * 0.1f);
                float endScale = 0.9f + (scaleFactor * 0.1f);
                
                View contentView = drawer_Layout.getChildAt(0);
                contentView.setScaleX(endScale);
                contentView.setScaleY(endScale);
                
                drawerView.setScaleX(slideOffset);
                drawerView.setScaleY(slideOffset);
                drawerView.setAlpha(slideOffset);
            }
        });

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
                performHapticFeedback();
                if (item.getItemId() == R.id.about) {
                    Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.notification) {
                    Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nightmode) {
                    toggleTheme();
                } else if (item.getItemId() == R.id.Other) {
                    Toast.makeText(MainActivity.this, "Other Apps", Toast.LENGTH_SHORT).show();
                }
                drawer_Layout.closeDrawers();
                return true;
            }
        });
    }

    private void performHapticFeedback() {
        View view = getCurrentFocus();
        if (view != null) {
            view.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
        }
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

    private void toggleTheme() {
        boolean isNightMode = sharedPreferences.getBoolean("NightMode", false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        
        // Save the current fragment state
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        String currentFragmentTag = currentFragment != null ? currentFragment.getClass().getName() : null;
        
        MenuItem themeItem = navigationView.getMenu().findItem(R.id.nightmode);
        
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("NightMode", false);
            themeItem.setTitle("Dark Mode");
            themeItem.setIcon(R.drawable.ic_dark_mode);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("NightMode", true);
            themeItem.setTitle("Light Mode");
            themeItem.setIcon(R.drawable.ic_light_mode);
        }
        
        editor.apply();
        
        // Instead of recreate(), use delegate to handle configuration change
        getDelegate().applyDayNight();
        
        // Restore the fragment state if needed
        if (currentFragmentTag != null) {
            try {
                Fragment newFragment = getSupportFragmentManager()
                    .getFragmentFactory()
                    .instantiate(getClassLoader(), currentFragmentTag);
                
                getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                    .replace(R.id.frame, newFragment)
                    .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
