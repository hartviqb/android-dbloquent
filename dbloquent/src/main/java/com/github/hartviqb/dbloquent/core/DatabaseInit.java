package com.github.hartviqb.dbloquent.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.github.hartviqb.dbloquent.shared.MigrationClassCacheShared;

/**
 * Author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * Copyright 2016 hartviq
 */

public class DatabaseInit extends SQLiteOpenHelper {
    private Context context;
    protected DatabaseInit(Context context) {
        super(context, new MigrationClassCacheShared(context).getSharedDbName(), null, new MigrationClassCacheShared(context).getSharedDbVersion());
        setContext(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w(DatabaseInit.class.getName(), "Create New Table ");
        //noinspection UnusedAssignment
        db = new DatabaseMigrationHandler(getContext()).up(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseInit.class.getName(),
                "Upgrading database from version " +
                        oldVersion + " to " + newVersion + ", which will destroy all old data");
        db = new DatabaseMigrationHandler(getContext()).down(db);
        // Create tables again
        onCreate(db);
    }

    private Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }
}
