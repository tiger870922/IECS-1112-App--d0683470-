package com.example.orderapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BuyCarDBHelper extends SQLiteOpenHelper {

    private static final String database="buycardb.db";
    private static final int version=1;

    public BuyCarDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public BuyCarDBHelper(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE buyCarTable(_id integer primary key autoincrement," +
                "imgid real no null," +
                "name text no null," +
                "note text no null," +
                "amount real no null," +
                "price real no null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS buyCarTable");
        onCreate(db);
    }
}
