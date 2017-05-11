package com.example.njuptkechengquan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_personal);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                int id = item.getItemId();
                if(id == R.id.nav_personal){
                    mDrawerLayout.closeDrawers();
                }else if(id == R.id.nav_login){
                    mDrawerLayout.closeDrawers();
                }else if(id == R.id.nav_bbs){
                    Intent intent = new Intent("com.intent.action.BBS");
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                }else if(id == R.id.nav_searchCourse){
                    Intent intent = new Intent("com.intent.action.COURSE");
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                }else if(id == R.id.nav_weather){
                    Intent intent = new Intent("com.intent.action.WEATHER");
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.home:
//                Toast.makeText(this,"You click Home",Toast.LENGTH_SHORT).show();
//                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Toast.makeText(this,"You click Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.swinfo:
                Toast.makeText(this,"You click swinfo",Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this,"You click Share",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }



}
