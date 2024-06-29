package com.nimsara.cupcakeapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    private String CategoryName, CatDescription;
    private byte[] CatCover;
    private int ItemID;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCatDescription() {
        return CatDescription;
    }

    public void setCatDescription(String catDescription) {
        CatDescription = catDescription;
    }

    public byte[] getCatCover() {
        return CatCover;
    }

    public void setCatCover(byte[] catCover) {
        CatCover = catCover;
    }

    public void Save(SQLiteDatabase CatDB){
        try{
            ContentValues values = new ContentValues();
            values.put("name", CategoryName);
            values.put("description",CatDescription);
            values.put("cover",CatCover);
            CatDB.insert("Cat",null,values);
        }catch (Exception ex) {
            throw ex;
        }
    }

    public List<Categories> GetCat(SQLiteDatabase CatDB){
        try{
            List<Categories> categories = new ArrayList<Categories>();
            String query="Select id,name,description,cover from Cat";
            Cursor cursor = CatDB.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do{
                    Categories categories1 = new Categories();
                    categories1.setItemID(cursor.getInt(0));
                    categories1.setCategoryName(cursor.getString(1));
                    categories1.setCatDescription(cursor.getString(2));
                    categories1.setCatCover(cursor.getBlob(3));
                    categories.add(categories1);
                }while (cursor.moveToNext());
            }return categories;
        }catch (Exception ex){
            throw ex;
        }
    }
    public List<String> getCategoryNames(SQLiteDatabase CatDB) {
        try {
            List<String> categoryNames = new ArrayList<>();
            String query = "SELECT name FROM Cat";
            Cursor cursor = CatDB.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String categoryName = cursor.getString(0);
                    categoryNames.add(categoryName);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return categoryNames;
        } catch (Exception ex) {
            throw ex;
        }
    }


}
