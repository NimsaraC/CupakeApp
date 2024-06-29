package com.nimsara.cupcakeapp.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nimsara.cupcakeapp.CupCake;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.User_Cake_Adapter;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Category_cakes extends AppCompatActivity {
    SQLiteDatabase CakeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_cakes);
        SetCakeDB();
        RecyclerView recyclerView = findViewById(R.id.CCView);
        String name = getIntent().getStringExtra("name");

        if (name != null) {
            CupCake cupCake = new CupCake(); // Pass context
            List<CupCake> cupCakes = cupCake.getCakeByCategory(CakeDB, name);
            User_Cake_Adapter adapter = new User_Cake_Adapter(this, CakeDB, cupCakes);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Category name is null", Toast.LENGTH_SHORT).show();
        }

        BottomNav();
    }

    private void SetCakeDB() {
        try {
            CakeDB=openOrCreateDatabase("CakeData",MODE_PRIVATE,null);
            CakeDB.execSQL("Create table if not exists cake(id integer primary key autoincrement,name text,description text,category text,price real,cover blob)");
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void BottomNav(){
        final int home=1;
        final int dash=2;
        final int notifications=3;
        @SuppressLint({"MissingInflatedId","LocalSuppress"})
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNav);
        bottomNavigation.add(new MeowBottomNavigation.Model(home, R.drawable.store));
        bottomNavigation.add(new MeowBottomNavigation.Model(dash, R.drawable.cardt));
        bottomNavigation.add(new MeowBottomNavigation.Model(notifications, R.drawable.order));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                int id = model.getId();
                if (id==1){
                    Intent intent = new Intent(getApplicationContext(), User_Home.class);
                    startActivity(intent);
                }
                if (id==2){
                    Intent intent = new Intent(getApplicationContext(), User_cart.class);
                    startActivity(intent);
                }
                if (id==3){
                    Intent intent = new Intent(getApplicationContext(), User_Orders.class);
                    startActivity(intent);
                }
                return null;
            }
        });
    }
}