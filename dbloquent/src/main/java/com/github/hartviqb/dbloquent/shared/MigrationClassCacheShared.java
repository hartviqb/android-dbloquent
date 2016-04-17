package com.github.hartviqb.dbloquent.shared;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;

/**
 * Author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * Copyright 2016 hartviq
 */

public class MigrationClassCacheShared {
    private static final String FILENAME = "dbloquent_migration_class_cache_shared";
    private static final String SHARED_MIGRATION_NAMES = "migrations";
    private static final String SHARED_DB_NAME = "db_name";
    private static final String SHARED_DB_VERSION = "db_version";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public MigrationClassCacheShared(Activity activity){
        sharedPreferences = activity.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public MigrationClassCacheShared(Context context){
        sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getSharedDbName() {
        return sharedPreferences.getString(SHARED_DB_NAME, null);
    }

    public void setSharedDbName(String sharedDbName) {
        editor.putString(SHARED_DB_NAME, sharedDbName);
    }

    public Integer getSharedDbVersion() {
        return sharedPreferences.getInt(SHARED_DB_VERSION, 0);
    }

    public void setSharedDbVersion(Integer sharedDbVersion) {
        editor.putInt(SHARED_DB_VERSION, sharedDbVersion);
    }

    @SuppressWarnings("ConstantConditions")
    public String[] getSharedMigrationNames() {
        Set<String> var = sharedPreferences.getStringSet(SHARED_MIGRATION_NAMES, null);
        return var.toArray(new String[var.size()]);
    }

    @SuppressWarnings("unchecked")
    public void setSharedMigrationNames(Set sharedClassesName) {
        editor.putStringSet(SHARED_MIGRATION_NAMES, sharedClassesName);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }

    public void commit(){
        editor.commit();
    }
}
