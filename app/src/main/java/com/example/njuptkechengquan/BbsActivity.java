package com.example.njuptkechengquan;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BbsActivity extends ListActivity {
    private SimpleCursorAdapter adapter;
    private EditText etTheme,etContent;
    private Button btnAdd;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite;



    private View.OnClickListener btnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues cv = new ContentValues();
            String txt1 = etTheme.getText().toString();
            if(txt1.length() == 0){
                Toast.makeText(BbsActivity.this,"主题不能为空",Toast.LENGTH_SHORT).show();    //弹出一个自动消失的提示框
                return;
            }
            String txt2 = etContent.getText().toString();
            if(txt2.length() == 0){
                Toast.makeText(BbsActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();    //弹出一个自动消失的提示框
                return;
            }
            cv.put("theme",etTheme.getText().toString());
            cv.put("content",etContent.getText().toString());

            dbWrite.insert("bbs",null,cv);

            refreshListView();

        }
    };

    private void refreshListView(){
        Cursor c = dbRead.query("bbs",null,null,null,null,null,null);
        adapter.changeCursor(c);
    }



    private AdapterView.OnItemLongClickListener ListViewItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        //长按删除
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            new AlertDialog.Builder(BbsActivity.this).setTitle("提醒").setMessage("确认删除该项吗？").setNegativeButton("取消",null).setPositiveButton("确定",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog,int which){
                    Cursor c = adapter.getCursor();
                    c.moveToPosition(position);

                    int itemId = c.getInt(c.getColumnIndex("_id"));
                    dbWrite.delete("bbs","_id=?",new String[]{itemId+""});
                    refreshListView();
                }
            }).show();

            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);



        etTheme = (EditText) findViewById(R.id.etTheme);
        etContent = (EditText) findViewById(R.id.etContent);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(btnAddListener);


        db = new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();

        adapter = new SimpleCursorAdapter(this, R.layout.user_list_cell, null, new String[]{"theme", "content"}, new int[]{R.id.tvTheme,R.id.tvContent});
        setListAdapter(adapter);

        refreshListView();

        getListView().setOnItemLongClickListener(ListViewItemLongClickListener);
    }

//    private void screenshot()
//    {
//        // 获取屏幕
//        View dView = getWindow().getDecorView();
//        dView.setDrawingCacheEnabled(true);
//        dView.buildDrawingCache();
//        Bitmap bmp = dView.getDrawingCache();
//        if (bmp != null)
//        {
//            try {
//                // 获取内置SD卡路径
//                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
//                // 图片文件路径
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",Locale.US);
//                String fname = sdf.format(new Date()) + ".png";
//                String filePath = sdCardPath + File.separator + fname;
//                File file = new File(filePath);
//                FileOutputStream os = new FileOutputStream(file);
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
//                Toast.makeText(this,"截图已成功保存至手机内存根目录下",Toast.LENGTH_SHORT).show();
//                os.flush();
//                os.close();
//            } catch (Exception e) {
//                Toast.makeText(this,"截图失败，请重试",Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
