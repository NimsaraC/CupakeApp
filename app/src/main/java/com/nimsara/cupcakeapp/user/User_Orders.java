package com.nimsara.cupcakeapp.user;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.Oreder_Adapter_User;
import com.nimsara.cupcakeapp.database.OrdersDB;

import java.util.ArrayList;

public class User_Orders extends AppCompatActivity {

    RecyclerView OList;
    Oreder_Adapter_User orderAdapter;
    ArrayList<String> OItem,OQty,OID,OStatus,OPrice;
    Context context;
    OrdersDB ordersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);
        context = this;

        ordersDB = new OrdersDB(User_Orders.this);
        OItem = new ArrayList<>();
        OQty = new ArrayList<>();
        OID = new ArrayList<>();
        OStatus = new ArrayList<>();
        OPrice = new ArrayList<>();
        OList = findViewById(R.id.OCList);

        displayOrder();

        orderAdapter = new Oreder_Adapter_User(User_Orders.this,OItem,OQty,OID,OStatus,OPrice);
        OList.setAdapter(orderAdapter);
        OList.setLayoutManager(new LinearLayoutManager(User_Orders.this));

    }

    void displayOrder(){
        Cursor cursor = ordersDB.getAllCard();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                OItem.add(cursor.getString(4));
                OQty.add(cursor.getString(5));
                OID.add(cursor.getString(0));
                OStatus.add(cursor.getString(8));
                OPrice.add(cursor.getString(7));
            }
        }
    }
}
