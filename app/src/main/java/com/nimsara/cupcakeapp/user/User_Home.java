package com.nimsara.cupcakeapp.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nimsara.cupcakeapp.Categories;
import com.nimsara.cupcakeapp.CupCake;
import com.nimsara.cupcakeapp.R;
import com.nimsara.cupcakeapp.adapter.User_Cake_Adapter;
import com.nimsara.cupcakeapp.adapter.User_Category_Adapter;
import com.nimsara.cupcakeapp.login.SharedPreference;
import com.nimsara.cupcakeapp.login.User_Login;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class User_Home extends AppCompatActivity {
    SQLiteDatabase CatDB, CakeDB;

    TextView user;
    LinearLayout Orders;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        SetCakeDB();
        setCatDB();

        user = findViewById(R.id.txtUnameHome);
        SharedPreference preference = new SharedPreference();
        String name = preference.getString(getApplicationContext(),SharedPreference.KEY_NAME);
        user.setText(name);

        RecyclerView recyclerView = findViewById(R.id.CakeItem);
        CupCake cupCake = new CupCake();
        List<CupCake> cupCakes = cupCake.getCake(CakeDB);
        User_Cake_Adapter adapter = new User_Cake_Adapter(this,CakeDB,cupCakes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView2 = findViewById(R.id.CatItem);
        Categories categories = new Categories();
        List<Categories> categories1 = categories.GetCat(CatDB);
        User_Category_Adapter adapter2 = new User_Category_Adapter(this,CatDB,categories1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setAdapter(adapter2);

        Orders = findViewById(R.id.orders);

        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),User_Orders.class);
                startActivity(intent);
            }
        });

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
    private void setCatDB(){
        try{
            CatDB = openOrCreateDatabase("CategoryDB",MODE_PRIVATE,null);
            CatDB.execSQL("create table if not exists Cat(id integer primary key autoincrement,name text,description text,cover blob)");
        }catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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