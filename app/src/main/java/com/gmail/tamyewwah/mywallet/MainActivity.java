package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference BillDatabase=FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = BillDatabase.child("User");
    private String UserID;
    private String UserPin;
    private FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View headerView;
    private TextView UserNameDrawer;
    private TextView UserEmailDrawer;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        headerView =navigationView.getHeaderView(0);
        UserNameDrawer = headerView.findViewById(R.id.drawerUserName);
        UserEmailDrawer =headerView.findViewById(R.id.drawerUserEmail);
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

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String getNickname;
                String getEmail;
                String getUserID;
                String getFullName;
                String getPhone;
                String getPin;
                String getPassword;

                for(DataSnapshot postData : dataSnapshot.getChildren()) {


                    getEmail=postData.child("email").getValue().toString();
                    getFullName=postData.child("full_name").getValue().toString();
                    getUserID=postData.child("id").getValue().toString();
                    getPassword=postData.child("password").getValue().toString();
                    getPhone=postData.child("phone_no").getValue().toString();
                    getPin=postData.child("pin").getValue().toString();
                    getNickname=postData.child("user_name").getValue().toString();
                    if(getUserID.matches(currentUser.getUid())) {

                            User user = new User(getUserID,getFullName,getNickname,getPhone,getEmail,getPassword,getPin);
                             UserID=user.getId();
                             UserPin=user.getPin();
                            UserNameDrawer.setText(user.getUser_name());
                            UserEmailDrawer.setText(user.getEmail());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                break;
            case R.id.nav_balance:
                selectedFragment = new BalanceFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                break;
            case R.id.nav_home:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
                break;
            case R.id.nav_feedback:
                selectedFragment = new FeedbackFragment();
                bottomNav.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                break;
            case R.id.nav_message:

                selectedFragment = new MessageFragment();
                bottomNav.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                break;
            case R.id.nav_setting:
                selectedFragment = new SettingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                break;
            case R.id.nav_help:
                selectedFragment = new HelpFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(MainActivity.this, Login.class);
                startActivity(intent2);
                finish();
                break;
        }
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
    public String USER_ID()
    {
        return UserID;
    }
    public String getUserPin(){
        return UserPin;
    }

}
