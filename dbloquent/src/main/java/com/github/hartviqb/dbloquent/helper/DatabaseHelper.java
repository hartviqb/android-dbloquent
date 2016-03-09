package com.github.hartviqb.dbloquent.helper;

import android.content.ContentValues;

/**
 * @author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * @copyright 2016 hartviq
 */

public class DatabaseHelper {

    public static String translateCreateTables(String tableName, String columnTable) {
        return "CREATE TABLE " + tableName + "(" + columnTable + ")";
    }

}
