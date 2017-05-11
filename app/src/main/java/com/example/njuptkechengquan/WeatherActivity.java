package com.example.njuptkechengquan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import java.util.Date;
import java.util.Locale;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class WeatherActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        WebView mWebView1 = (WebView) findViewById(R.id.myWebView1);
        WebSettings webSettings = mWebView1.getSettings();
        //webview支持js脚本
        webSettings.setJavaScriptEnabled(true);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        webSettings.setDomStorageEnabled(true);
        //配置权限

        mWebView1.setWebChromeClient(new WebChromeClient() {

            @Override

            public void onReceivedIcon(WebView view, Bitmap icon) {

                super.onReceivedIcon(view, icon);

            }





            @Override

            public void onGeolocationPermissionsShowPrompt(String origin,GeolocationPermissions.Callback callback) {

                callback.invoke(origin, true, false);

                super.onGeolocationPermissionsShowPrompt(origin, callback);

            }

        });
        mWebView1.loadUrl("http://m.weather.com.cn");
        mWebView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",Locale.CHINA);
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
