package com.example.njuptkechengquan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistActivity extends AppCompatActivity {

    private MyDBHelper dbHelper;
    private EditText editText3;
    private EditText editText4;

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
        setContentView(R.layout.activity_regist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        Button button_Reg = (Button) findViewById(R.id.button_Register);
        button_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3=(EditText)findViewById(R.id.et3);
                editText4=(EditText)findViewById(R.id.et4);
                String txt1 = editText3.getText().toString();
                if(txt1.length() == 0){
                    Toast.makeText(RegistActivity.this,"账户名不能为空！",Toast.LENGTH_SHORT).show();    //弹出一个自动消失的提示框
                    return;
                }
                String txt2 = editText4.getText().toString();
                if(txt2.length() == 0){
                    Toast.makeText(RegistActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();    //弹出一个自动消失的提示框
                    return;
                }
                String newname = editText3.getText().toString();
                String password = editText4.getText().toString();
                if (CheckIsDataAlreadyInDBorNot(newname)) {
                    Toast.makeText(RegistActivity.this,"该用户名已被注册，注册失败",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (register(newname, password)) {
                        Toast.makeText(RegistActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
        dbHelper = new MyDBHelper(this,"db1",null,1);
    }

    //向数据库插入数据
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        /*String sql = "insert into userData(name,password) value(?,?)";
        Object obj[]={username,password};
        db.execSQL(sql,obj);*/
        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        db.insert("userData",null,values);
        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }
    //检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
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