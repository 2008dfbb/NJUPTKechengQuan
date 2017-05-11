package com.example.njuptkechengquan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private com.example.njuptkechengquan.MyDBHelper dbHelper;
    private EditText username;
    private EditText password;
    private CheckBox checkBox1;

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
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        //点击注册按钮进入注册页面1
        Button button_toReg = (Button) findViewById(R.id.button_toRegist);
        button_toReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });

        //点击登录按钮1
        username=(EditText)findViewById(R.id.et1);
        password=(EditText)findViewById(R.id.et2);
        checkBox1=(CheckBox) findViewById(R.id.checkBox1);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userName=username.getText().toString();
                String passWord=password.getText().toString();
                if (login(userName,passWord)) {
                    Toast.makeText(LoginActivity.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                //跳转至主界面去
//                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
//                startActivity(intent);
            }
        });

        dbHelper = new MyDBHelper(this,"db1",null,1);
    }

//    //点击注册按钮进入注册页面
//    public void logonClicked(View view){
//        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
//        startActivity(intent);
//    }


    //点击登录按钮
    public void loginClicked(View view) {


    }

    //验证登录
    public boolean login(String username,String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
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
