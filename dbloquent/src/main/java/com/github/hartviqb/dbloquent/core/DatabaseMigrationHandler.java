package com.github.hartviqb.dbloquent.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.hartviqb.dbloquent.DbLoquentMigration;
import com.github.hartviqb.dbloquent.helper.DatabaseHelper;

import java.util.ArrayList;

/**
 * Author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * Copyright 2016 hartviq
 */

class DatabaseMigrationHandler {
    private ArrayList<DbLoquentMigration> dbLoquentMigrations = new ArrayList<>();

    public DatabaseMigrationHandler(Context context) {
        DatabaseMigrationSyncCache databaseMigrationSyncCache = new DatabaseMigrationSyncCache(context);
        dbLoquentMigrations = databaseMigrationSyncCache.register();
    }

    public SQLiteDatabase up(SQLiteDatabase db) {
        // create table
        for (int i = 0; i < dbLoquentMigrations.size(); i++) {
            DbLoquentMigration dataMigration = dbLoquentMigrations.get(i);
            db.execSQL(DatabaseHelper.translateCreateTables(dataMigration.getTableName(), dataMigration.getColumnsName()));
        }
        return db;
    }

    public SQLiteDatabase down(SQLiteDatabase db) {

        // Drop Table
        for (int i = 0; i < dbLoquentMigrations.size(); i++) {
            DbLoquentMigration dataMigration = dbLoquentMigrations.get(i);
            db.execSQL("DROP TABLE IF EXISTS " + dataMigration.getTableName());
        }
        return db;
    }
}
