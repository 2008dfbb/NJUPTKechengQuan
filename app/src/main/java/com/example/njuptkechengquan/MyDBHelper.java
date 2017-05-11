package com.example.njuptkechengquan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xueru on 2017/5/12.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String CREATE_USERDATA="create table userData(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"name TEXT DEFAULT \"\","
            +"password TEXT DEFAULT \"\")";

    private Context mContext;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USERDATA);
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        //onCreate(db);
    }

}