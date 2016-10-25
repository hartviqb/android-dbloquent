package com.github.hartviqb.dbloquent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.hartviqb.dbloquent.core.DatabaseInit;

import java.util.ArrayList;

/**
 * Author hartviq baturante <apiq404@gmail.com> on 01/01/16.
 * Copyright 2016 hartviq
 */

public abstract class DbLoquentModel extends DatabaseInit {

    public static final String ORDER_TYPE_ASC = "ASC";
    public static final String ORDER_TYPE_DESC = "DESC";

    //used at global query
    private String query = "";

    //used at function deleteALL
    private String deleteAllQuery = "";

    //used at function limit
    private String limitQuery = "";

    //used at function offset
    private String offsetQuery = "";

    private Integer primaryValue = 0;
    private final String primaryName = "id";

    private String tableName = "";
    private String[] columnName = null;

    private boolean isUsedWhere = false;
    private boolean isUsedWhereOnly = false;

    /**
     * tableName description.
     * <p>
     * note the name of the table will be called
     */
    public abstract String tableName();

    /**
     * fillAble description.
     * <p>
     * note list field names of the table will be called
     */
    public abstract String[] fillAble();

    public DbLoquentModel(Context context) {
        super(context);

        this.tableName = this.tableName();
        this.columnName = this.fillAble();

        resetQuery();
    }

    private void resetQuery() {
        this.query = "SELECT * FROM " + this.tableName;
        this.deleteAllQuery = "";
        this.offsetQuery = "";
        this.limitQuery = "";

        this.isUsedWhere = false;
        this.isUsedWhereOnly = false;

        this.primaryValue = 0;
    }

    private void checkSeparatorWhereAndORQuery(String ANDorOR) {
        checkSeparatorWhereOnlyQuery(ANDorOR);
        if (this.isUsedWhere) {
            this.query = this.query + " " + ANDorOR + " ";
            this.deleteAllQuery = this.deleteAllQuery + " " + ANDorOR + " ";
        } else {
            this.query = this.query + " WHERE ";
            this.deleteAllQuery = this.deleteAllQuery + " WHERE ";
        }
        this.isUsedWhere = true;
    }

    private void checkSeparatorWhereOnlyQuery(String ANDorOR) {
        if (this.isUsedWhereOnly) {
            this.deleteAllQuery = this.deleteAllQuery + " " + ANDorOR + " ";
        }
        this.isUsedWhereOnly = true;
    }

    public DbLoquentModel where(String fieldname, String value) {
        checkSeparatorWhereAndORQuery("AND");
        String newQuery = fieldname + " ='" + value + "'";
        this.deleteAllQuery = this.deleteAllQuery + newQuery;
        this.query = this.query + newQuery;
        return this;
    }

    public DbLoquentModel where(String fieldname, String expression, String value) {
        checkSeparatorWhereAndORQuery("AND");

        String newQuery = fieldname + " " + expression + "'" + value + "'";
        this.deleteAllQuery = this.deleteAllQuery + newQuery;
        this.query = this.query + newQuery;
        return this;
    }

    public DbLoquentModel whereOR(String fieldname, String value) {
        checkSeparatorWhereAndORQuery("OR");

        String newQuery = fieldname + " ='" + value + "'";
        this.deleteAllQuery = this.deleteAllQuery + newQuery;
        this.query = this.query + newQuery;
        return this;
    }

    public DbLoquentModel whereOR(String fieldname, String expression, String value) {
        checkSeparatorWhereAndORQuery("OR");

        String newQuery = fieldname + " " + expression + "'" + value + "'";
        this.deleteAllQuery = this.deleteAllQuery + newQuery;
        this.query = this.query + newQuery;
        return this;
    }

    public DbLoquentModel whereIN(String fieldname, String[] clauses) {
        checkSeparatorWhereAndORQuery("AND");
        String newQuery = fieldname + " IN(" + translateWhereINClause(clauses) + ")";
        this.deleteAllQuery = this.deleteAllQuery + newQuery;
        this.query = this.query + newQuery;
        return this;
    }

    public DbLoquentModel whereINOR(String fieldname, String[] clauses) {
        checkSeparatorWhereAndORQuery("OR");
        String newQuery = fieldname + " IN (" + translateWhereINClause(clauses) + ")";
        this.deleteAllQuery = this.deleteAllQuery + newQuery;
        this.query = this.query + newQuery;
        return this;
    }

    private String translateWhereINClause(String[] clauses) {
        String clauseValue = "";
        for (int i = 0; i < clauses.length; i++) {
            clauseValue = clauseValue + "'" + clauses[i] + "'";
            if (i < clauses.length - 1) {
                clauseValue = clauseValue + ",";
            }
        }
        return clauseValue;
    }

    public DbLoquentModel limit(Integer noOfRows) {
        if (this.query != null) {
            this.limitQuery = " LIMIT " + noOfRows + " ";
        }
        return this;
    }

