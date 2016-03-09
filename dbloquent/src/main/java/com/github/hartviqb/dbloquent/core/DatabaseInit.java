package com.github.hartviqb.dbloquent.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.github.hartviqb.dbloquent.shared.MigrationClassCacheShared;

/**
 * @author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * @copyright 2016 hartviq
 */

public class DatabaseInit extends SQLiteOpenHelper {
    private Context context;
    public DatabaseInit(Context context) {
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
