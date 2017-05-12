package com.example.njuptkechengquan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xueru on 2017/5/11.
 */

public class Db extends SQLiteOpenHelper {

    public Db(Context context){
        super(context,"db",null,1);//context,数据库名字,cursor自定义才会改,= =版本号
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL
        //登陆界面相关数据
//        db.execSQL("CREATE TABLE stu(" +
//                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "uid TEXT DEFAULT \"\"," +
//                "Email TEXT DEFAULT \"\"," +
//                "password TEXT DEFAULT \"\")");

        //bbs相关SQL数据
        db.execSQL("CREATE TABLE bbs(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "theme TEXT DEFAULT \"\"," +
                "like INTEGER DEFAULT (0)," +
                "content TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}