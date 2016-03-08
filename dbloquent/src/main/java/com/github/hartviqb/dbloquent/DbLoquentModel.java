package com.github.hartviqb.dbloquent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.github.hartviqb.dbloquent.core.DatabaseInit;
import java.util.ArrayList;

/**
 * Created by hartviq on 5/21/15.
 */
public abstract class DbLoquentModel extends DatabaseInit {

    public static final String ORDER_TYPE_ASC = "ASC";
    public static final String ORDER_TYPE_DESC = "DESC";

    //used at global query
    private String query = "";

    //used at function deleteALL
    private String whereQuery = "";

    private Integer primaryValue = 0;
    private String primaryName = "id";

    private String tableName = "";
    private String[] columnName = null;

    private boolean isUsedWhere = false;
    private boolean isUsedWhereOnly = false;

    /**
     * tableName description.
     *
     * @note the name of the table will be called
     */
    public abstract String tableName();

    /**
     * fillAble description.
     *
     * @note list field names of the table will be called
     */
    public abstract String[] fillAble();

    public DbLoquentModel(Context context) {
        super(context);

        this.tableName = this.tableName();
        this.columnName = this.fillAble();

        select();
    }

    private void select() {
        this.query = "SELECT * FROM " + this.tableName;
    }

    private void checkSeparatorWhereAndORQuery(String ANDorOR) {
        checkSeparatorWhereOnlyQuery(ANDorOR);
        if (this.isUsedWhere) {
            this.query = this.query + " " + ANDorOR + " ";
        } else {
            this.query = this.query + " WHERE ";
        }
        this.isUsedWhere = true;
    }

    private void checkSeparatorWhereOnlyQuery(String ANDorOR) {
        if (this.isUsedWhereOnly) {
            this.whereQuery = this.whereQuery + " " + ANDorOR + " ";
        }
        this.isUsedWhereOnly = true;
    }

    public void where(String fieldname, String value) {
        checkSeparatorWhereAndORQuery("AND");

        String newQuery = fieldname + " ='" + value + "'";
        this.whereQuery = this.whereQuery + newQuery;
        this.query = this.query + newQuery;
    }

    public void where(String fieldname, String separator, String value) {
        checkSeparatorWhereAndORQuery("AND");

        String newQuery = fieldname + " " + separator + "'" + value + "'";
        this.whereQuery = this.whereQuery + newQuery;
        this.query = this.query + newQuery;
    }

    public void whereOR(String fieldname, String value) {
        checkSeparatorWhereAndORQuery("OR");

        String newQuery = fieldname + " ='" + value + "'";
        this.whereQuery = this.whereQuery + newQuery;
        this.query = this.query + newQuery;
    }

    public void whereOR(String fieldname, String separator, String value) {
        checkSeparatorWhereAndORQuery("OR");

        String newQuery = fieldname + " " + separator + "'" + value + "'";
        this.whereQuery = this.whereQuery + newQuery;
        this.query = this.query + newQuery;
    }

    public void whereIN(String fieldname, String[] clause) {
        checkSeparatorWhereAndORQuery("AND");
        String newQuery = fieldname + " IN(" + translateWhereINClause(clause) + ")";
        this.whereQuery = this.whereQuery + newQuery;
        this.query = this.query + newQuery;
    }

    public void whereINOR(String fieldname, String[] clause) {
        checkSeparatorWhereAndORQuery("OR");
        String newQuery = fieldname + " IN (" + translateWhereINClause(clause) + ")";
        this.whereQuery = this.whereQuery + newQuery;
        this.query = this.query + newQuery;
    }

    private String translateWhereINClause(String[] clause) {
        String clauseValue = "";
        for (int i = 0; i < clause.length; i++) {
            clauseValue = clauseValue + "'" + clause[i] + "'";
            if (i < clause.length - 1) {
                clauseValue = clauseValue + ",";
            }
        }
        return clauseValue;
    }

    public void orderBy(String fieldName, String type) {
        this.query = this.query + " ORDER BY " + fieldName + " " + type;
    }

    public void groupBy(String fieldName) {
        this.query = this.query + " GROUP BY " + fieldName;
    }

    public ContentValues findByID(Integer id) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getReadableDatabase();

        this.query = this.query + " WHERE " + this.primaryName + "=" + id;

        Cursor cursor = db.rawQuery(this.query, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    for (int i = 0; i < this.columnName.length; i++) {
                        contentValues.put(this.columnName[i],
                                cursor.getString(cursor.getColumnIndex(this.columnName[i].toString())));
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
                    for (int i = 0; i < this.columnName.length; i++) {
                        contentValues.put(this.columnName[i],
                                cursor.getString(cursor.getColumnIndex(this.columnName[i])));
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
                    for (int i = 0; i < this.columnName.length; i++) {
                        contentValues.put(this.columnName[i],
                                cursor.getString(cursor.getColumnIndex(this.columnName[i])));
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

        Cursor cursor = db.rawQuery(this.query, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    ContentValues contentValues = new ContentValues();
                    for (int i = 0; i < this.columnName.length; i++) {
                        contentValues.put(this.columnName[i],
                                cursor.getString(cursor.getColumnIndex(this.columnName[i])));
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
        if (this.whereQuery != null) {
            SQLiteDatabase db = this.getReadableDatabase();
            delete = db.delete(this.tableName, this.whereQuery, null) > 0;
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

    public Long createOrUpdate(ContentValues where, ContentValues data) {

        for (String key : where.keySet()) {
            String myKey = key;
            String myValue = (String) where.get(key);
            where(myKey, myValue);
        }

        ContentValues resultQwery = first();
        if (resultQwery.size() > 0) {
            this.primaryValue = resultQwery.getAsInteger(this.primaryName);
            Boolean success = this.update(data);
            if (success) {
                return Long.parseLong(this.primaryValue.toString());
            }
            return 0L;
        } else {
            return this.create(data);
        }
    }

    public boolean update(ContentValues data) {
        ContentValues createData = data;
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean update = false;
        Log.d(getClass().getName(), "update primaryValue " + this.primaryValue.toString());
        if (this.primaryValue != 0 && createData.size() > 0) {
            update = db.update(this.tableName, createData, this.primaryName + "=" + this.primaryValue, null) > 0;
        }
        db.close();
        return update;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getQuery() {
        return this.query;
    }

}
