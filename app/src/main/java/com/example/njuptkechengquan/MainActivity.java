package com.example.njuptkechengquan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        navView.setCheckedItem(R.id.nav_calender);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                int id = item.getItemId();
                if(id == R.id.nav_calender){
                    mDrawerLayout.closeDrawers();
                }else if(id == R.id.nav_login){
                    Intent intent = new Intent("com.intent.action.LOGIN");
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                }else if(id == R.id.nav_regist){
                    Intent intent = new Intent("com.intent.action.REGIST");
                    startActivity(intent);
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
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Toast.makeText(this,"开发中",Toast.LENGTH_SHORT).show();
                break;
            case R.id.swinfo:
                Intent intent = new Intent("com.intent.action.INFO");
                startActivity(intent);
                break;
            case R.id.share:
                screenshot();
                break;
            default:
        }
        return true;
    }

    private void screenshot()
    {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null)
        {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
                String fname = sdf.format(new Date()) + ".png";
                String filePath = sdCardPath + File.separator + fname;
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                Toast.makeText(this,"截图已成功保存至手机内存根目录下",Toast.LENGTH_SHORT).show();
                os.flush();
                os.close();
            } catch (Exception e) {
                Toast.makeText(this,"截图失败，请重试",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