    public DbLoquentModel offset(Integer rowNum) {
        if (this.query != null) {
            this.offsetQuery = " OFFSET " + rowNum + " ";
        }
        return this;
    }

    public DbLoquentModel orderBy(String fieldName, String typeOrder) {
        this.query = this.query + " ORDER BY " + fieldName + " " + typeOrder;
        return this;
    }

    public DbLoquentModel groupBy(String fieldName) {
        this.query = this.query + " GROUP BY " + fieldName;
        return this;
    }

    public ContentValues findByID(Integer id) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getReadableDatabase();

        this.query = this.query + " WHERE " + this.primaryName + "=" + id;

        Cursor cursor = db.rawQuery(this.query, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    for (String aColumnName : this.columnName) {
                        contentValues.put(aColumnName,
                                cursor.getString(cursor.getColumnIndex(aColumnName)));
                    }

                    this.primaryValue = cursor.getInt(cursor.getColumnIndex(this.primaryName));
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        db.close();
        return contentValues;
    }

    public ContentValues last() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(this.query, null);

        final ContentValues contentValues = new ContentValues();

        try {
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToLast()) {
                    for (String aColumnName : this.columnName) {
                        contentValues.put(aColumnName,
                                cursor.getString(cursor.getColumnIndex(aColumnName)));
                    }

                    this.primaryValue = cursor.getInt(cursor.getColumnIndex(this.primaryName));
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        db.close();
        return contentValues;
    }

    public ContentValues first() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(this.query, null);

        final ContentValues contentValues = new ContentValues();

        try {
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    for (String aColumnName : this.columnName) {
                        contentValues.put(aColumnName,
                                cursor.getString(cursor.getColumnIndex(aColumnName)));
                    }

                    this.primaryValue = cursor.getInt(cursor.getColumnIndex(this.primaryName));
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        db.close();
        return contentValues;
    }

    public ArrayList<ContentValues> get() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ContentValues> listData = new ArrayList();

        String mergeQuery = this.query + this.limitQuery + this.offsetQuery;
        Cursor cursor = db.rawQuery(mergeQuery, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    ContentValues contentValues = new ContentValues();
                    for (String aColumnName : this.columnName) {
                        contentValues.put(aColumnName,
                                cursor.getString(cursor.getColumnIndex(aColumnName)));
                    }
                    listData.add(contentValues);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        db.close();
        return listData;
    }

    public boolean delete() {
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean delete = false;
        if (this.primaryValue != 0) {
            delete = db.delete(this.tableName, this.primaryName + "=" + this.primaryValue, null) > 0;
        }
        db.close();
        return delete;
    }

    public boolean truncate() {
        Boolean delete;
        SQLiteDatabase db = this.getReadableDatabase();
        delete = db.delete(this.tableName, null, null) > 0;
        db.close();
        return delete;
    }

    public boolean deleteAll() {
        Boolean delete = false;
        if (this.deleteAllQuery != null) {
            SQLiteDatabase db = this.getReadableDatabase();
            String mergeQueryDeleteAll = this.deleteAllQuery + this.limitQuery + this.offsetQuery;
            String mergeQuery = this.primaryName + " IN (SELECT " + this.primaryName + " FROM " + this.tableName + " " + mergeQueryDeleteAll + " )";
            delete = db.delete(this.tableName, mergeQuery, null) > 0;
            db.close();
        }
        return delete;
    }

    public Long create(ContentValues data) {
        Long create = null;
        if (data.size() > 0) {
            SQLiteDatabase db = this.getReadableDatabase();
            create = db.insert(this.tableName, null, data);
            db.close();
        }
        return create;
    }

    public Long createOrUpdate(ContentValues wheres, ContentValues values) {

        for (String key : wheres.keySet()) {
            String myValue = String.valueOf(wheres.get(key));
            where(key, myValue);
        }

        ContentValues resultQwery = first();
        if (resultQwery.size() > 0) {
            this.primaryValue = resultQwery.getAsInteger(this.primaryName);
            Boolean success = this.update(values);
            if (success) {
                return Long.parseLong(this.primaryValue.toString());
            }
            return 0L;
        } else {
            return this.create(values);
        }
    }

    public boolean update(ContentValues values) {
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean update = false;
        if (this.primaryValue != 0 && values.size() > 0) {
            update = db.update(this.tableName, values, this.primaryName + "=" + this.primaryValue, null) > 0;
        }
        db.close();
        return update;
    }

    public boolean updateAll(ContentValues values) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean update = db.update(this.tableName, values, this.deleteAllQuery, null) > 0;
        db.close();
        return update;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getQuery() {
        return this.query;
    }

    /**
     * Close any open database object.
     */
    @Override
    public synchronized void close() {
        super.close();
        resetQuery();
    }
}
