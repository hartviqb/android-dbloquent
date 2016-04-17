package com.github.hartviqb.dbloquent.helper;

/**
 * Author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * Copyright 2016 hartviq
 */

public class DatabaseHelper {

    public static String translateCreateTables(String tableName, String columnTable) {
        return "CREATE TABLE " + tableName + "(" + columnTable + ")";
    }

}
