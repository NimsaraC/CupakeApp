package com.nimsara.cupcakeapp.user;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.Address_Adapter;
import com.nimsara.cupcakeapp.database.AddressDB;

import java.util.ArrayList;

public class User_Shipping_address extends AppCompatActivity {

    RecyclerView AddressList;
    LinearLayout addButton;
    AddressDB addressDB;
    ArrayList<String> UName,UPhone,UStreet,UCity,UPostal;
    Context context;
    Address_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shipping_address);
        AddressList = findViewById(R.id.AddressList);
        addButton = findViewById(R.id.addBtn);
        context = this;

        addressDB = new AddressDB(User_Shipping_address.this);
        UName = new ArrayList<>();
        UPhone = new ArrayList<>();
        UStreet = new ArrayList<>();
        UCity = new ArrayList<>();
        UPostal = new ArrayList<>();

        displayAddress();

        adapter = new Address_Adapter(User_Shipping_address.this,UName,UPhone,UStreet,UCity,UPostal);
        AddressList.setAdapter(adapter);
        AddressList.setLayoutManager(new LinearLayoutManager(User_Shipping_address.this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Shipping_address.this, Add_address.class);
                startActivity(intent);
            }
        });
    }

    void displayAddress(){
        Cursor cursor = addressDB.getAllAddress();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                UName.add(cursor.getString(1));
                UPhone.add(cursor.getString(2));
                UStreet.add(cursor.getString(3));
                UCity.add(cursor.getString(4));
                UPostal.add(cursor.getString(5));
            }
        }
    }
}