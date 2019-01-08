package com.gmail.tamyewwah.mywallet;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private String UserID="KAmWb6hSTxbT8jCWT61n98TR6PA3";
    private DrawerLayout drawer;
    private BottomNavigationView bottomNav;
    public String getUserID(){
        return UserID;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer =findViewById(R.id.main_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        findViewById(R.id.main_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                return false;
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId())
        {
            case R.id.nav_transactionRecord:
                selectedFragment = new TransactionRecordFragment();
                break;
            case R.id.nav_balance:
                selectedFragment = new BalanceFragment();
                break;
            case R.id.nav_feedback:
                selectedFragment = new FeedbackFragment();
                bottomNav.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_message:

                selectedFragment = new MessageFragment();
                bottomNav.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_setting:
                selectedFragment = new SettingFragment();
                break;
            case R.id.nav_help:
                selectedFragment = new HelpFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId())
            {
                case R.id.nav_payment:
                    selectedFragment = new PaymentFragment();

                    bottomNav.setVisibility(View.INVISIBLE);
                    break;
                case R.id.nav_transfer:
                    selectedFragment = new TransferFragment();
                    break;
                case R.id.nav_MyWallet:
                    selectedFragment = new MyWalletFragment();
                    break;
                case R.id.nav_promotion:
                    selectedFragment = new PromotionFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };


}
