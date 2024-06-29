package com.nimsara.cupcakeapp.database;

import static com.nimsara.cupcakeapp.login.SharedPreference.KEY_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class CartDB extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "CartDB";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "cart";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CATEGORY = "category";
    private static final String QUANTITY = "quantity";
    private static final String TOTAL = "total";
    private static final String COVER = "cover";
    public CartDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+ TABLE_NAME + "("+ID+" integer primary key autoincrement,"+
                NAME+" text,"+CATEGORY+" text,"+QUANTITY+" integer,"+TOTAL+" integer,"+COVER+" blob);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public void addCart(Context context, String name, String category, int quantity, double total, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();

//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " = ?", new String[]{name});
        Cursor cursor = db.rawQuery("SELECT " + ID + ", " + QUANTITY + " FROM " + TABLE_NAME + " WHERE " + NAME + " = ?", new String[]{name});
        if (cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
            int newQuantity = currentQuantity + quantity;

            ContentValues values = new ContentValues();
            values.put(QUANTITY, newQuantity);

            long result = db.update(TABLE_NAME, values, NAME + " = ?", new String[]{name});
            if(result==-1){
                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Added Successfully",Toast.LENGTH_SHORT).show();
            }
        } else {

            ContentValues values = new ContentValues();
            values.put(NAME, name);
            values.put(CATEGORY, category);
            values.put(QUANTITY, quantity);
            values.put(TOTAL, total);
            values.put(COVER, image);

            long result = db.insert(TABLE_NAME, null, values);
            if(result==-1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
            }else{
            Toast.makeText(context,"Added Successfully",Toast.LENGTH_SHORT).show();
            }
            }

        cursor.close();
        db.close();
    }

    public Cursor allData(){
        String query = "select * from "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }return cursor;
    }

    public void deleteCartItem(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_NAME, NAME + "=?", new String[]{itemName});

        if (result > 0) {
            Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    public void updateCartItemQuantity(String itemName, String newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUANTITY, newQuantity);

        db.update(TABLE_NAME, values, NAME + " = ?", new String[]{itemName});
        db.close();
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


}
