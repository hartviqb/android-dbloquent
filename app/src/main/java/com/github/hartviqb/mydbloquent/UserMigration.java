package com.github.hartviqb.mydbloquent;

import com.github.hartviqb.dbloquent.DbLoquentMigration;

/**
 * Author hartviq <apiq404@gmail.com> on 3/9/16.
 * Copyright 2016 hartviq
 */
public class UserMigration extends DbLoquentMigration {
    /**
     * tableName description.
     *
     * note the name of the table to be created
     */
    @Override
    public String tableName() {
        return "users";
    }

    /**
     * columnsName description.
     *
     * note list table schema to be created
     */
    @Override
    public String columnsName() {
        return ""
                + "id" +" INTEGER PRIMARY KEY,"
                + "name" + " VARCHAR(100),"
                + "phone" + " INTEGER";
    }
}
