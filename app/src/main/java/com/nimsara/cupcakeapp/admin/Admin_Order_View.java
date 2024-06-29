package com.nimsara.cupcakeapp.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.Order_Adapter;
import com.nimsara.cupcakeapp.database.OrdersDB;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Admin_Order_View extends AppCompatActivity {

    RecyclerView OList;
    Order_Adapter orderAdapter;
    ArrayList<String> OItem,OQty,OID,OStatus;
    Context context;
    OrdersDB ordersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_view);
        context = this;

        ordersDB = new OrdersDB(Admin_Order_View.this);
        OItem = new ArrayList<>();
        OQty = new ArrayList<>();
        OID = new ArrayList<>();
        OStatus = new ArrayList<>();
        OList = findViewById(R.id.OList);

        displayOrder();

        orderAdapter = new Order_Adapter(Admin_Order_View.this,OItem,OQty,OID,OStatus);
        OList.setAdapter(orderAdapter);
        OList.setLayoutManager(new LinearLayoutManager(Admin_Order_View.this));
        BottomNav();
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
            }
        }
    }
    public void BottomNav(){
        final int home=1;
        final int dash=2;
        final int notifications=3;
        @SuppressLint({"MissingInflatedId","LocalSuppress"})
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNav);
        bottomNavigation.add(new MeowBottomNavigation.Model(home,R.drawable.adminhome));
        bottomNavigation.add(new MeowBottomNavigation.Model(dash, R.drawable.orders));
        bottomNavigation.add(new MeowBottomNavigation.Model(notifications, R.drawable.cakes));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                int id = model.getId();
                if (id==1){
                    Intent intent = new Intent(getApplicationContext(), Admin_Menu.class);
                    startActivity(intent);
                }
                if (id==2){
                    Intent intent = new Intent(getApplicationContext(), Admin_Order_View.class);
                    startActivity(intent);
                }
                if (id==3){
                    Intent intent = new Intent(getApplicationContext(), Admin_Cake_List.class);
                    startActivity(intent);
                }
                return null;
            }
        });
    }

}