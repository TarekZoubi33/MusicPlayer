package com.sparidapps.musicplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.*;
import com.ogaclejapan.smarttablayout.utils.BuildConfig;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.*;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements albums.OnFragmentInteractionListener, artist.OnFragmentInteractionListener,
        folders.OnFragmentInteractionListener, genre.OnFragmentInteractionListener, playlist.OnFragmentInteractionListener, tracks.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    Context mContext;
    int MY_PERMISSON_READ_EXTERNAL_STORAGE = 10;
    int extstoragePermissionCheck;
    SharedPreferences preferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        extstoragePermissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);


        if(extstoragePermissionCheck == PackageManager.PERMISSION_GRANTED){
            preferences.edit().putInt("exPermissionCheck", extstoragePermissionCheck).apply();
        }
      else if (extstoragePermissionCheck == PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]  {android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSON_READ_EXTERNAL_STORAGE);
            }

        }






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SmartTabLayout tabLayout = (SmartTabLayout) findViewById(R.id.viewpagertab);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("TRACK", tracks.class)
                .add("ALBUM", albums.class)
                .add("ARTIST", artist.class)
                .add("GENRE", genre.class)
                .add("PLAYLIST", playlist.class)
                .add("FOLDER", folders.class)
                .create());


        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPagerTransformer());
        tabLayout.setViewPager(viewPager);

        mContext = getApplicationContext();






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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        for(String permission : permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                //Permission denied
                showPermissionNeededDialog();
            }
            else if (ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED){
                //Permission granted
                preferences.edit().putInt("exPermissionCheck", extstoragePermissionCheck).apply();
            }
            else{
                //User checked never ask again
                showPermissionNeededDialog();
            }
        }
    }

    private void showPermissionNeededDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Permission is needed");
        builder.setMessage("You need to enable storage permission from settings!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.show();
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}
