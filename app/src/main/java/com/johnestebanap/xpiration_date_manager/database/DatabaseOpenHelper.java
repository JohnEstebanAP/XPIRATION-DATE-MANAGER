package com.johnestebanap.xpiration_date_manager.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="DB_XPIRATION_DATE_MANAGER.db";
    private static final int DATABASE_VERSION = 1;

//constructor
    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION );
    }

}



