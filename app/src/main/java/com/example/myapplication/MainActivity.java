package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.myapplication.clinic.dto.RegisterLocation;
import com.example.myapplication.clinic.fragment.ClinicDetailInformationFragment;
import com.example.myapplication.clinic.fragment.ClinicList_Fragment;
import com.example.myapplication.clinic.fragment.MenuFragment;
import com.example.myapplication.clinic.fragment.RegisterClinicFragment;
import com.example.myapplication.community.CommunityFragment;
import com.example.myapplication.diary.DiaryFragment;
import com.example.myapplication.member.fragment.LogInFragment;

import java.util.ArrayList;
import java.util.List;
//import com.example.myapplication.member.fragment.LogInFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static List<RegisterLocation> list = new ArrayList<>();
    public static String clinicLocation = null;

    public static RegisterClinicFragment registerClinicFragment;
//mainmain


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerClinicFragment = new RegisterClinicFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String page = intent.getStringExtra("page");



       if(page == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ClinicList_Fragment())
                    .commit();
        }else{
            if(page.equals("note")){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new DiaryFragment())
                        .commit();
            }else if(page.equals("clinic")){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new ClinicList_Fragment())
                        .commit();
            }else if(page.equals("community")){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new CommunityFragment())
                        .commit();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //옵션 메뉴뉴
   /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //병원정보 클릭
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ClinicList_Fragment())
                    .commit();
        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new DiaryFragment())
                    .commit();

        } else if (id == R.id.nav_slideshow) {
            //커뮤니티 클릭
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new CommunityFragment())
                    .commit();
        } else if (id == R.id.nav_manage) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new LogInFragment())
                    .commit();
        } else if (id == R.id.nav_share) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ClinicDetailInformationFragment())
                    .commit();
        } else if (id == R.id.nav_send) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new MenuFragment())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
