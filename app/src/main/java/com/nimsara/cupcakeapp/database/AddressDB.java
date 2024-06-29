package com.nimsara.cupcakeapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AddressDB extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "AddressDB";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "address";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String STREET = "street";
    private static final String CITY = "city";
    private static final String POSTAL = "postal";
    private static final String EMAIL = "email";
    public AddressDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " + PHONE + " TEXT, " + STREET + " TEXT, " + CITY + " TEXT, " + POSTAL + " INTEGER, " + EMAIL + " TEXT" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long insertAddress(String name, String email, String phone, String street, String city, String postal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(PHONE, phone);
        values.put(STREET, street);
        values.put(CITY, city);
        values.put(POSTAL, postal);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public Cursor getAllAddress(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

}
