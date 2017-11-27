package com.sunny.sunny.workingtime;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Sunny on 15/11/2017.
 */

public class JobProvider extends ContentProvider {

    private static final String AUTHORITY = "com.sunny.sunny.workingtime.authority.details";
    protected static final Uri CONTENT_URI_WORK = Uri.parse("content://" + AUTHORITY + "/" + JobDBHelper.TABLE_NAME);
    protected static final Uri CONTENT_URI_HOURS = Uri.parse("content://" + AUTHORITY + "/" + JobDBHelper.TABLE_NAME2);

    private JobDBHelper helper;

    @Override
    public boolean onCreate() {

        helper = new JobDBHelper(getContext());

        if (helper != null){
            return true;
        }
        return false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = helper.getWritableDatabase();
        long rowId;

        if (uri.equals(CONTENT_URI_WORK)) {
            rowId = db.insert(JobDBHelper.TABLE_NAME, null, contentValues);
        }else {
            rowId = db.insert(JobDBHelper.TABLE_NAME2, null, contentValues);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return uri.withAppendedPath(uri, rowId + "");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c;

        if (uri.equals(CONTENT_URI_WORK)) {
            c = db.query(JobDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }else{
            c = db.query(JobDBHelper.TABLE_NAME2, projection, selection, selectionArgs, null, null, sortOrder);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int count;

        if (uri.equals(CONTENT_URI_WORK)) {
            count = db.delete(JobDBHelper.TABLE_NAME, selection, selectionArgs);
        }else{
            count = db.delete(JobDBHelper.TABLE_NAME2, selection, selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int count;

        if (uri.equals(CONTENT_URI_WORK)) {
            count = db.update(JobDBHelper.TABLE_NAME, values, selection, selectionArgs);
        }else{
            count = db.update(JobDBHelper.TABLE_NAME2, values, selection, selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}
