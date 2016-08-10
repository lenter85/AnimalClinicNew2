package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.clinic.dto.RegisterLocation;
import com.example.myapplication.clinic.fragment.ClinicDetailInformationFragment;
import com.example.myapplication.clinic.fragment.ClinicList_Fragment;
import com.example.myapplication.clinic.fragment.MenuFragment;
import com.example.myapplication.clinic.fragment.RegisterClinicFragment;
import com.example.myapplication.community.CommunityFragment;
import com.example.myapplication.diary.DiaryFragment;
import com.example.myapplication.member.fragment.LogInFragment;
import com.example.myapplication.network.MainNetwork;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import com.example.myapplication.member.fragment.LogInFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static List<RegisterLocation> list = new ArrayList<>();
    public static String clinicLocation = null;
    public static String loginId = "test";
    public static String clinicId = "test";
    private ImageView imageViewReviewImg;
    private String mimageName;
    private Bitmap bitmap;
    private Bitmap smallbitmap;


    public static RegisterClinicFragment registerClinicFragment;

    public static String cName;
    public static ProgressDialog asyncDialog;

    //mainmain
    //메인 받아짐??
    //준식커밋
    //영진이 컴퓨터
    //민규 컴퓨터

    public static Fragment previousFragment;
    public static String LoginId = "NOMAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        asyncDialog = new ProgressDialog(this);

        setTitle("펫토피아");

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

        /*View nav_header_view = navigationView.getHeaderView(0);


        imageViewReviewImg = (ImageView) nav_header_view.findViewById(R.id.imageViewReviewImg);

        imageViewReviewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });*/

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
        } /*else if(getVisibleFragment() instanceof RegisterVaccinationFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new VaccinationFragment())
                    .commit();
        }*/

        else {
            super.onBackPressed();
        }

        /*if(getVisibleFragment() instanceof RegisterVaccinationFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new VaccinationFragment())
                    .commit();
        }

        if(getVisibleFragment() instanceof VaccinationFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new MyDiaryFragment())
                    .commit();
        }

        if(getVisibleFragment() instanceof MyDiaryFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new DiaryFragment())
                    .commit();
        }

        if(getVisibleFragment() instanceof DiaryFragment) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }*/
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
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

    private void dialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog
                .setTitle("선택")
                .setNegativeButton("카메라", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                });
        dialog
                .setPositiveButton("갤러리", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 2);
                    }
                });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            bitmap = (Bitmap) data.getExtras().get("data");
            smallbitmap = getResizedBitmap(bitmap, 500);
            imageViewReviewImg.setImageBitmap(smallbitmap);

            Uri uri = data.getData();
            String realPath = getAbsolutePathFromUri(this, uri);
            File file = new File(realPath);
            mimageName = file.getName();
            MainNetwork.registerMemberImage(loginId, mimageName, smallbitmap);

        } else if(requestCode == 2) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                smallbitmap = getResizedBitmap(bitmap, 500);
                imageViewReviewImg.setImageBitmap(smallbitmap);
                Uri uri = data.getData();
                String realPath = getAbsolutePathFromUri(this, uri);
                File file = new File(realPath);
                mimageName = file.getName();
                MainNetwork.registerMemberImage(loginId, mimageName, smallbitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        return path;
    }
}
