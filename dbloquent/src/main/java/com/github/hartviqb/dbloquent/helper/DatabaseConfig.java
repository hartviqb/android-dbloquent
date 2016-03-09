package com.github.hartviqb.dbloquent.helper;

import com.github.hartviqb.dbloquent.DbLoquentMigration;
import java.util.List;

/**
 * @author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * @copyright 2016 hartviq
 */

public abstract class DatabaseConfig {
    public abstract Integer dbVersion();
    public abstract String dbName();
    public abstract List<DbLoquentMigration> addMigrationHelpers();

    private Integer dbVersion;
    private String dbName;
    private List<DbLoquentMigration> addMigrationHelpers;

    public Integer getDbVersion() {
        return this.dbVersion();
    }

    public String getDbName() {
        return this.dbName();
    }

    public List<DbLoquentMigration> getAddMigrationHelpers() {
        return this.addMigrationHelpers();
    }

}
