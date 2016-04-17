package com.github.hartviqb.dbloquent;

/**
 * Author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * Copyright 2016 hartviq
 */

public abstract class DbLoquentMigration {

    private String tableName;
    private String columnsName;

    /**
     * tableName description.
     * note the name of the table to be created
     */
    public abstract String tableName();

    /**
     * columnsName description.
     *
     * note list table schema to be created
     */
    public abstract String columnsName();

    public DbLoquentMigration() {
        setTableName(tableName());
        setColumnsName(columnsName());
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(String columnsName) {
        this.columnsName = columnsName;
    }
}
