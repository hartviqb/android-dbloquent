package com.github.hartviqb.dbloquent.helper;

import android.content.ContentValues;

/**
 * Created by hartviq on 5/21/15.
 */
public class DatabaseHelper {

    public static void translateCreateTableQuery(ContentValues contentValues) {

    }

    public static String translateCreateTables(String tableName, String columnTable) {
        return "CREATE TABLE " + tableName + "(" + columnTable + ")";
    }

}
