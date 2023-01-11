package com.compileC.aw;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    Toolbar tb;
    DrawerLayout db;
    NavigationView nv;
    ActionBarDrawerToggle toggle;
    FragmentManager fm;
    FragmentTransaction ft;
    ConnectivityManager connectivityManager;



    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        isNetworkConnectionAvailable();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);
        if (!Environment.isExternalStorageManager()){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }



        nv = findViewById( R.id.nav_view );
        db = findViewById( R.id.drawer );
        tb = findViewById( R.id.toolbar );
        setSupportActionBar( tb );
        nv.bringToFront();
        nv.setNavigationItemSelectedListener(this);

        connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService( Context.CONNECTIVITY_SERVICE );
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            showStartDialog();
        }

        toggle = new ActionBarDrawerToggle( this,db,tb,R.string.navigation_drawer_open,R.string.navigation_drawer_close );
        db.addDrawerListener( toggle );
        toggle.setDrawerIndicatorEnabled( true );
        toggle.syncState();

        fm = getSupportFragmentManager();
        ft =fm.beginTransaction();
        ft.add( R.id.content_fragment,new MainFragment() );
        ft.commit();

    }


    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        db.closeDrawer( GravityCompat.START );
        switch(item.getItemId())
        {
            case R.id.nav_home:
            fm = getSupportFragmentManager();
            ft =fm.beginTransaction();
            ft.replace( R.id.content_fragment,new MainFragment() );
            ft.commit();
            break;
            case R.id.nav_doraemon:
                Intent i6 = new Intent(MainActivity.this,Doreamon.class);
                startActivity(i6);
                break;
            case R.id.nav_nobita:
                Intent i2 = new Intent(MainActivity.this,Nobita.class);
                startActivity(i2);
                break;
            case R.id.nav_hot:
                Intent i9 = new Intent(MainActivity.this,hot.class);
                startActivity(i9);
                break;
            case R.id.nav_shizuka:
                Intent i3 = new Intent(MainActivity.this,Shizuka.class);
                startActivity(i3);
                break;
            case R.id.nav_suneo:
                Intent i4 = new Intent(MainActivity.this,Suneo.class);
                startActivity(i4);
                break;
            case R.id.nav_gian:
                Intent i5 = new Intent(MainActivity.this,Gian.class);
                startActivity(i5);
                break;
            case R.id.nav_share:
                Intent share = new Intent( Intent.ACTION_SEND );
                share.setType( "text/plain" );
                String shareBody ="https://play.google.com/store/apps/details?id=com.compileC.aw";
                String shareSubject = "ANIME X CARTOON";

                share.putExtra( Intent.EXTRA_TEXT,shareBody );
                share.putExtra( Intent.EXTRA_SUBJECT,shareSubject);

                startActivity( Intent.createChooser( share,"Share" ) );
                break;
            case R.id.nav_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.compileC.aw")));
                break;
        }
        return true;
    }




    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void checkNetworkConnection(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("NO INTERNET");

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage( "PLEASE CHECK YOUR INTERNET CONNECTION")
                .setCancelable(false)
                .setPositiveButton("Close:", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public void isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
        }
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("HELLO!! FIRST TIME ? HOW TO USE?")
                .setMessage("TAP IMAGES OTHERWISE TAP ON NAVIGATION MENU TO SEE OTHER OPTIONS")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }




}


