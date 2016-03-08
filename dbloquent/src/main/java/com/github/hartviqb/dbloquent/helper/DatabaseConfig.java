package com.github.hartviqb.dbloquent.helper;

import com.github.hartviqb.dbloquent.DbLoquentMigration;
import java.util.ArrayList;

/**
 * Created by hartviq on 5/21/15.
 */
public abstract class DatabaseConfig {
    public abstract Integer dbVersion();
    public abstract String dbName();
    public abstract ArrayList<DbLoquentMigration> addMigrationHelpers();

    private Integer dbVersion;
    private String dbName;
    private ArrayList<DbLoquentMigration> addMigrationHelpers;

    public Integer getDbVersion() {
        return this.dbVersion();
    }

    public String getDbName() {
        return this.dbName();
    }

    public ArrayList<DbLoquentMigration> getAddMigrationHelpers() {
        return this.addMigrationHelpers();
    }

}
