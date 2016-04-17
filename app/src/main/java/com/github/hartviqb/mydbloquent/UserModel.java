package com.github.hartviqb.mydbloquent;

import android.content.Context;

import com.github.hartviqb.dbloquent.DbLoquentModel;

/**
 * Author hartviq <apiq404@gmail.com> on 3/9/16.
 * Copyright 2016 hartviq
 */
public class UserModel extends DbLoquentModel {


    public UserModel(Context context) {
        super(context);
    }

    /**
     * tableName description.
     *
     * note the name of the table will be called
     */
    @Override
    public String tableName() {
        return "users";
    }

    /**
     * fillAble description.
     *
     * note list field names of the table will be called
     */
    @Override
    public String[] fillAble() {
        return new String[] {
                "id",
                "name",
                "phone"
        };
    }
}
