package com.github.hartviqb.dbloquent.core;

import android.content.Context;
import android.util.Log;

import com.github.hartviqb.dbloquent.DbLoquentMigration;
import com.github.hartviqb.dbloquent.shared.MigrationClassCacheShared;

import java.util.ArrayList;

/**
 * @author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * @copyright 2016 hartviq
 */

public class DatabaseMigrationSyncCache {
    private Context context;

    public DatabaseMigrationSyncCache(Context context){
        setContext(context);
    }

    public ArrayList<DbLoquentMigration> register(){
        ArrayList<DbLoquentMigration> dbLoquentMigrations = new ArrayList<>();

        MigrationClassCacheShared migrationClassCacheShared = new MigrationClassCacheShared(getContext());
        String[] aaa = migrationClassCacheShared.getSharedMigrationNames();

        try {
            for (int i = 0; i < aaa.length; i++) {
                Object o = Class.forName(aaa[i]).newInstance();
                DbLoquentMigration dbLoquentMigration = (DbLoquentMigration) o;
                dbLoquentMigrations.add(dbLoquentMigration);
                Log.d(getClass().getName(),dbLoquentMigration.getTableName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return dbLoquentMigrations;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
