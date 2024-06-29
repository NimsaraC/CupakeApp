package com.nimsara.cupcakeapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CupCake {
    private String CakeName, Description, Category;
    private int ItemID;
    private double Price;
    private byte[] Cover;


    public CupCake(String cakeName, String description, String category, int itemID, double price, byte[] cover) {
        CakeName = cakeName;
        Description = description;
        Category = category;
        ItemID = itemID;
        Price = price;
        Cover = cover;
    }

    public CupCake(String cakeName, String description, String category, double price, byte[] cover) {
        CakeName = cakeName;
        Description = description;
        Category = category;
        Price = price;
        Cover = cover;
    }

    public CupCake() {
    }

    public String getCakeName() {
        return CakeName;
    }

    public void setCakeName(String cakeName) {
        CakeName = cakeName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public byte[] getCover() {
        return Cover;
    }

    public void setCover(byte[] cover) {
        Cover = cover;
    }

    public void Save(SQLiteDatabase CakeDB){
        try{
            ContentValues values = new ContentValues();
            values.put("name", CakeName);
            values.put("description",Description);
            values.put("category",Category);
            values.put("price",Price);
            values.put("cover",Cover);
            CakeDB.insert("cake",null,values);
        }catch (Exception ex) {
            throw ex;
        }
    }

    public List<CupCake> getCake(SQLiteDatabase CakeDB){
        try{
            List<CupCake> cupCakes = new ArrayList<CupCake>();
            String query="Select id,name,description,category,price,cover from cake";
            Cursor cursor = CakeDB.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do{
                    CupCake cupCake = new CupCake();
                    cupCake.setItemID(cursor.getInt(0));
                    cupCake.setCakeName(cursor.getString(1));
                    cupCake.setDescription(cursor.getString(2));
                    cupCake.setCategory(cursor.getString(3));
                    cupCake.setPrice(cursor.getDouble(4));
                    cupCake.setCover(cursor.getBlob(5));
                    cupCakes.add(cupCake);
                }while (cursor.moveToNext());
            }return cupCakes;
        }catch (Exception ex){
            throw ex;
        }
    }
    public List<CupCake> getCakeByCategory(SQLiteDatabase CakeDB, String categoryName){
        try{
            List<CupCake> cupCakes = new ArrayList<CupCake>();
            String query = "SELECT id, name, description, category, price, cover FROM cake WHERE category = ?";
            Cursor cursor = CakeDB.rawQuery(query, new String[]{categoryName});
            if (cursor.moveToFirst()){
                do{
                    CupCake cupCake = new CupCake();
                    cupCake.setItemID(cursor.getInt(0));
                    cupCake.setCakeName(cursor.getString(1));
                    cupCake.setDescription(cursor.getString(2));
                    cupCake.setCategory(cursor.getString(3));
                    cupCake.setPrice(cursor.getDouble(4));
                    cupCake.setCover(cursor.getBlob(5));
                    cupCakes.add(cupCake);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return cupCakes;
        } catch (Exception ex){
            throw ex;
        }
    }

    public void delete(SQLiteDatabase CakeDB, String cakeName) {
        try {
            CakeDB.delete("cake", "name=?", new String[]{cakeName});
        } catch (Exception ex) {
            throw ex;
        }
    }

}
