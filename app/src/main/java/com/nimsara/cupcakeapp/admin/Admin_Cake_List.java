package com.nimsara.cupcakeapp.admin;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimsara.cupcakeapp.CupCake;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.Admin_cake_adapter;

import java.util.List;

public class Admin_Cake_List extends AppCompatActivity {
    LinearLayout AddCake;

    SQLiteDatabase CakeDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cake_list);
        RecyclerView recyclerView = findViewById(R.id.cakeList);
        SetCakeDB();
        CupCake cupCake = new CupCake();
        List<CupCake> cupCakes = cupCake.getCake(CakeDB);
        Admin_cake_adapter adapter = new Admin_cake_adapter(this,CakeDB,cupCakes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        AddCake = findViewById(R.id.AddItem);
        AddCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Admin_add_cake.class);
                startActivity(intent);
            }
        });

    }
    private void SetCakeDB() {
        try {
            CakeDB=openOrCreateDatabase("CakeData",MODE_PRIVATE,null);
            CakeDB.execSQL("Create table if not exists cake(id integer primary key autoincrement,name text,description text,category text,price real,cover blob)");
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}