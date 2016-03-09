package com.github.hartviqb.dbloquent;

import android.content.Context;
import com.github.hartviqb.dbloquent.helper.DatabaseConfig;
import com.github.hartviqb.dbloquent.shared.MigrationClassCacheShared;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * @copyright 2016 hartviq
 */

public class DbLoquent {
    private Context context;

    public DbLoquent(Context context){
        this.context = context;
    }

    public void init(DatabaseConfig databaseConfig){
        registerMigration(databaseConfig);
    }

    private void registerMigration(DatabaseConfig databaseConfig){
        List<DbLoquentMigration> dbLoquentMigrations= databaseConfig.getAddMigrationHelpers();
        MigrationClassCacheShared migrationClassCacheShared = new MigrationClassCacheShared(context);
        Set setMigrations = new HashSet();

        if (databaseConfig.getAddMigrationHelpers().size() > 0){
            for (int i = 0; i < dbLoquentMigrations.size(); i++) {
                DbLoquentMigration dbLoquentMigration = dbLoquentMigrations.get(i);
                String className = dbLoquentMigration.getClass().getName();
                setMigrations.add(className);
            }
        }
        migrationClassCacheShared.setSharedMigrationNames(setMigrations);
        migrationClassCacheShared.setSharedDbVersion(databaseConfig.getDbVersion());
        migrationClassCacheShared.setSharedDbName(databaseConfig.getDbName());
        migrationClassCacheShared.commit();
    }
}
