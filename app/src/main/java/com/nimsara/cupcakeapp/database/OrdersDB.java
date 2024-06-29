package com.nimsara.cupcakeapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OrdersDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "OrdersDB";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "orders";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String NUMBER = "number";
    private static final String ITEM = "item";
    private static final String QUANTITY = "quantity";
    private static final String PRICE = "price";
    private static final String TOTAL = "total";
    private static final String STATUS = "status";

    public OrdersDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " + ADDRESS + " TEXT, " + NUMBER + " TEXT, " + ITEM + " TEXT, " + QUANTITY + " INTEGER, " + PRICE + " REAL, " + TOTAL + " REAL, " + STATUS + " TEXT DEFAULT 'pending')";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addOrder(String name, String address, String number, String item, int quantity, double price, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(ADDRESS, address);
        values.put(NUMBER, number);
        values.put(ITEM, item);
        values.put(QUANTITY, quantity);
        values.put(PRICE, price);
        values.put(TOTAL, total);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public void deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(orderId)});
        db.close();
    }

    public Cursor getAllCard(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void editOrderStatus(int orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS, newStatus);
        db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(orderId)});
        db.close();
    }



}
